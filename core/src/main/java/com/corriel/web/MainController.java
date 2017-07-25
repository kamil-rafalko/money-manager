package com.corriel.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
class MainController {

    @ResponseBody
    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
