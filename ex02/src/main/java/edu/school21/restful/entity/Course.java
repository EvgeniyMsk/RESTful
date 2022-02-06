package edu.school21.restful.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startDate;
    private Date endDate;
    private String name;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    private Set<User> teachers;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    private Set<User> students;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private Set<Lesson> lessons;
    private String state;

    public Course() {
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
        this.lessons = new HashSet<>();
        this.state = "DRAFT";
    }

    public Course(Date startDate, Date endDate, String name, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.state = "DRAFT";
        this.description = description;
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
        this.lessons = new HashSet<>();
    }

    public void publish() {
        this.state = "PUBLISHED";
    }
}
