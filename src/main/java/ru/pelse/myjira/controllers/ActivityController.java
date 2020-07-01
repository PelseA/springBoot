package ru.pelse.myjira.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pelse.myjira.entity.Activity;
import ru.pelse.myjira.entity.Project;
import ru.pelse.myjira.repository.ActivityRepository;

import java.util.Optional;

@Controller
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping("/{id}")
    public String activity(@RequestParam String id, Model model) {
        Optional<Activity> optionalActivity = activityRepository.findById(Integer.valueOf(id));
        if (!optionalActivity.isPresent()) {
            model.addAttribute("noActivity", "Такой записи нет");
            model.addAttribute("title", "Ошибка");
        } else {
            Activity activity = optionalActivity.get();
            model.addAttribute("activity", activity);
            model.addAttribute("title", activity.getTitle());
        }
        return "activity/activity";
    }

    @GetMapping("/edit/{id}")
    public String editActivity(@RequestParam String id, Model model) {
        Optional<Activity> optionalActivity = activityRepository.findById(Integer.valueOf(id));
        if(!optionalActivity.isPresent()) {
            model.addAttribute("noActivity", "Не удалось найти запись");
            model.addAttribute("title", "Ошибка");
        } else {
            Activity activity = optionalActivity.get();
            model.addAttribute("activity", activity);
        }
        return "activity/edit";
    }
}
