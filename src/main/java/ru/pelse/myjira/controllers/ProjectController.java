package ru.pelse.myjira.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pelse.myjira.entity.Project;
import ru.pelse.myjira.entity.User;
import ru.pelse.myjira.repository.ProjectRepository;

import java.util.Optional;

@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/create")
    public String addProject(Model model) {
        model.addAttribute("title", "Новый проект");
        return "project/create";
    }

    @PostMapping("/create")
    public String addProject(
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            @RequestParam String description,
            Model model) {
        Project project = new Project(title, description, user);
        projectRepository.save(project);

        return "redirect:/profile";
    }

    @GetMapping("/{id}")
    public String project(@RequestParam String id, Model model) {
        Optional<Project> optionalProject = projectRepository.findById(Integer.valueOf(id));
        if (!optionalProject.isPresent()) {
            model.addAttribute("noProject", "Такого проекта нет");
            model.addAttribute("title", "Ошибка");
        } else {
            Project project = optionalProject.get();
            model.addAttribute("project", project);
            model.addAttribute("title", project.getTitle());
        }
        return "project/project";
    }

}
