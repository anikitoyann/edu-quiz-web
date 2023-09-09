package com.example.eduquizweb.controller;
import com.example.eduquizcommon.entity.User;
import com.example.eduquizcommon.entity.UserType;
import com.example.eduquizweb.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MainController {
    @GetMapping("/")
    public String main(){
        return "index";
    }

    @GetMapping("/customLogin")
    public String customLogin() {
        return "customLoginPage";
    }

    @GetMapping("/customSuccessLogin")
    public String customSuccessLogin(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            User user = currentUser.getUser();
            if(user.getUserType() == UserType.TEACHER){
                return "redirect:/";
            }else if(user.getUserType() == UserType.STUDENT){
                return "redirect:/";
            }
        }
        return "redirect:/";
    }
}
