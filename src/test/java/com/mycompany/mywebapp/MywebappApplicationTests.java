package com.mycompany.mywebapp;

import com.mycompany.mywebapp.user.User;
import com.mycompany.mywebapp.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class MywebappApplicationTests {

    @Autowired
    private UserRepository repo;
    @Test
    void testAddUser() {
        User user = new User();
        user.setFirstName("Zooja");
        user.setLastName("Puhur");
        user.setEmail("zooja.puhur@voco.ee");
        user.setPassword("password123");

        User savedUser = repo.save(user);
        System.out.println(savedUser);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    void testListAll() {
        Iterable<User> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for (User user: users){
            System.out.println(user);
        }
    }

    @Test
    void testUpdate() {
        Optional<User> optionalUser = repo.findById(5);
        User user = optionalUser.get();
        System.out.println(user);
        user.setLastName("Mänd");
        repo.save(user);

        User updatedUser = repo.findById(5).get();
        System.out.println(updatedUser);

        Assertions.assertThat(updatedUser.getLastName()).isEqualTo("Mänd");
    }

}

