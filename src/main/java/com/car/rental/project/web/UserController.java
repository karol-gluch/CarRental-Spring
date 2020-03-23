package com.car.rental.project.web;

import com.car.rental.project.model.Car;
import com.car.rental.project.model.CarPhoto;
import com.car.rental.project.model.Offer;
import com.car.rental.project.model.User;
import com.car.rental.project.repository.CarPhotoRepository;
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
import java.io.*;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;
    private final UserValidator userValidator;
    private final OfferService offerService;
    private final CarPhotoRepository carPhotoRepository;


    @Autowired
    public UserController(UserService userService, SecurityService securityService, UserValidator userValidator, OfferService offerService, CarPhotoRepository carPhotoRepository) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.offerService = offerService;
        this.carPhotoRepository = carPhotoRepository;

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
    public String index(Model model) throws IOException {

        File file = new File("C:\\Users\\kikos\\OneDrive\\Pulpit\\Java\\car-rental\\src\\main\\webapp\\resources\\images\\offer\\Audi-A6.png");
        byte[] picInBytes = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(picInBytes);
        fileInputStream.close();

        Car c1 = new Car("Skoda","Octavia","2018");
        CarPhoto p1 = new CarPhoto(picInBytes,c1);
        carPhotoRepository.save(p1);
        Offer o1 = new Offer("Taka o",200,c1);
        offerService.save(o1);

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

    @RequestMapping("/offer")
    public String offer(Model model) {
        List<Offer> offerList = offerService.findAll();
        model.addAttribute ("offerList", offerList);
        return "offer";
    }

}