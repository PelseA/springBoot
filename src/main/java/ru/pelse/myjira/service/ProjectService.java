package ru.pelse.myjira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pelse.myjira.entity.Activity;
import ru.pelse.myjira.entity.Project;
import ru.pelse.myjira.entity.User;
import ru.pelse.myjira.repository.ActivityRepository;
import ru.pelse.myjira.repository.ProjectRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public Project getProjectById(String id) throws NoEntityException {
        return projectRepository
                .findById(Integer.parseInt(id))
                .orElseThrow(() -> new NoEntityException(id));
    }

    private ArrayList<LocalDate> getDaysFromStartToDeadline(Project project) {
        if (project.getDeadline() != null) {
            long days =  ChronoUnit.DAYS.between(project.getStart(), project.getDeadline());
            ArrayList<LocalDate> daysList= new ArrayList<>();
            //в сет положим даты с  deadline до сегодняшней включительно
            for (long i = 0; i <= days; i++) {
                daysList.add(project.getStart().plusDays(i));
            }
            return daysList;
        }
        return null;
    }

    public TreeMap<LocalDate, String> getCheckedActiveDays(Project project) {
        ArrayList<LocalDate> daysList = this.getDaysFromStartToDeadline(project);
        if (daysList != null) {
            List<Activity> allActivitiesInProject = activityRepository.findByProjectId(project.getId());
            ArrayList<LocalDate> activeDays = new ArrayList<>();

            for (Activity activity: allActivitiesInProject) {
                activeDays.add(activity.getDate().toLocalDate());
            }

            TreeMap<LocalDate, String> checkedActiveDays = new TreeMap<>();
            for (LocalDate day: daysList) {
                for (LocalDate activeDay: activeDays) {
                    if (day.equals(activeDay)) {
                        checkedActiveDays.put(day, "ph-done");
                    } else {
                        checkedActiveDays.put(day, "");
                    }
                }
            }
            return checkedActiveDays;
        }
        return null;
    }

    public Project editProject(User user, Project project, String id) throws NoEntityException {
        project.setId(Integer.parseInt(id));
        project.setUser(user);
        project.setStart(this.getProjectById(id).getStart());
        return projectRepository.save(project);
    }

}
