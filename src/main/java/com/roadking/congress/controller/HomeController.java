package com.roadking.congress.controller;

import com.roadking.congress.domain.Congressman;
import com.roadking.congress.service.CongressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CongressService congressService;

    @RequestMapping("/")
    public String home(Model model) {
        //top5 조회수 의원 조회
        List<Congressman> cons = congressService.findOrderbyView();

        model.addAttribute("cons", cons);
        model.addAttribute("currentPage","home");
        return "home";
    }









}
