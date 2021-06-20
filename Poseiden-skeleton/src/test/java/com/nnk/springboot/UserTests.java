package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.utils.RoleEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class UserTests {

    @Autowired
    private UserRepository userRepository;


    private String username = "loa3m";
    private String fullname = "lola2xas";
    private String password = "Abc1234!5";
    private String role = RoleEnum.ADMIN.name();

    @Test
    public void addUserTest() {
        User user = new User(username, password, fullname, role);

        User inDb = userRepository.save(user);
        assertNotNull(inDb.getId());
        assertEquals(fullname, inDb.getFullname());
        assertEquals(username, inDb.getUsername());
        assertNotEquals("drAjkl4!", inDb.getPassword());
    }

    @Test
    public void findUserByIdTest() {
        User user = new User(username, password, fullname, role);
        user = userRepository.save(user);
        assertTrue(userRepository.findById(user.getId()).isPresent());
    }

    @Test
    public void findUserByUsernameTest() {
        User user = new User(username, password, fullname, role);
        user = userRepository.save(user);
        assertNotNull(userRepository.findByUsername(username));
    }

    @Test
    public void updateUserTest() {
        User user = new User(username, password, fullname, role);

        user = userRepository.save(user);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(user.getId());
        userRepository.save(user);
        User inDb = userRepository.findById(user.getId()).get();
        assertEquals(fullname, inDb.getFullname());
        assertEquals(username, inDb.getUsername());
        assertNotEquals(password, inDb.getPassword());
    }

    @Test
    public void deleteUserTest() {
        int count = userRepository.findAll().size();
        User user = new User(username, password, fullname, role);

        user = userRepository.save(user);

        assertTrue(userRepository.findAll().size() > count);

        userRepository.delete(user);

        assertEquals(count, userRepository.findAll().size());
    }
}
