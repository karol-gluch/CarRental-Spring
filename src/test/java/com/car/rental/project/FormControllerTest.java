package com.car.rental.project;

import com.car.rental.project.model.*;
import com.car.rental.project.repository.CarRepository;
import com.car.rental.project.repository.LocationRepository;
import com.car.rental.project.repository.OfferRepository;
import com.car.rental.project.web.FormController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FormControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    FormController formController;

    @Test
    void shouldAddNewLocation() throws Exception {
        Location l = new Location();
        l.setMiasto("tumlin");
        l.setAdres("abc");
        l.setTelefon("123456789");

        this.mockMvc.perform(post("/addLocation/{id}", 1L).with(user("user").roles("ADMIN"))
                .param("miasto", l.getMiasto())
                .param("adres", l.getAdres())
                .param("telefon", l.getTelefon()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/locationform"))
                .andExpect(flash().attribute("location", Matchers.equalTo("true")));
    }

    @Test
    void shouldDeleteNewUser() throws Exception {
        this.mockMvc.perform(get("/deleteUser/{id}", 1L)
                .with(user("user").roles("ADMIN")))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/users"))
                .andExpect(flash().attribute("success", Matchers.equalTo("true")));
    }

    @Test
    void shouldAddNewCar() throws Exception {
        Car c = new Car();
        c.setBodyType("Hatchback");
        c.setEngineCapacity("2.0");
        c.setFuelType("Benzyna");
        c.setMark("Audi");
        c.setModel("A3");
        c.setNumberOfPlaces("5");
        c.setYearOfProduction("2010");

        this.mockMvc.perform(post("/addCar")
                .with(user("user").roles("ADMIN"))
                .param("bodyType", c.getBodyType())
                .param("engineCapacity", c.getEngineCapacity())
                .param("fuelType", c.getFuelType())
                .param("mark", c.getMark())
                .param("model", c.getModel())
                .param("numberOfPlaces", c.getNumberOfPlaces())
                .param("yearOfProduction", c.getYearOfProduction()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/cars"))
                .andExpect(flash().attribute("car", Matchers.equalTo("true")));
    }

    @Test
    void shouldDeleteNewCar() throws Exception {
        this.mockMvc.perform(get("/deleteCar/{id}", 1L)
                .with(user("user").roles("ADMIN")))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/cars"));
    }

    @Test
    void shouldDeleteNewCarFromPanel() throws Exception {
        this.mockMvc.perform(get("/deleteCarPanel/{id}", 1L)
                .with(user("user").roles("ADMIN")))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/cars"))
                .andExpect(flash().attribute("deletecar", Matchers.equalTo("true")));

    }

    @Test
    void offerFormShouldRedirect() throws Exception {
        this.mockMvc.perform(get("/offerform"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void locationFormShouldRedirect() throws Exception {
        this.mockMvc.perform(get("/locationform"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void wypozyczShouldReturnOk() throws Exception {
        this.mockMvc.perform(post("/wypozycz/{id}", 1L).with(user("user").roles("USER"))).andExpect(status().isOk());
    }

    @Test
    void shouldDeleteByName() throws Exception {
        this.mockMvc.perform(get("/deletebyname/{name}", "user123")
                .with(user("user").roles("ADMIN")))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/logout"))
                .andExpect(flash().attribute("success", Matchers.equalTo("true")));

    }

    @Test
    void shouldCallback() throws Exception {
        Rent r = new Rent();
        r.setStatus("Opłacone");
        this.mockMvc.perform(get("/callback/{id}", 1L)
                .with(user("user").roles("ADMIN"))
                .param("status", r.getStatus()))
                .andExpect(status().isFound());

        assertEquals("Opłacone",r.getStatus());
    }

    @Test
    void saveCarShouldReturnOk() throws Exception {

        Car c = new Car();
        c.setBodyType("Hatchback");
        c.setEngineCapacity("2.0");
        c.setFuelType("Benzyna");
        c.setMark("Audi");
        c.setModel("A3");
        c.setNumberOfPlaces("5");
        c.setYearOfProduction("2010");

        this.mockMvc.perform(post("/save")
                .with(user("user").roles("ADMIN"))
                .param("bodyType", c.getBodyType())
                .param("engineCapacity", c.getEngineCapacity())
                .param("fuelType", c.getFuelType())
                .param("mark", c.getMark())
                .param("model", c.getModel())
                .param("numberOfPlaces", c.getNumberOfPlaces())
                .param("yearOfProduction", c.getYearOfProduction()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/cars"))
                .andExpect(flash().attribute("modifycar", Matchers.equalTo("true")));
    }

    @Test
    void modifyCarShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/modifyCar/{id}", 1L).with(user("user").roles("USER"))).andExpect(status().isOk());
    }

    @Test
    void faultFormrShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/faultForm/{id}", 1L).with(user("user").roles("USER"))).andExpect(status().isOk());
    }

    @Test
    void shouldCalculateNumberOfDays() throws Exception {
        Rent r = new Rent();
        r.setDataOddania("2020-06-15");
        r.setDataWypozyczenia("2020-06-10");

        this.mockMvc.perform(post("/payRes/{id}", 1L).with(user("user").roles("ADMIN"))
                .param("dataW", r.getDataOddania())
                .param("dataO", r.getDataWypozyczenia()))
                .andExpect(status().isOk());

        LocalDate rentDateL = LocalDate.parse(r.getDataWypozyczenia());
        LocalDate returnDateL = LocalDate.parse(r.getDataOddania());
        long numberOfDays = ChronoUnit.DAYS.between(rentDateL, returnDateL);

        assertEquals(5, numberOfDays);
    }

    @Test
    void shouldEndReservation() throws Exception {
        Rent r = new Rent();
        r.setStatus("Zakończone");
        this.mockMvc.perform(get("/endRent/{id}", 1L)
                .with(user("user").roles("ADMIN"))
                .param("status", r.getStatus()))
                .andExpect(status().isFound());

        assertEquals("Zakończone",r.getStatus());
    }

    @Test
    void shouldAddNewOpinion() throws Exception {
        Opinion o = new Opinion();
        o.setStars("5");
        o.setComment("Polecam! TEST");

        this.mockMvc.perform(post("/writeOpinion/{id}", 1L).with(user("user").roles("ADMIN"))
                .param("stars", o.getStars())
                .param("comment", o.getComment()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/panel/user"))
                .andExpect(flash().attribute("opinion", Matchers.equalTo("true")));
    }

    @Test
    void shouldSearchCar() throws Exception {
        Car car = new Car("Audi", "A3", "2015", "Benzyna", "2.0", "Sedan", "4");
        List <Car> cars = carRepository.findOf("0", "1000", "Benzyna", "Sedan", "2000","2020", "1.5", "3.2", "2", "5");
        assertFalse(cars.isEmpty());
    }

    @Test
    void shouldntSearchCarWithBadData() throws Exception {
        Car car = new Car("Audi", "A3", "2015", "Benzyna", "2.0", "Sedan", "4");
        List <Car> cars = carRepository.findOf("0", "1000", "Benzyna", "Sedan", "2021","2022", "1.5", "3.2", "2", "5");
        assertTrue(cars.isEmpty());
    }

    @Test
    void shouldntSearchCarWithTooLowPrice() throws Exception {
        Car car = new Car("Audi", "A3", "2015", "Benzyna", "2.0", "Sedan", "4");
        List <Car> cars = carRepository.findOf("0", "5", "Benzyna", "Sedan", "2000","2020", "1.5", "3.2", "2", "5");
        assertTrue(cars.isEmpty());
    }

    @Test
    void shouldReturnReturnPriceFromDatabase() throws Exception {
        Offer o = offerRepository.findById(1L).orElseThrow();
        int cena = o.getPrice();
        assertEquals(500, cena);
    }

    @Test
    void shouldCalculatePriceForRent() throws Exception {
        assertEquals(2500, (long)(formController.calculatePriceForRent(500, 5, "8", "12"))); //rent for 5 days
        assertEquals(500, (long)(formController.calculatePriceForRent(1000, 0, "8", "20"))); //rent for hours
        assertEquals(13500, (long)(formController.calculatePriceForRent(1000, 15, "8", "20"))); //rent with discount 10%
        assertEquals(24000, (long)(formController.calculatePriceForRent(1000, 30, "8", "20"))); //rent with discount 20%
    }


}
