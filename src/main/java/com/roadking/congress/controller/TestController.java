package com.roadking.congress.controller;

import com.roadking.congress.domain.Test;
import com.roadking.congress.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/test/db")
    public String createForm(Model model) {
        model.addAttribute("testForm", new TestForm());
        return "test/createTestForm";
    }

    @PostMapping("/test/db")
    public String create(TestForm form) {
        Test test = new Test();
        test.setName(form.getName());
        test.setMessage(form.getMessage());
        testService.insertMessage(test);
        return "redirect:/";
    }
}
