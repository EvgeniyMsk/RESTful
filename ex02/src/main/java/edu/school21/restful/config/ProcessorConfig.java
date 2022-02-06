package edu.school21.restful.config;

import edu.school21.restful.controller.CourseController;
import edu.school21.restful.entity.Course;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProcessorConfig implements RepresentationModelProcessor<EntityModel<Course>> {

    @Override
    public EntityModel<Course> process(EntityModel<Course> model) {
        if (model.getContent().getState().equals("DRAFT"))
            model.add(linkTo(methodOn(CourseController.class).publish(model.getContent().getId(), null))
            .withRel("publish"));
        return model;
    }
}
