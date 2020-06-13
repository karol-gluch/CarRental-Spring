package com.car.rental.project;


import com.car.rental.project.model.Car;
import com.car.rental.project.model.Location;
import com.car.rental.project.model.Offer;
import com.car.rental.project.model.User;
import com.car.rental.project.repository.LocationRepository;
import com.car.rental.project.repository.OfferRepository;
import com.car.rental.project.service.OfferService;
import com.car.rental.project.web.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.assertEquals;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CarRentalSpringApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferService service;

    @MockBean
    private OfferRepository repository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    UserController userController;



    @Test
    public void shouldFindCars() {
        when(repository.findAll()).thenReturn(Stream
                .of(new Offer("Good car", 300), new Offer( "Very good car", 310)).collect(Collectors.toList()));
        assertEquals(2, service.findAll().size());
    }

    @Test
    public void shouldHaveEmptyList() throws Exception {
        mockMvc.perform(get("/cars").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("cars", Matchers.equalTo(new ArrayList<Car>())));
    }

    @Test
    public void shouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void getLocationsTest() {
        when(locationRepository.findAll()).thenReturn(Stream
        .of(new Location( "tumlin", "asd", "123"), new Location("asd","abc","456")).collect(Collectors.toList()));
    assertEquals(2,locationRepository.findAll().size());
    }

    @Test
    void shouldRegisterNewUser() throws Exception{
        User u = new User();
        u.setUsername("user432");
        u.setPassword("user123123");
        u.setPasswordConfirm("user123123");

        this.mockMvc.perform(post("/registration")
                .param("username",u.getUsername())
                .param("password",u.getPassword())
                .param("passwordConfirm",u.getPasswordConfirm()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/index"))
                .andExpect(flash().attribute("register", Matchers.equalTo("true")));
    }

    @Test
    void shouldNotRegisterNewUserBecousePasswordIsBad() throws Exception{
        User u = new User();
        u.setUsername("user432");
        u.setPassword("user1231231");
        u.setPasswordConfirm("user123123");

        this.mockMvc.perform(post("/registration")
                .param("username",u.getUsername())
                .param("password",u.getPassword())
                .param("passwordConfirm",u.getPasswordConfirm()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/index"))
                .andExpect(flash().attribute("error",Matchers.equalTo("password")));
    }



}