package ru.pelse.myjira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pelse.myjira.entity.Project;
import ru.pelse.myjira.entity.Task;
import ru.pelse.myjira.repository.ProjectRepository;
import ru.pelse.myjira.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectService projectService;

    public Task getTaskById(String id) throws NoEntityException {
        return taskRepository
                .findById(Integer.parseInt(id))
                .orElseThrow(() -> new NoEntityException(id));
    }

    public Task createTask(String projectId, String title, String description) throws NoEntityException {
        Project project = projectService.getProjectById(projectId);
        Task task = new Task(
                title,
                description,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                project
        );
        return taskRepository.save(task);
    }
}
