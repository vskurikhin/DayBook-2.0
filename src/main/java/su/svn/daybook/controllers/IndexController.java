/*
 * This file was last modified at 2020.11.15 22:00 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * IndexController.java
 * $Id$
 */

package su.svn.daybook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    @Operation(hidden = true)
    public String index() {
        return "index";
    }

    @RequestMapping("/home**")
    @Operation(hidden = true)
    public String home() {
        return "forward:/";
    }

    @RequestMapping("/generated**")
    @Operation(hidden = true)
    public String generated() {
        return "forward:/";
    }

    @RequestMapping("/appointments**")
    @Operation(hidden = true)
    public String appointments() {
        return "forward:/";
    }
}
