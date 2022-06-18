package edu.school21.restful;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import edu.school21.restful.models.User;
import edu.school21.restful.models.roles.ERole;
import edu.school21.restful.repository.UserRepository;
import edu.school21.restful.request.LoginRequest;
import edu.school21.restful.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class RestfulApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void signUpTestFail() throws Exception {
        LoginRequest loginRequest = new LoginRequest("Admin", "blablabla");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/signUp")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequest))).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void signUpTestSuccess() throws Exception {
        LoginRequest loginRequest = new LoginRequest("Admin", "password");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/signUp")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequest))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("Success")).andReturn().getResponse();
    }

    @Test
    public void getUsersTest() throws Exception {
        User user = new User();
        user.setFirstname("Тестировщик");
        user.setLastname("Тестировщиков");
        user.setUsername("Tester");
        user.setPassword("TestPassword");
        Set<ERole> roleSet = new HashSet<>();
        roleSet.add(ERole.ROLE_STUDENT);
        user.setRoles(roleSet);
        LoginRequest loginRequest = new LoginRequest("Admin", "password");
        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response = mockMvc.perform(post("/signUp")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequest))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("Success")).andReturn().getResponse();
        String BEARER_TOKEN = JsonPath.parse(response.getContentAsString()).read("$.token");
        mockMvc.perform(get("/users?page=0&size=10")
                .header("Authorization", "Bearer " + BEARER_TOKEN)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    public void addUserTest() throws Exception {
        //  Авторизация
        LoginRequest loginRequest = new LoginRequest("Admin", "password");
        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response = mockMvc.perform(post("/signUp")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequest))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("Success")).andReturn().getResponse();
        String BEARER_TOKEN = JsonPath.parse(response.getContentAsString()).read("$.token");
        //  Добавление пользователя
        User user = new User();
        user.setFirstname("Тестировщик");
        user.setLastname("Тестировщиков");
        user.setUsername("Tester");
        user.setPassword("TestPassword");
        Set<ERole> roleSet = new HashSet<>();
        roleSet.add(ERole.ROLE_STUDENT);
        user.setRoles(roleSet);
        int currentSize = userRepository.findAll().size();
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user))
                .header("Authorization", "Bearer " + BEARER_TOKEN))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertEquals(currentSize + 1, userRepository.findAll().size());
    }

    @Test
    public void deleteUserTest() throws Exception {
        //  Авторизация
        LoginRequest loginRequest = new LoginRequest("Admin", "password");
        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response = mockMvc.perform(post("/signUp")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequest))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("Success")).andReturn().getResponse();
        String BEARER_TOKEN = JsonPath.parse(response.getContentAsString()).read("$.token");
        //  Добавление пользователя
        int currentSize = userRepository.findAll().size();
        mockMvc.perform(delete("/users/18")
                .header("Authorization", "Bearer " + BEARER_TOKEN))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertEquals(currentSize - 1, userRepository.findAll().size());
    }

}
