package edu.school21.restful.controller;
import edu.school21.restful.models.User;
import edu.school21.restful.models.dto.UserDto;
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
@RequestMapping("/users")
@Tag(name="Контроллер пользователей", description="Обеспечивает управление пользователями")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(
            summary = "getAllUsers",
            description = "Возвращает всех пользователей [есть пагинация, сортировка по ID]"
    )
    public ResponseEntity<Page<User>> getAllUsers(@PageableDefault(sort = "id", size = 5) Pageable pageable) {
        try {
            Page<User> users = userService.getAllUsers(pageable);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{user-id}")
    @Operation(
            summary = "getUserById",
            description = "Возвращает конкретного пользователя"
    )
    public ResponseEntity<User> getUserById(@PathVariable("user-id") String userId) {
        try {
            User user = userService.getUserById(Long.parseLong(userId));
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @Operation(
            summary = "addNewUser",
            description = "Добавление нового пользователя"
    )
    public ResponseEntity<User> addNewUser(@RequestBody UserDto userDto) {
        try {
            User user = userService.createUser(userDto);
            return ResponseEntity.ok(user);
        } catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{user-id}")
    @Operation(
            summary = "updateUser",
            description = "Изменение пользователя"
    )
    public ResponseEntity<User> updateUser(@PathVariable("user-id") String userId,
                                           @RequestBody UserDto userDto) {
        try {
            User user = userService.updateUser(userDto, userId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{user-id}")
    @Operation(
            summary = "deleteUser",
            description = "Удаление пользователя"
    )
    public ResponseEntity<String> deleteUser (@PathVariable("user-id") String userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok("Successfully deleted!");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
