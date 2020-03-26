package com.car.rental.project.web;

import com.car.rental.project.model.*;
import com.car.rental.project.repository.CarRepository;
import com.car.rental.project.service.OfferService;
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
import java.util.*;

@Controller
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;
    private final UserValidator userValidator;
    private final OfferService offerService;
    private final CarRepository carRepository;

    @Autowired
    public UserController(UserService userService, SecurityService securityService, UserValidator userValidator, OfferService offerService, CarRepository carRepository) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.offerService = offerService;
        this.carRepository = carRepository;
    }

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
    public String login(Model model) {
        return "index";
    }

    @GetMapping("/fblogin")
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


    @GetMapping("/flota")
    public String flota(Model model) {
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars",cars);
        return "flota";
    }
    @GetMapping("/kontakt")
    public String kontakt(Model model) {
        return "kontakt";
    }
    @GetMapping("/ofirmie")
    public String ofirmie(Model model) {
        return "ofirmie";
    }
    @GetMapping("/welcome")
    public String welcome(Model model) {
        return "welcome";
    }
    @GetMapping("/adminPanel")
    public String adminPanel(Model model) {
        return "adminPanel";
    }

    @GetMapping("/offer")
    public String offer(Model model) {
        List<OfferWithCar>offers = offerService.findAllOffersWithCars();
        model.addAttribute ("offerList", offers);
        return "offer";
    }

}