package ru.pelse.myjira.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class OAuth2Controller {
    @GetMapping("/test")
    public String testPage() {
        return "this is test page";
    }

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            System.out.println("================ principal :" + principal);
        }
        //todo если email == null то предложить через другую соцсеть (запрашиваемые учетные данные недоступны)
        //проверка в бд по email => регистрация если нет профиля
        try {
            return Collections.singletonMap("email", principal.getAttribute("email"));
        } catch(NullPointerException e) {
            System.out.println(" catched NullPointerException============");
        }
        return null;
    }
}
