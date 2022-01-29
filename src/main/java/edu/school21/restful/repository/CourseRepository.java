package edu.school21.restful.repository;

import edu.school21.restful.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course getCourseById(Long id);
    void deleteCourseById(Long id);
}
