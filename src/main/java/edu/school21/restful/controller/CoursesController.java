package edu.school21.restful.controller;

import edu.school21.restful.models.Course;
import edu.school21.restful.models.Lesson;
import edu.school21.restful.models.User;
import edu.school21.restful.models.dto.CourseDto;
import edu.school21.restful.models.dto.LessonDto;
import edu.school21.restful.request.PublishedCourse;
import edu.school21.restful.request.UserRequest;
import edu.school21.restful.service.CourseService;
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

    @Autowired
    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @Operation(
            summary = "getAllCourses",
            description = "Возвращает все имеющиеся курсы [есть пагинация, сортировка по ID]"
    )
    public ResponseEntity<Page<Course>> getAllCourses(@PageableDefault(sort = "id", size = 5) Pageable pageable) {
        try {
            Page<Course> coursePage = courseService.findAll(pageable);
            return ResponseEntity.ok(coursePage);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @Operation(
            summary = "addCourse",
            description = "Добавить курс"
    )
    public ResponseEntity<Course> addCourse(@RequestBody CourseDto courseDto) {
        try {
            Course course = courseService.createCourse(courseDto);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
            return ResponseEntity.ok(course);
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
    public ResponseEntity<String> deleteCourse(@PathVariable("course-id") String courseId) {
        try {
            courseService.deleteCourseById(courseId);
            return ResponseEntity.ok("Successfully deleted!");
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
            return ResponseEntity.ok(courseService.getLessonsByCourse(Long.parseLong(courseId), pageable));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{course-id}/lessons")
    @Operation(
            summary = "addLessonToCourse",
            description = "Добавляет урок в курс по ID [есть пагинация, сортировка по ID]"
    )
    public ResponseEntity<Page<Lesson>> addLessonToCourse(@PathVariable("course-id") String courseId,
                                                          @RequestBody LessonDto lessonDto,
                                                          @PageableDefault(sort = "id", size = 5) Pageable pageable) {
        try {
            courseService.addLesson(courseId, lessonDto);
            return ResponseEntity.ok(courseService.getLessonsByCourse(Long.parseLong(courseId), pageable));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{course-id}/lessons/{lesson-id}")
    @Operation(
            summary = "updateLessonInCourse",
            description = "Изменяет урок в курсе по ID"
    )
    public ResponseEntity<Lesson> updateLessonInCourse(@PathVariable("course-id") String courseId,
                                                       @PathVariable("lesson-id") String lessonId,
                                                       @RequestBody LessonDto lessonDto) {
        try {
            return ResponseEntity.ok(courseService.updateLessonInCourse(lessonId, lessonDto));
        } catch (Exception c) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{course-id}/lessons/{lesson-id}")
    @Operation(
            summary = "deleteLessonFromCourse",
            description = "Добавляет урок в курс по ID [есть пагинация, сортировка по ID]"
    )
    public ResponseEntity<String> deleteLessonFromCourse(@PathVariable("course-id") String courseId,
                                                         @PathVariable("lesson-id") String lessonId) {
        try {
            courseService.deleteLessonFromCourse(courseId, lessonId);
            return ResponseEntity.ok("Successfully deleted!");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{course-id}/students")
    @Operation(
            summary = "getStudentsByCourse",
            description = "Возвращает студентов курса по ID [есть пагинация, сортировка по ID]"
    )
    public ResponseEntity<Page<User>> getStudentsByCourse(@PathVariable("course-id") String courseId,
                                                          @PageableDefault(sort = "id", size = 5) Pageable pageable) {
        try {
            return ResponseEntity.ok(courseService.getStudentsByCourse(courseId, pageable));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/{course-id}/students")
    @Operation(
            summary = "addStudentToCourse",
            description = "Добавляет студента на курс по ID [есть пагинация, сортировка по ID]"
    )
    public ResponseEntity<Page<User>> addStudentToCourse(@PathVariable("course-id") String courseId,
                                                         @RequestBody UserRequest userId,
                                                         @PageableDefault(sort = "id", size = 5) Pageable pageable) {
        try {
            courseService.addStudentToCourse(courseId, userId.getUserId(), pageable);
            return ResponseEntity.ok(courseService.getStudentsByCourse(courseId, pageable));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{course-id}/students/{student-id}")
    @Operation(
            summary = "addStudentToCourse",
            description = "Удаляет студента с курса по ID [есть пагинация, сортировка по ID]"
    )
    public ResponseEntity<String> deleteStudentFromCourse(@PathVariable("course-id") String courseId,
                                                          @PathVariable("student-id") String studentId) {
        try {
            courseService.deleteStudentFromCourse(courseId, studentId);
            return ResponseEntity.ok("Successfully deleted!");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{course-id}/teachers")
    @Operation(
            summary = "getTeachersByCourse",
            description = "Возвращает студентов курса по ID [есть пагинация, сортировка по ID]"
    )
    public ResponseEntity<Page<User>> getTeachersByCourse(@PathVariable("course-id") String courseId,
                                                          @PageableDefault(sort = "id", size = 5) Pageable pageable) {
        try {
            return ResponseEntity.ok(courseService.getTeachersByCourse(courseId, pageable));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{course-id}/teachers")
    @Operation(
            summary = "getTeachersByCourse",
            description = "Добавляет студента на курса по ID [есть пагинация, сортировка по ID]"
    )
    public ResponseEntity<Page<User>> addTeacherToCourse(@PathVariable("course-id") String courseId,
                                                         @RequestBody UserRequest userId,
                                                         @PageableDefault(sort = "id", size = 5) Pageable pageable) {
        try {
            courseService.addTeacherToCourse(courseId, userId.getUserId(), pageable);
            return ResponseEntity.ok(courseService.getTeachersByCourse(courseId, pageable));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{course-id}/teachers/{teacher-id}")
    @Operation(
            summary = "deleteTeacherFromCourse",
            description = "Добавляет студента на курса по ID [есть пагинация, сортировка по ID]"
    )
    public ResponseEntity<String> deleteTeacherFromCourse(@PathVariable("course-id") String courseId,
                                                          @PathVariable("teacher-id") String teacher) {
        try {
            courseService.deleteTeacherFromCourse(courseId, teacher);
            return ResponseEntity.ok("Successfully deleted!");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{course-id}/publish")
    @Operation(
            summary = "publishCourseById",
            description = "Публикует курс по ID"
    )
    public ResponseEntity<PublishedCourse> publishCourseById(@PathVariable("course-id") String courseId) {
        try {
            return ResponseEntity.ok(courseService.publishCourseById(courseId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
