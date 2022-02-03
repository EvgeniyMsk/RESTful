package edu.school21.restful.service;

import edu.school21.restful.exceptions.NotFoundException;
import edu.school21.restful.models.Course;
import edu.school21.restful.models.Lesson;
import edu.school21.restful.models.User;
import edu.school21.restful.models.dto.CourseDto;
import edu.school21.restful.models.dto.LessonDto;
import edu.school21.restful.repository.CourseRepository;
import edu.school21.restful.repository.LessonRepository;
import edu.school21.restful.repository.UserRepository;
import edu.school21.restful.request.PublishedCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, LessonRepository lessonRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    public Course createCourse(CourseDto courseDto) {
        Course course = new Course(courseDto.getStartDate(), courseDto.getEndDate(), courseDto.getName(), courseDto.getDescription());
        return courseRepository.saveAndFlush(course);
    }

    public Course getCourseById(String id) {
        return courseRepository.findById(Long.parseLong(id)).orElseThrow(NotFoundException::new);
    }

    public Course updateCourse(CourseDto courseDto, String id) {
        Course course = courseRepository.findById(Long.parseLong(id)).orElseThrow(NotFoundException::new);
        course.setStartDate(courseDto.getStartDate());
        course.setEndDate(courseDto.getEndDate());
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        return courseRepository.saveAndFlush(course);
    }

    public void deleteCourseById(String courseId) {
        courseRepository.deleteById(Long.parseLong(courseId));
    }


    public Page<Lesson> getLessonsByCourse(Long courseId, Pageable pageable) {
        List<Lesson> lessons = lessonRepository.findLessonsByCourse(courseId, pageable);
        return new PageImpl<>(lessons, pageable, lessons.size());
    }

    public void addLesson(String courseId, LessonDto lessonDto) {
        Course course = courseRepository.findById(Long.parseLong(courseId)).orElseThrow(NotFoundException::new);
        Lesson lesson = new Lesson();
        lesson.setStartTime(lessonDto.getStartTime());
        lesson.setEndTime(lessonDto.getFinishTime());
        lesson.setDayOfWeek(lessonDto.getDayOfWeek());
        lesson.setTeacher(userRepository.findById(lessonDto.getTeacherId()).orElseThrow(NotFoundException::new));
        lessonRepository.saveAndFlush(lesson);
        course.getLessons().add(lesson);
        courseRepository.saveAndFlush(course);
    }

    public Lesson updateLessonInCourse(String lessonId, LessonDto lessonDto) {
        Lesson lesson = lessonRepository.findById(Long.parseLong(lessonId)).orElseThrow(NotFoundException::new);
        lesson.setStartTime(lessonDto.getStartTime());
        lesson.setEndTime(lessonDto.getFinishTime());
        lesson.setDayOfWeek(lessonDto.getDayOfWeek());
        lesson.setTeacher(userRepository.findById(lessonDto.getTeacherId()).orElseThrow(NotFoundException::new));
        return lessonRepository.saveAndFlush(lesson);
    }

    public void deleteLessonFromCourse(String courseId, String lessonId) {
        Course course = courseRepository.findById(Long.parseLong(courseId)).orElseThrow(NotFoundException::new);
        Lesson lesson = lessonRepository.findById(Long.parseLong(lessonId)).orElseThrow(NotFoundException::new);
        course.getLessons().remove(lesson);
        courseRepository.saveAndFlush(course);
        lessonRepository.deleteById(Long.parseLong(lessonId));
    }

    public Page<User> getStudentsByCourse(String courseId, Pageable pageable) {
        List<User> students = userRepository.findStudentsByCourse(Long.parseLong(courseId), pageable);
        return new PageImpl<>(students, pageable, students.size());
    }

    public void addStudentToCourse(String courseId, String userId, Pageable pageable) {
        Course course = courseRepository.findById(Long.parseLong(courseId)).orElseThrow(NotFoundException::new);
        course.getStudents().add(userRepository.findById(Long.parseLong(userId)).orElseThrow(NotFoundException::new));
        courseRepository.saveAndFlush(course);
        getStudentsByCourse(courseId, pageable);
    }

    public void deleteStudentFromCourse(String courseId, String userId) {
        Course course = courseRepository.findById(Long.parseLong(courseId)).orElseThrow(NotFoundException::new);
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(NotFoundException::new);
        course.getStudents().remove(user);
        courseRepository.saveAndFlush(course);
    }

    public Page<User> getTeachersByCourse(String courseId, Pageable pageable) {
        List<User> teachers = userRepository.findTeachersByCourse(Long.parseLong(courseId), pageable);
        return new PageImpl<>(teachers, pageable, teachers.size());
    }

    public void addTeacherToCourse(String courseId, String userId, Pageable pageable) {
        Course course = courseRepository.findById(Long.parseLong(courseId)).orElseThrow(NotFoundException::new);
        course.getTeachers().add(userRepository.findById(Long.parseLong(userId)).orElseThrow(NotFoundException::new));
        courseRepository.saveAndFlush(course);
        getTeachersByCourse(courseId, pageable);
    }

    public void deleteTeacherFromCourse(String courseId, String userId) {
        Course course = courseRepository.findById(Long.parseLong(courseId)).orElseThrow(NotFoundException::new);
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(NotFoundException::new);
        course.getTeachers().remove(user);
        courseRepository.saveAndFlush(course);
    }

    public PublishedCourse publishCourseById(String courseId) {
        Course course = courseRepository.findById(Long.parseLong(courseId)).orElseThrow(NotFoundException::new);
        course.setState("PUBLISHED");
        courseRepository.saveAndFlush(course);
        return new PublishedCourse(course);
    }
}
