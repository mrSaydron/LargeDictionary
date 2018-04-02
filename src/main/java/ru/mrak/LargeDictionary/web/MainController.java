package ru.mrak.LargeDictionary.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mrak.LargeDictionary.model.user.LearnBook;
import ru.mrak.LargeDictionary.model.user.User;
import ru.mrak.LargeDictionary.service.user.UserService;

import java.util.LinkedList;
import java.util.List;

@Controller
public class MainController {

    private UserService userService;
    private AuthorizedUser authorizedUser;

    public MainController(UserService userService, AuthorizedUser authorizedUser) {
        this.userService = userService;
        this.authorizedUser = authorizedUser;
    }

    @GetMapping("/main")
    public String main(Model model) {
        User user = userService.get(authorizedUser.getId());
        userService.getLearnSet(user);
        userService.getLearnBook(user);
        List<LearnBook> readBooks = new LinkedList<>();
        List<LearnBook> notReadBooks = new LinkedList<>();
        for (LearnBook book : user.getLearnBooks()) {
            if(book.getRead()) readBooks.add(book);
            else notReadBooks.add(book);
        }
        model.addAttribute("user", user);
        model.addAttribute("readBooks", readBooks);
        model.addAttribute("notReadBooks", notReadBooks);
        return "main";
    }
}