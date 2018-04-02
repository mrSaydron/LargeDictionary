package ru.mrak.LargeDictionary.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String root() {
        return "/main";
    }

    @GetMapping("/test")
    public String test() {
        return "index";
    }
}
