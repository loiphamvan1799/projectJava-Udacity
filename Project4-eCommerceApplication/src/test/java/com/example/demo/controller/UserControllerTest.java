package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController,"userRepository", userRepository);
        TestUtils.injectObjects(userController,"cartRepository", cartRepository);
        TestUtils.injectObjects(userController,"bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    public CreateUserRequest createUser() {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("LoiPV2");
        createUserRequest.setPassword("123456aa");
        createUserRequest.setConfirmPassword("123456aa");
        return createUserRequest;
    }

    @Test
    public void testCreateUser() {
        final ResponseEntity<User> response = userController.createUser(createUser());

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("LoiPV2", user.getUsername());
    }

    @Test
    public void testCreateUserfail() {
        CreateUserRequest reqPasswordNotMatch = createUser();
        reqPasswordNotMatch.setConfirmPassword("1");
        final ResponseEntity<User> resPasswordNotMatch = userController.createUser(reqPasswordNotMatch);
        assertNotNull(resPasswordNotMatch);
        assertEquals(400, resPasswordNotMatch.getStatusCodeValue());

        CreateUserRequest reqPassword = createUser();
        reqPassword.setPassword("less7");
        reqPassword.setConfirmPassword("less7");
        final ResponseEntity<User> resPassword = userController.createUser(reqPassword);
        assertNotNull(resPassword);
        assertEquals(400, resPassword.getStatusCodeValue());
    }

    @Test
    public void testFindUser() {
        User userTest = new User(1L, "LoiPV2", "123456aa");

        // find by id
        when(userRepository.findById(1L)).thenReturn(Optional.of(userTest));
        final ResponseEntity<User> resFindById = userController.findById(userTest.getId());
        User user = resFindById.getBody();
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("LoiPV2", user.getUsername());

        // find by name
        when(userRepository.findByUsername("LoiPV2")).thenReturn(userTest);
        final ResponseEntity<User> resFindByName = userController.findByUserName("LoiPV2");
        user = resFindByName.getBody();
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("LoiPV2", user.getUsername());
    }

    @Test
    public void testFindUserFail() {
        // find by id
        final ResponseEntity<User> responseByID = userController.findById(2L);
        assertNull(responseByID.getBody());

        // find by name
        final ResponseEntity<User> responseByName = userController.findByUserName("notAName");
        assertNull(responseByName.getBody());
    }
}
