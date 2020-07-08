package ru.pelse.myjira.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pelse.myjira.entity.Activity;
import ru.pelse.myjira.entity.Task;
import ru.pelse.myjira.entity.Project;
import ru.pelse.myjira.repository.ActivityRepository;
import ru.pelse.myjira.repository.TaskRepository;
import ru.pelse.myjira.repository.ProjectRepository;
import ru.pelse.myjira.service.NoEntityException;
import ru.pelse.myjira.service.ProjectService;
import ru.pelse.myjira.service.TaskService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/create/{id}")
    public String createTask(@PathVariable("id") String projectId, Model model) {
        try {
            Project project = projectService.getProjectById(projectId);
            model.addAttribute("project", project);
            return "task/create";
        } catch (NoEntityException e) {
            e.printStackTrace();
            model.addAttribute("errorText", "Ошибка перенаправления");
            return "error";
        }
    }

    @PostMapping("/create")
    public String createTask(@RequestParam String projectId,
                             @RequestParam String title,
                             @RequestParam String description,
                             Model model) {
        try {
            return "redirect:/task/" + taskService.createTask(projectId, title, description).getId();
        } catch (NoEntityException e) {
            e.printStackTrace();
            model.addAttribute("errorText", "Не удалось создать запись");
            return "error";
        }
    }

    @GetMapping("/{id}")
    public String task(@PathVariable("id") String id, Model model) {
        try {
            Task task = taskService.getTaskById(id);
            Project project = projectRepository.findByTasks(task);
            Iterable<Task> tasks = taskRepository.findByProjectId(project.getId());
            Iterable<Activity> activities = activityRepository.findByTask(task);
            model.addAttribute("task", task);
            model.addAttribute("tasks", tasks);
            model.addAttribute("project", project);
            model.addAttribute("activities", activities);
            return "task/task";
        } catch (NoEntityException e) {
            e.printStackTrace();
            model.addAttribute("errorText", "Не удалось получить записи");
            return "error";
        }
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable("id") String id, Model model) {
        try {
            Task task = taskService.getTaskById(id);
            model.addAttribute("task", task);
            return "task/edit";
        } catch (NoEntityException e) {
            e.printStackTrace();
            model.addAttribute("errorText", "Не удалось получить задачу для редактирования");
            return "error";
        }

    }

//    @PostMapping("/edit/{id}")
//    public String editTask() {
//        return "task/" + id;
//    }
}
