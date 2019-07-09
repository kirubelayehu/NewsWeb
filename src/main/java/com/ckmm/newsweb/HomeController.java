package com.ckmm.newsweb;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

  @GetMapping("/")     // <2>
  public String home() {
    return "home";     // <3>
  }

}
