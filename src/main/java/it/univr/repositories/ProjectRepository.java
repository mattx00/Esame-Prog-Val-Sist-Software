package it.univr.repositories;

import it.univr.models.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findById(int id);
    List<Project> findByName(String name);
}
