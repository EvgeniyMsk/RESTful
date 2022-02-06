package edu.school21.restful.repository;

import edu.school21.restful.entity.Lesson;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LessonRepository extends PagingAndSortingRepository<Lesson, Long> {
}
