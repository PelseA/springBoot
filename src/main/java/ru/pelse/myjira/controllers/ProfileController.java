package ru.pelse.myjira.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.pelse.myjira.entity.Project;
import ru.pelse.myjira.entity.User;
import ru.pelse.myjira.repository.ProjectRepository;
import ru.pelse.myjira.repository.UserRepository;

@Controller
public class ProfileController {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal User user, Model model) {
        //вывести проекты авторизованного пользователя
        Iterable<Project> projects = projectRepository.findByUser(user);
        model.addAttribute("projects", projects);
        model.addAttribute("firstName", user.getFirstname());
        return "profile";
    }
}
