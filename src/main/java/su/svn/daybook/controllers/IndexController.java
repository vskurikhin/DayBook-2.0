package su.svn.daybook.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/home**")
    public String home() {
        return "forward:/";
    }

    @RequestMapping("/appointments**")
    public String appointments() {
        return "forward:/";
    }
}
