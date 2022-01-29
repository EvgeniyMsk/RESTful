package edu.school21.restful.controller;

import edu.school21.restful.models.Lesson;
import edu.school21.restful.models.User;
import edu.school21.restful.models.dto.UserDto;
import edu.school21.restful.service.LessonService;
import edu.school21.restful.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;


@RestController
@RequestMapping("/users")
@Tag(name="Название контроллера", description="Описание контролера")
public class GreetingController {
    private final UserService userService;
    private final LessonService lessonService;

    @Autowired
    public GreetingController(UserService userService, LessonService lessonService) {
        this.userService = userService;
        this.lessonService = lessonService;
    }

    @PutMapping
    public HttpStatus registerUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return HttpStatus.OK;
    }

    @PostMapping
    public HttpStatus updateUser(@RequestBody UserDto userDto) {
        if (userService.getUserByUsername(userDto.getUsername()) == null)
            return HttpStatus.NOT_FOUND;
        userService.createUser(userDto);
        return HttpStatus.OK;
    }

    @GetMapping(value = "{id}")
    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя"
    )
    public ResponseEntity<User> greeting(@PathVariable("id") @Min(0) @Parameter(description = "Количество баллов") String id) {
        return new ResponseEntity<>(userService.getUserById(Long.parseLong(id)), HttpStatus.OK);
    }
}
