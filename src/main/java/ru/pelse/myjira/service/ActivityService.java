package ru.pelse.myjira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pelse.myjira.entity.Activity;
import ru.pelse.myjira.entity.Image;
import ru.pelse.myjira.entity.State;
import ru.pelse.myjira.entity.Task;
import ru.pelse.myjira.repository.ActivityRepository;
import ru.pelse.myjira.repository.StateRepository;
import ru.pelse.myjira.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private StateService stateService;

    public Activity getActivityById(String id) throws NoEntityException {
        return activityRepository
                .findById(Integer.parseInt(id))
                .orElseThrow(() -> new NoEntityException(id));
    }

    public Activity createActivity(String taskId, String action, String currentResult, String state) throws NoEntityException {
        Task task = taskService.getTaskById(taskId);
        State stateEntity = stateService.getStateByValue(state);
        Activity activity = new Activity(action, currentResult, stateEntity, task);
        return activityRepository.save(activity);
    }

    public Activity edit(String activityId, String action, String currentResult, String state) throws NoEntityException {
        Activity activity = this.getActivityById(activityId);
        State stateEntity = stateService.getStateByValue(state);
        activity.setAction(action);
        activity.setCurrentResult(currentResult);
        activity.setState(stateEntity);
        return activityRepository.save(activity);
    }

    public Activity addImage(Activity activity, Image image) {
        ArrayList<Image> images = new ArrayList<>();
        images.add(image);
        activity.setImages(images);
        return activityRepository.save(activity);
    }

}
