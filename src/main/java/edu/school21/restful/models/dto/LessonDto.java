package edu.school21.restful.models.dto;

import lombok.Data;

@Data
public class LessonDto {
    private String startTime;
    private String finishTime;
    private String dayOfWeek;
    private Long teacherId;
}
