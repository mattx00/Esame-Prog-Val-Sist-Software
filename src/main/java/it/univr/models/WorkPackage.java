package it.univr.models;

import jakarta.persistence.*;

@Entity
@Table(name="work_packages")
public class WorkPackage {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    //private String firstName; => come e quando occorre
    //private String lastName;  => come e quando occorre
    private String name;

    // Relations

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    protected WorkPackage() {}

    public WorkPackage(String name) {
        this.name = name;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
