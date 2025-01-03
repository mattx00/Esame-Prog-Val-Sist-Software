package it.univr.services;

import it.univr.component.PasswordHash;
import it.univr.models.PasswordResetToken;
import it.univr.models.Project;
import it.univr.repositories.ProjectRepository;
import it.univr.repositories.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // boolean?
    public Project projectExists(int id) {
        return projectRepository.findById(id);
    }

    public List<Project> searchByName(String name) {
        return projectRepository.findByName(name);
    }

    public void save(Project project) {
        System.out.println("Trying to save project:" + project.toString());
        projectRepository.save(project);
    }
}
