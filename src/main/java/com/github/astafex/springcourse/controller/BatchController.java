package com.github.astafex.springcourse.controller;

import com.github.astafex.springcourse.dao.PersonDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-batch-update")
@RequiredArgsConstructor
public class BatchController {
    private final PersonDAO personDAO;

    @GetMapping
    public String index() {
        return "/batch/index";
    }

    @GetMapping("/without")
    public String withoutBatch() {
        personDAO.testMultipleUpdate();
        return "redirect:/people";
    }

    @GetMapping("/with")
    public String withBatch() {
        personDAO.testBatchUpdate();
        return "redirect:/people";
    }
}
