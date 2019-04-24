package za.co.goodspeed.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class zaza {

    @GetMapping("/zaza")
    public String zaza(Model model){
        return "zaza";
    }
}
