package com.car.rental.project.web;

import com.car.rental.project.model.*;
import com.car.rental.project.repository.CarRepository;
import com.car.rental.project.repository.LocationRepository;
import com.car.rental.project.repository.UserRepository;
import com.car.rental.project.service.OfferService;
import com.car.rental.project.social.FBConnection;
import com.car.rental.project.social.FBGraph;
import com.car.rental.project.service.SecurityService;
import com.car.rental.project.service.UserService;
import com.car.rental.project.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, SecurityService securityService, UserValidator userValidator, OfferService offerService, CarRepository carRepository, LocationRepository locationRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.offerService = offerService;
        this.carRepository = carRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        redirectAttributes.addFlashAttribute("register", "true");
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
        if(!userService.existsByUsername(fbProfileData.get("name")))
        userService.save(new User(fbProfileData.get("name"),fbProfileData.get("id")));

        securityService.autoLogin(fbProfileData.get("name"), fbProfileData.get("id"));

        return "redirect:/index";
    }

    @PostMapping("/changepassword")
    public String changePassword(RedirectAttributes redirectAttributes, HttpServletRequest request) {
        User u = userRepository.findByUsername(request.getParameter("username"));
        String newPass = request.getParameter("newPassword");
        String confPass = request.getParameter("passwordConfirm");
        String oldPass = request.getParameter("oldPassword");

        if(newPass.equals(confPass) && bCryptPasswordEncoder.matches(oldPass,u.getPassword())) {
            u.setPassword(newPass);
            userService.save(u);
            redirectAttributes.addFlashAttribute("changed", "true");
        }
        else {
            redirectAttributes.addFlashAttribute("notchanged", "true");
        }
        return "redirect:/panel/"+u.getUsername();
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
        List<Offer>offers = offerService.findAll();
        model.addAttribute ("offerList", offers);
        return "offer";
    }

    @GetMapping("/locations")
    public String locations(Model model) {
        List<Location> locations = locationRepository.findAll();
        model.addAttribute("locations",locations);
        return "locations";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userRepository.findUsers();
        model.addAttribute("users",users);
        return "users";
    }

    @GetMapping("/cars")
    public String cars(Model model) {
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);
        return "cars";
    }
}