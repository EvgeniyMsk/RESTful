package edu.school21.restful.service;

import edu.school21.restful.models.User;
import edu.school21.restful.models.dto.UserDto;
import edu.school21.restful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserDto userDto) {
        User user = new User(userDto.getUsername(), userDto.getPassword());
        userRepository.saveAndFlush(user);
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public void updateUser(User user) {
        userRepository.saveAndFlush(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteUsersById(id);
    }
}
