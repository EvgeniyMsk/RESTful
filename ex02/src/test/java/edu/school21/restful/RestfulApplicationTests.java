package edu.school21.restful;

import edu.school21.restful.entity.Course;
import edu.school21.restful.exceptions.NotFoundException;
import edu.school21.restful.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.RestMediaTypes;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs("target/generated-snippets")
class RestfulApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    public void init() {
        Course course = courseRepository.findById(3L).orElseThrow(NotFoundException::new);
        course.setState("DRAFT");
        courseRepository.save(course);
    }

    @Test
    public void publishTest() throws Exception {
        ResultActions resultActions = mockMvc.perform(put("/courses/3/publish")
        .accept(RestMediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        resultActions.andDo(document("publishTest"));
    }
}
