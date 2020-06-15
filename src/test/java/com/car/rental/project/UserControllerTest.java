package com.car.rental.project;

import com.car.rental.project.model.*;
import com.car.rental.project.repository.*;
import com.car.rental.project.service.UserService;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    OpinionRepository opinionRepository;

    @Autowired
    RentRepository rentRepository;

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    FaultRepository faultRepository;

    User user = new User();
    @BeforeClass
    void setUp() {

        user.setPassword("user123123");
        user.setUsername("user123");
        Mockito.when(userService.findByUsername(user.getUsername()))
                .thenReturn(user);
    }

    @Test
    void souldRegisterNewUser() throws Exception {
        User u = new User();
        u.setUsername("user432");
        u.setPassword("user123123");
        u.setPasswordConfirm("user123123");

        this.mockMvc.perform(post("/registration")
                .param("username", u.getUsername())
                .param("password", u.getPassword())
                .param("passwordConfirm", u.getPasswordConfirm()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/index"))
                .andExpect(flash().attribute("register", Matchers.equalTo("true")));
    }

    @Test
    void souldNotRegisterNewUserBecousePasswordIsBad() throws Exception {
        User u = new User();
        u.setUsername("user432");
        u.setPassword("user1231231");
        u.setPasswordConfirm("user123123");

        this.mockMvc.perform(post("/registration")
                .param("username", u.getUsername())
                .param("password", u.getPassword())
                .param("passwordConfirm", u.getPasswordConfirm()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/index"))
                .andExpect(flash().attribute("error", Matchers.equalTo("password")));
    }

    @Test
    void indexShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    void flotaShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/flota").with(user("user").roles("USER"))).andExpect(status().isOk());
    }

    @Test
    void adminPanelShouldReturnForbidden() throws Exception {
        this.mockMvc.perform(get("/adminPanel").with(user("user").roles("USER"))).andExpect(status().isForbidden());
    }

    @Test
    void flotaShouldRedirect() throws Exception {
        this.mockMvc.perform(get("/flota"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void shouldChangePasswordAndHaveAttributeChangedAndCheckPasswordInDataBase() throws Exception {
        User u = new User();
        u.setUsername("user123");
        u.setPassword("user123123");
        String username = u.getUsername();
        String newPass = "noweHas1o";
        String confPass = "noweHas1o";
        String oldPass = u.getPassword();
        this.mockMvc.perform(post("/changepassword").with(user(u.getUsername()).roles("USER"))
                .param("username", username)
                .param("oldPassword", oldPass)
                .param("newPassword", newPass)
                .param("passwordConfirm", confPass))
                .andExpect(status().is(302))
                .andExpect(flash().attribute("changed",Matchers.equalTo("true")))
                .andExpect(redirectedUrl("/panel/" + u.getUsername()));

        User u1 = userService.findByUsername("user123");
        Boolean test = bCryptPasswordEncoder.matches("noweHas1o",u1.getPassword());
        assertEquals(test,true);
    }

    @Test
    void shouldNotChangePasswordAndHaveAttributeNotChanged() throws Exception {
        User u = new User();
        u.setUsername("user123");
        u.setPassword("user123123");
        String username = u.getUsername();
        String newPass = "noweHas1o";
        String confPass = "noweHas1o1";
        String oldPass = u.getPassword();
        this.mockMvc.perform(post("/changepassword").with(user(u.getUsername()).roles("USER"))
                .param("username", username)
                .param("oldPassword", oldPass)
                .param("newPassword", newPass)
                .param("passwordConfirm", confPass))
                .andExpect(status().is(302))
                .andExpect(flash().attribute("notchanged", Matchers.equalTo("true")))
                .andExpect(redirectedUrl("/panel/" + u.getUsername()));
    }

    @Test
    void shouldHaveAttributeWithOpinions() throws Exception {
        List<Opinion> list = opinionRepository.findAll();
        this.mockMvc.perform(get("/ofirmie")
                .with(user("user").roles("USER")))
                .andExpect(model().attribute("opinion",Matchers.hasSize(list.size())))
                .andExpect(view().name("ofirmie"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHaveAttributeWithRentsAndMoney() throws Exception {
        List<Rent> list = rentRepository.findAll();
        int sum = rentRepository.sumMoney();
        this.mockMvc.perform(get("/adminPanel")
                .with(user("admin").roles("ADMIN")))
                .andExpect(model().attribute("rents",Matchers.hasSize(list.size())))
                .andExpect(model().attribute("money",Matchers.equalTo(sum)))
                .andExpect(view().name("adminPanel"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHaveAttributeOffers() throws Exception {
        List<Offer> offers = offerRepository.findAll();
       // offers.stream().map(e -> e.getCar().getCarPhoto().stream().sorted().collect(Collectors.toList()));
        this.mockMvc.perform(get("/offer")
        .with(user("user").roles("USER")))
                .andExpect(model().attribute("offerList",Matchers.hasSize(offers.size())))
                .andExpect(view().name("offer"))
                .andExpect(status().isOk());

    }

    @Test
    void shouldHaveAttributeLocations() throws Exception {
        List<Location> locations = locationRepository.findAll();
        this.mockMvc.perform(get("/locations")
                .with(user("user").roles("USER")))
                .andExpect(model().attribute("locations",Matchers.hasSize(locations.size())))
                .andExpect(view().name("locations"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHaveAttributeUsers() throws Exception {
        List<User> users = userRepository.findUsers();
        this.mockMvc.perform(get("/users")
                .with(user("admin").roles("ADMIN")))
                .andExpect(model().attribute("users",Matchers.hasSize(users.size())))
                .andExpect(view().name("users"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHaveAttributeCars() throws Exception {
        List<Car> cars = carRepository.findAll();
        this.mockMvc.perform(get("/cars")
                .with(user("user").roles("USER")))
                .andExpect(model().attribute("cars",Matchers.hasSize(cars.size())))
                .andExpect(view().name("cars"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHaveAttributeFaults() throws Exception {
        List<Fault> faults = faultRepository.findAll();
        this.mockMvc.perform(get("/faults")
                .with(user("admin").roles("ADMIN")))
                .andExpect(model().attribute("faults",Matchers.hasSize(faults.size())))
                .andExpect(view().name("faults"))
                .andExpect(status().isOk());
    }

    @AfterClass
    void restoreData(){
        userService.save(user);
        Boolean test = bCryptPasswordEncoder.matches("user123123",user.getPassword());
        assertEquals(test,true);
    }
}
