package ru.pelse.myjira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pelse.myjira.entity.Activity;
import ru.pelse.myjira.entity.State;
import ru.pelse.myjira.entity.Task;
import ru.pelse.myjira.repository.ActivityRepository;
import ru.pelse.myjira.repository.StateRepository;
import ru.pelse.myjira.repository.TaskRepository;

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

}
