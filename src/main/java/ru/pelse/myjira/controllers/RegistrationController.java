package ru.pelse.myjira.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pelse.myjira.entity.User;
import ru.pelse.myjira.service.UserService;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("title", "Регистрация в 'Pelse-helper'");
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String password,
                          @RequestParam String password2,
                          @RequestParam(required = false) String birth,
                          User user,
                          Model model) {
        if (!password.equals(password2)) {
            model.addAttribute("mismatch", "Пароли не совпадают");
            model.addAttribute("user", user);
            return "registration";
        }
        if (birth != null) {
            //todo проверка возраста, например с 6 лет
            System.out.println(birth);
        }
        //если не смогли добавить пользователя => пользователь существует
        User savedUser = userService.addUser(user);
        if (savedUser == null) {
            model.addAttribute("userExists", "Этот email уже зарегистрирован");
            return "registration";
        }
        String email = savedUser.getUsername();
        model.addAttribute("confirm", "На электронную почту " + email + " было отправлено письмо. \r" +
                " Подтвердите, пожалуйста, регистрацию, перейдя по ссылке в письме.");
        return "login";
    }

    //пользователь подтверждает регистрацию переходя по ссылке из письма
    @GetMapping("/activate/{code}")
    //@PathVariable - исп. для работы с параметрами, передаваемыми в строке запроса
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("afterConfirm", "Спасибо, что подтвердили регистрацию! Авторизуйтесь с вашими именем и паролем.");
        } else {
            model.addAttribute("afterConfirm", "Неверный код активации");
        }

        return "login";
    }

}
