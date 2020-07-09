package ru.pelse.myjira.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pelse.myjira.entity.Project;
import ru.pelse.myjira.entity.Task;
import ru.pelse.myjira.entity.User;
import ru.pelse.myjira.repository.ProjectRepository;
import ru.pelse.myjira.repository.TaskRepository;
import ru.pelse.myjira.service.NoEntityException;
import ru.pelse.myjira.service.ProjectService;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectService projectService;

    @GetMapping("/create")
    public String addProject(Model model) {
        model.addAttribute("title", "Новый проект");
        return "project/create";
    }

    @PostMapping("/create")
    public String addProject(@AuthenticationPrincipal User user, Project project) {
        project.setUser(user);
        project.setStart(LocalDate.now());
        projectRepository.save(project);

        return "redirect:/profile";
    }

    @GetMapping("/{id}")
    public String project(@PathVariable("id") String id, Model model) {
        ArrayList<Task> tasks = (ArrayList) taskRepository.findByProjectId(Integer.parseInt(id));
        try {
            Project project = projectService.getProjectById(id);
            ArrayList<LocalDate> days = projectService.getDaysFromStartToDeadline(project);
            if (days != null) {
                model.addAttribute("days", days);
            }
            model.addAttribute("project", project);
            model.addAttribute("tasks", tasks);
            model.addAttribute("title", project.getTitle());
            return "project/project";
        } catch (NoEntityException e) {
            model.addAttribute("errorText", "Проект не найден");
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable("id") String id, Model model) {
        try {
            Project project = projectService.getProjectById(id);
            model.addAttribute("project", project);
            model.addAttribute("titleName", project.getTitle());
            return "project/edit";
        } catch (NoEntityException e) {
            model.addAttribute("errorText", "Проект для редактирования не найден");
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/edit/{id}")
    public String editProject(@PathVariable("id") String id,
                              @RequestParam String title,
                              @RequestParam String description,
                              Model model) {
        try {
            Project savedProject = projectService.editProject(id, title, description);
            return project(String.valueOf(savedProject.getId()), model);
        } catch (NoEntityException e) {
            e.printStackTrace();
            model.addAttribute("errorText", "Не удалось редактировать проект");
            return "error";
        }
    }

}
