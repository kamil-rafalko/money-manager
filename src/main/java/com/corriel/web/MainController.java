package com.corriel.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainController {

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "Money Manager Home Page");
        modelAndView.setViewName("hello");

        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView modelAndView = new ModelAndView();
        if (error != null) {
            modelAndView.addObject("error", "Invalid username and password!");
        }
        if (logout != null) {
            modelAndView.addObject("message", "You've been logged out successfully.");
        }
        modelAndView.setViewName("login");

        return modelAndView;
    }
}
