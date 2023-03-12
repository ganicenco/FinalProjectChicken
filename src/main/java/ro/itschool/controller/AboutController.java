package ro.itschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static ro.itschool.util.Constants.ABOUT_PAGE;

@Controller
public class AboutController {
    @RequestMapping(value = {"/about"})
    public String about() {
        return ABOUT_PAGE;
    }

}
