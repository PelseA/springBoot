package ru.pelse.myjira.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pelse.myjira.entity.Activity;
import ru.pelse.myjira.entity.Image;
import ru.pelse.myjira.entity.Task;
import ru.pelse.myjira.repository.ActivityRepository;
import ru.pelse.myjira.service.ActivityService;
import ru.pelse.myjira.service.ImageService;
import ru.pelse.myjira.service.NoEntityException;
import ru.pelse.myjira.service.TaskService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ImageService imageService;

    @Value("${ph.upload.path}")
    private String uploadPath;

    @GetMapping("/create/{id}")
    public String createActivity(@PathVariable("id") String taskId, Model model) {
        try {
            Task task = taskService.getTaskById(taskId);
            model.addAttribute("titleName", "Заполнить сделанное");
            model.addAttribute("task", task);
            return "activity/create";
        } catch (NoEntityException e) {
            e.printStackTrace();
            model.addAttribute("errorText", "Ошибка получения данных");
            return "error";
        }
    }

    @PostMapping("/create")
    //если одинаковое имя переменной и имя параметра для @RequestParam => без атрибута name
    private String createActivity(@RequestParam String action,
                                  @RequestParam String currentResult,
                                  @RequestParam String state,
                                  @RequestParam String taskId,
                                  @RequestParam(value = "file", required = false) MultipartFile file,
                                  Model model) {
        try {
            Activity activity = activityService.createActivity(taskId, action, currentResult, state);
            try {
                this.saveFile(activity, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "redirect:/task/" + taskId;
        } catch (NoEntityException e) {
            e.printStackTrace();
            model.addAttribute("errorText", "Не удалось создать запись");
            return "error";
        }
    }

    @GetMapping("/{id}")
    public String activity(@PathVariable("id") String activityId, Model model) {
        try {
            Activity activity = activityService.getActivityById(activityId);
            Task task = activity.getTask();
            Iterable<Activity> activities = activityRepository.findByTask(task);

            model.addAttribute("titleName", "Действие ");
            model.addAttribute("activity", activity);
            model.addAttribute("activities", activities);
            model.addAttribute("task", task);
            return "activity/activity";
        } catch (NoEntityException e) {
            e.printStackTrace();
            model.addAttribute("errorText", "Не удалось перейти к этому действию");
            return "error";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        try {
            Activity activity = activityService.getActivityById(id);
            model.addAttribute("activity", activity);
            return "activity/edit";
        } catch (NoEntityException e) {
            e.printStackTrace();
            model.addAttribute("errorText", "Не удалось перейти к редактированию действия");
            return "error";
        }
    }

    @PostMapping("/edit/{id}")
    public String edit(@RequestParam String action,
                       @RequestParam String currentResult,
                       @RequestParam String state,
                       @RequestParam(value = "file", required = false) MultipartFile file,
                       @PathVariable("id") String activityId,
                       Model model) {
        try {
            Activity editedActivity = activityService.edit(activityId, action, currentResult, state);
            try {
                this.saveFile(editedActivity, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "redirect:/activity/" + editedActivity.getId();
        } catch (NoEntityException e) {
            e.printStackTrace();
            model.addAttribute("errorText", "Не удалось редактировать действие");
            return "error";
        }
    }

    private void saveFile(@Valid Activity activity,
                          @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String filename = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + filename));
            Image savedImg = imageService.saveImage(filename, activity);
            Activity savedActivity = activityService.addImage(activity, savedImg);
        }
    }
}
