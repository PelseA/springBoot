package ru.pelse.myjira.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.pelse.myjira.entity.Project;
import ru.pelse.myjira.repository.ProjectRepository;

@Controller
public class ProfileController {
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/profile")
    public String profile(Model model) {
        //вывести проекты
        Iterable<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);
        return "profile";
    }
}
