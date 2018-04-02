package ru.mrak.LargeDictionary.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mrak.LargeDictionary.model.user.User;
import ru.mrak.LargeDictionary.service.user.UserService;

@Controller
public class DictionaryController {
    private UserService userService;
    private AuthorizedUser authorizedUser;

    public DictionaryController(UserService userService, AuthorizedUser authorizedUser) {
        this.userService = userService;
        this.authorizedUser = authorizedUser;
    }

    @GetMapping("/dictionary")
    public String dictionary(Model model) {
        User user = userService.get(authorizedUser.getId());
        userService.getLearnSet(user);
        model.addAttribute("user", user);
        return "dictionary";
    }
}
