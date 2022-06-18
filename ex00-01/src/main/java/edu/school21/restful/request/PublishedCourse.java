package edu.school21.restful.request;

import edu.school21.restful.models.Course;
import lombok.Data;

@Data
public class PublishedCourse {
    private String title;
    private String description;
    private String state;
    public PublishedCourse(Course course) {
        this.title = course.getName();
        this.description = course.getDescription();
        this.state = "PUBLISHED";
    }
}
