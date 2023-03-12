package ro.itschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static ro.itschool.util.Constants.LOGIN;

@Controller
public class LogoutController {

    @RequestMapping(value = {"/logout"})
    public String logout(){
        return LOGIN;
    }
}
