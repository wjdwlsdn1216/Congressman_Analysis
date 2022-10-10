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
        //결과많이 나온 의원수 top5 조회
        List<Congressman> scons = congressService.findOrderbySimilarView();

        model.addAttribute("cons", cons);
        model.addAttribute("scons", scons);
        model.addAttribute("currentPage","home");
        return "home";
    }









}
