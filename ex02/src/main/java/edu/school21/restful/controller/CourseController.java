package edu.school21.restful.controller;

import edu.school21.restful.entity.Course;
import edu.school21.restful.exceptions.NotFoundException;
import edu.school21.restful.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class CourseController {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @RequestMapping(value = "/courses/{courseId}/publish", method = RequestMethod.PUT)
    @ResponseBody
    public PersistentEntityResource publish(@PathVariable("courseId") Long courseId,
                                            PersistentEntityResourceAssembler asm) {
        Course course = courseRepository.findById(courseId).orElseThrow(NotFoundException::new);
        course.publish();
        return asm.toFullResource(courseRepository.save(course));
    }
}
