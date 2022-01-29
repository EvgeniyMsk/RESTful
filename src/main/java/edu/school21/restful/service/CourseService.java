package edu.school21.restful.service;

import edu.school21.restful.models.Course;
import edu.school21.restful.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public void createCourse(Course course) {
        courseRepository.saveAndFlush(course);
    }

    public Course getCourseById(Long id) {
        return courseRepository.getCourseById(id);
    }

    public void updateCourse(Course course) {
        courseRepository.saveAndFlush(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteCourseById(id);
    }
}
