package com.example.demo.DAO;

import com.example.demo.models.User;
import lombok.Getter;

import java.util.List;

public interface UserDAO extends CommonDAO<User, Long> {
    User findByLogin(String login);
    boolean existsByLogin(String login);
    List<User> findByLastName(String lastName);
    List<User> findByFirstName(String firstName);
    List<User> findBySurname(String surname);
    List<User> findByPhone(String phone);
    List<User> findByEmail(String email);
    List<User> findByAddress(String address);
    List<User> findAllAdmins();
}
