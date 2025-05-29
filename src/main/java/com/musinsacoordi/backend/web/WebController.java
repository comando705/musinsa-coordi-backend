package com.musinsacoordi.backend.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 웹 페이지를 제공하는 컨트롤러
 * 홈 페이지 리다이렉트만 처리합니다.
 * 나머지 페이지는 WebConfig에서 처리합니다.
 */
@Controller
public class WebController {

    /**
     * 홈 페이지 (기본 리다이렉트)
     *
     * @return 브랜드 관리 페이지로 리다이렉트
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/brands";
    }
}
