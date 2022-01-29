package edu.school21.restful.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    @OneToOne
    private User teacher;
}
