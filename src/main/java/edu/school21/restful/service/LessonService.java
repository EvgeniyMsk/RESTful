package edu.school21.restful.service;

import edu.school21.restful.models.Course;
import edu.school21.restful.models.Lesson;
import edu.school21.restful.repository.CourseRepository;
import edu.school21.restful.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    public void createLesson(Lesson lesson) {
        lessonRepository.saveAndFlush(lesson);
    }

    public Lesson getLessonById(Long id) {
        return lessonRepository.getLessonById(id);
    }

    public void updateLesson(Lesson lesson) {
        lessonRepository.saveAndFlush(lesson);
    }

    public void deleteLesson(Long id) {
        lessonRepository.deleteLessonsById(id);
    }

    public Page<Lesson> getLessonsByCourse(String courseId, Pageable pageable) {
        Course course = courseRepository.getCourseById(Long.parseLong(courseId));
        return new PageImpl<>(new ArrayList<>(course.getLessons()), pageable, course.getLessons().size());
    }
}
