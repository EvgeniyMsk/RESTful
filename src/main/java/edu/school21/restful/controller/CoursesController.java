package edu.school21.restful.controller;

import edu.school21.restful.models.Course;
import edu.school21.restful.models.Lesson;
import edu.school21.restful.models.dto.CourseDto;
import edu.school21.restful.service.CourseService;
import edu.school21.restful.service.LessonService;
import edu.school21.restful.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/courses")
@Tag(name="Контроллер курсов", description="Обеспечивает управление курсами")
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
    @Operation(
            summary = "getAllCourses",
            description = "Возвращает все имеющиеся курсы [есть пагинация, сортировка по ID]"
    )
    public ResponseEntity<Page<Course>> getAllCourses(@PageableDefault(sort = "id", size = 5) Pageable pageable) {
        return new ResponseEntity<>(courseService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "addCourse",
            description = "Добавить курс"
    )
    public ResponseEntity<Course> addCourse(@RequestBody CourseDto courseDto) {
        try {
            Course course = courseService.createCourse(courseDto);
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @GetMapping("/{course-id}")
    @Operation(
            summary = "getCourse",
            description = "Возвращает курс по ID"
    )
    public ResponseEntity<Course> getCourse(@PathVariable ("course-id") String courseId) {
        try {
            Course course = courseService.getCourseById(courseId);
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{course-id}")
    @Operation(
            summary = "updateCourse",
            description = "Обновление информации о курсе по ID"
    )
    public ResponseEntity<Course> updateCourse(@PathVariable ("course-id") String courseId,
                                               @RequestBody CourseDto courseDto) {
        try {
            Course course = courseService.updateCourse(courseDto, courseId);
            return new ResponseEntity<>(course, HttpStatus.OK);
        }
        catch (Exception c) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{course-id}")
    @Operation(
            summary = "deleteCourse",
            description = "Удаление курса по ID"
    )
    public ResponseEntity<Course> deleteCourse(@PathVariable("course-id") String courseId) {
        try {
            courseService.deleteCourse(courseId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{course-id}/lessons")
    @Operation(
            summary = "getLessonsByCourse",
            description = "Возвращает уроки из курса по ID [есть пагинация, сортировка по ID]"
    )
    public ResponseEntity<Page<Lesson>> getLessonsByCourse(@PathVariable ("course-id") String courseId,
                                                           @PageableDefault(sort = "id", size = 5) Pageable pageable)
    {
        try {
            return new ResponseEntity<>(lessonService.getLessonsByCourse(courseId, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/{course-id}/lessons")
//    public ResponseEntity<Lesson> addLessonToCourse(LessonDto lessonDto) {
//
//    }
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
