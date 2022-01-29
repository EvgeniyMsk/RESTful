package edu.school21.restful.controller;

import edu.school21.restful.models.Course;
import edu.school21.restful.service.CourseService;
import edu.school21.restful.service.LessonService;
import edu.school21.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CoursesController {
    private final CourseService courseService;
    private final UserService userService;
    private final LessonService lessonService;

    @Autowired
    public CoursesController(CourseService courseService, UserService userService, LessonService lessonService) {
        this.courseService = courseService;
        this.userService = userService;
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return new ResponseEntity<>(courseService.findAll(), HttpStatus.OK);
    }

//    @PostMapping
//    public HttpStatus getAllCourses() {
//        return new H<>(courseService.findAll(), HttpStatus.OK);
//    }

//    @GetMapping("/{course-id}")
//
//    @PutMapping("/{course-id}")
//
//    @DeleteMapping("/{course-id}")
//
//    @GetMapping("/{course-id}/lessons")
//
//    @PostMapping("/{course-id}/lessons")
//
//    @PutMapping("/{course-id}/lessons/{lesson-id}")
//
//    @DeleteMapping("/{course-id}/lessons/{lesson-id}")
//
//    @GetMapping("/{course-id}/students")
//
//    @PostMapping("/{course-id}/students")
//
//    @DeleteMapping("/{course-id}/students/{student-id}")
//
//    @GetMapping("/{course-id}/teachers")
//
//    @PostMapping("/{course-id}/teachers")
//
//    @DeleteMapping("/{course-id}/teachers/{teacher-id}")
}
