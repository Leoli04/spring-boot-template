package com.leoli04.springboottemplate.controller;

import com.leoli04.springboottemplate.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: demo
 * @Author: LeoLi04
 * @CreateDate: 2024/7/21 21:31
 * @Version: 1.0
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;
}
