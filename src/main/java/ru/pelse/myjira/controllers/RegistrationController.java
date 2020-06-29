package ru.pelse.myjira.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.pelse.myjira.entity.User;
import ru.pelse.myjira.service.UserService;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("title", "Регистрация в 'MyJira'");
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        //если не смогли добавить пользователя => пользователь существует
        if (!userService.addUser(user)) {
            model.addAttribute("userExists", "Этот email уже зарегистрирован");
            return "registration";
        }

        return "redirect:/login";
    }

    //пользователь подтверждает регистрацию переходя по ссылке из письма
    @GetMapping("/activate/{code}")
    //@PathVariable - исп. для работы с параметрами, передаваемыми в строке запроса
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("afterActivation", "Спасибо, что подтвердили регистрацию! Авторизуйтесь с вашими именем и паролем.");
        } else {
            model.addAttribute("afterActivation", "Этот электронный адрес уже подтвердил регистрацию");
        }

        return "login";
    }

}
