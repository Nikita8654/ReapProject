package com.ttn.reapProject.controller;

import com.ttn.reapProject.component.LoggedInUser;
import com.ttn.reapProject.entity.User;
import com.ttn.reapProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    UserService userService;


    @GetMapping("/")
    public ModelAndView index(RedirectAttributes redirectAttributes, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> activeUser = Optional.ofNullable((User) httpSession.getAttribute("activeUser"));
        if(activeUser.isPresent()){
            modelAndView.setViewName("redirect:/users/"+activeUser.get().getId());
        }
        else {
            modelAndView.setViewName("index");
            modelAndView.addObject("newUser", new User());
            modelAndView.addObject("loggedInUser", new LoggedInUser());
            redirectAttributes.addAttribute("error");
            redirectAttributes.addAttribute("success");
        }
        return modelAndView;
    }

    // Log user in
    @PostMapping("/login")
    public ModelAndView logUserIn(@ModelAttribute("loggedInUser") LoggedInUser loggedInUser,
                                  HttpServletRequest httpServletRequest,
                                  RedirectAttributes redirectAttributes) {
        //System.out.println("Updated password of the user:"+loggedInUser.getPassword());
        Optional<User> optionalUser = userService.findUserByEmailAndPassword(loggedInUser.getEmail(), loggedInUser.getPassword());
        System.out.println(loggedInUser);
        System.out.println(optionalUser);
        if (!optionalUser.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("error", "Invalid credentials!Try Again");
            return modelAndView;
        } else {
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute("activeUser", optionalUser.get());
            return new ModelAndView("redirect:/users/" + optionalUser.get().getId());
        }
    }

    // Log user out
    @PostMapping("/logout")
    public ModelAndView logUserOut(HttpServletRequest httpServletRequest,
                                   RedirectAttributes redirectAttributes) {
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.invalidate();
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        redirectAttributes.addFlashAttribute("success", "Logged out");
        return modelAndView;
    }
}
