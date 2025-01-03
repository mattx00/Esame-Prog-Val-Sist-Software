package it.univr.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) 
  private Long id; 
  private String name; 
  private String description; 
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate; 
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @OneToMany(mappedBy="project")
  private Set<WorkPackage> workPackages;

  // Getters and Setters
  
  public long getId() {return id;}

  public void setId(long id) {this.id = id;}

  public String getName() {return name;}

  public void setName(String name) {this.name = name;}

  public String getDescription() {return description;}

  public void setDescription(String description) {this.description = description;}

  public LocalDate getStartDate() {return startDate;}

  public void setStartDate(LocalDate startDate) {this.startDate = startDate;}

  public LocalDate getEndDate() {return endDate;}

  public void setEndDate(LocalDate endDate) {this.endDate = endDate;}

  @Override
  public String toString() {
    return "Project [id=" + id + ", name=" + name + ", description=" + description + ", startDate=" + startDate
        + ", endDate=" + endDate + "]";
  }

  
  
}
