package com.car.rental.project.web;

import com.car.rental.project.model.User;
import com.car.rental.project.social.FBConnection;
import com.car.rental.project.social.FBGraph;
import com.car.rental.project.service.SecurityService;
import com.car.rental.project.service.UserService;
import com.car.rental.project.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/index";
    }


    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "index";
    }

    @RequestMapping("/fblogin")
    public String service(HttpServletRequest req, HttpServletResponse res) {
        String code = req.getParameter("code");
        if (code.equals("")) {
            throw new RuntimeException(
                    "ERROR: Didn't get code parameter in callback.");
        }
        FBConnection fbConnection = new FBConnection();
        String accessToken = fbConnection.getAccessToken(code);

        FBGraph fbGraph = new FBGraph(accessToken);
        String graph = fbGraph.getFBGraph();
        Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
        RandomValueStringGenerator x = new RandomValueStringGenerator();
        x.setLength(8);
        String random = x.generate();

        userService.save(new User(fbProfileData.get("name"),random));
        securityService.autoLogin(fbProfileData.get("name"), random);

        return "redirect:/index";
    }

    @GetMapping({"/","/index"})
    public String index(Model model) {
        return "index";
    }


    @RequestMapping("/flota")
    public String flota(Model model) {
        return "flota";
    }
    @RequestMapping("/kontakt")
    public String kontakt(Model model) {
        return "kontakt";
    }
    @RequestMapping("/ofirmie")
    public String ofirmie(Model model) {
        return "ofirmie";
    }
    @RequestMapping("/welcome")
    public String welcome(Model model) {
        return "welcome";
    }
    @RequestMapping("/adminPanel")
    public String adminPanel(Model model) { return "adminPanel"; }
}