package edu.school21.restful.repository;

import edu.school21.restful.models.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson getLessonById(Long id);
    void deleteLessonsById(Long id);
}
