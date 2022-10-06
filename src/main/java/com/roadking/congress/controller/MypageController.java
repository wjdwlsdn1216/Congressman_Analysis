package com.roadking.congress.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MypageController {

    @GetMapping("/mypage")
    public String mypage(Model model) {
        model.addAttribute("currentPage", "mypage");
        return "mypage";
    }
}
