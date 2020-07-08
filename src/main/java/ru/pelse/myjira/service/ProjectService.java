package ru.pelse.myjira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pelse.myjira.entity.Project;
import ru.pelse.myjira.repository.ProjectRepository;

import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project getProjectById(String id) throws NoEntityException {
        return projectRepository
                .findById(Integer.parseInt(id))
                .orElseThrow(() -> new NoEntityException(id));
    }

    public Project editProject(String projectId, String title, String description) throws NoEntityException {
        Project project = this.getProjectById(projectId);
        project.setTitle(title);
        project.setDescription(description);
        return projectRepository.save(project);
    }
}
