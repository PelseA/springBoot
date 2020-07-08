package ru.pelse.myjira.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pelse.myjira.entity.Activity;
import ru.pelse.myjira.entity.Project;
import ru.pelse.myjira.entity.Task;
import ru.pelse.myjira.repository.ActivityRepository;
import ru.pelse.myjira.service.ActivityService;
import ru.pelse.myjira.service.NoEntityException;
import ru.pelse.myjira.service.TaskService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Controller
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ActivityService activityService;

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
                                  Model model) {
        try {
            activityService.createActivity(taskId, action, currentResult, state);
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
            model.addAttribute("titleName", "Действие ");
            model.addAttribute("activity", activity);
            return "activity/activity";
        } catch (NoEntityException e) {
            e.printStackTrace();
            model.addAttribute("errorText", "Не удалось перейти к этому действию");
            return "error";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit() {
        return "activity/edit";
    }
}
