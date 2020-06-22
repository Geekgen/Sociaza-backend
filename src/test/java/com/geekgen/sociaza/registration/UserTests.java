package com.geekgen.sociaza.registration;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
@SpringBootTest
public class UserTests {

    @MockBean
    UserService userService;

    static User userMock = new User("test@test.com","jabuticaba","Ada Lovelace");

    @Test
    void creationAndSetup(){
        assertEquals(userMock.getEmail() ,"test@test.com");
        assertEquals(userMock.getPassword() ,"jabuticaba");
        assertEquals(userMock.getName() ,"Ada Lovelace");
    }

    @Test
    void findUserByEmailTest(){
        // Arrange
        Mockito.when(userService.findUserByEmail("test@test.com"))
                .thenReturn(userMock);
        // Act
        User user = userService.findUserByEmail("test@test.com");
        // Assert
        Assertions.assertEquals(userMock, user);
    }
}


