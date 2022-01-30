package edu.school21.restful.models;

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
    @OneToMany
    private Set<User> teachers;
    @OneToMany
    private Set<User> students;
    private String description;
    @OneToMany
    private Set<Lesson> lessons;

    public Course() {
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
        this.lessons = new HashSet<>();
    }

    public Course(Date startDate, Date endDate, String name, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.description = description;
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
        this.lessons = new HashSet<>();
    }
}
