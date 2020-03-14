package com.kaituo.comparison.back.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: yedong
 * @Date: 2020/2/13 17:14
 * @Modified by:
 */
@RestController
@RequestMapping(value = {"/common"})
public class CommonControl {
    @GetMapping(value = "/cors")
    public String cors() {
        return "cors success";
    }
}
