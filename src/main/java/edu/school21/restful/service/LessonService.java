package edu.school21.restful.service;

import edu.school21.restful.models.Lesson;
import edu.school21.restful.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
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
}
