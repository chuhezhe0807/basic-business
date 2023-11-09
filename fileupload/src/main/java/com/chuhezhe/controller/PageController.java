package com.chuhezhe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName: PageController
 * Package: com.chuhezhe.controller
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/10/19 23:42
 * @Version 1.0
 */
@Controller
@RequestMapping("/page")
public class PageController {
    @GetMapping("/{path}")
    public String toPage(@PathVariable String path) {return path;}
}
