package ru.mrak.LargeDictionary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.mrak.LargeDictionary.service.user.UserService;

@Controller
public class AuthorizedUser {

    private UserService service;

    @Autowired
    public AuthorizedUser(UserService service) {
        this.service = service;
        id = service.getByName("user").getId();
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}