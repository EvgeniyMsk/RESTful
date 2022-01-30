package edu.school21.restful.service;

import edu.school21.restful.models.Course;
import edu.school21.restful.models.dto.CourseDto;
import edu.school21.restful.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    public Course createCourse(CourseDto courseDto) {
        Course course = new Course(courseDto.getStartDate(), courseDto.getEndDate(), courseDto.getName(), courseDto.getDescription());
        return courseRepository.saveAndFlush(course);
    }

    public Course getCourseById(String id) {
        return courseRepository.getCourseById(Long.parseLong(id));
    }

    public Course updateCourse(CourseDto courseDto, String id) {
        Course course = courseRepository.getCourseById(Long.parseLong(id));
        course.setStartDate(courseDto.getStartDate());
        course.setEndDate(courseDto.getEndDate());
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        return courseRepository.saveAndFlush(course);
    }

    public void deleteCourse(String id) {
        courseRepository.deleteCourseById(Long.parseLong(id));
    }
}
