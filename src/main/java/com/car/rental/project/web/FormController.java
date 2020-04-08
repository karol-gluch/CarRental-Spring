package com.car.rental.project.web;

import com.car.rental.project.model.*;
import com.car.rental.project.payment.Order;
import com.car.rental.project.payment.Product;
import com.car.rental.project.repository.*;
import com.car.rental.project.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
public class FormController {

    private final OfferService offerService;
    private final CarPhotoRepository carPhotoRepository;
    private final CarRepository carRepository;
    private final LocationRepository locationRepository;
    private final OfferRepository offerRepository;
    private final RentRepository rentRepository;
    private final UserRepository userRepository;


    public FormController(OfferService offerService, CarPhotoRepository carPhotoRepository, CarRepository carRepository, LocationRepository locationRepository, OfferRepository offerRepository, RentRepository rentRepository, UserRepository userRepository) {
        this.offerService = offerService;
        this.carPhotoRepository = carPhotoRepository;
        this.carRepository = carRepository;
        this.locationRepository = locationRepository;
        this.offerRepository = offerRepository;
        this.rentRepository = rentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping({"/carform","/carform/{id}"})
    public String carForm(@PathVariable Optional<Long> id, Model model) {
        if(id.isEmpty()){
            Car newCar = new Car();
            carRepository.save(newCar);
            model.addAttribute("id",newCar.getId());
        }
        return "carform";
    }

    @PostMapping({"/addCar","/addCar/{id}"})
    public String addCar(@PathVariable long id,@ModelAttribute("carForm") Car carForm){
        Car c = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Empty car"));
        c.setMark(carForm.getMark());
        c.setModel(carForm.getModel());
        c.setYearOfProduction(carForm.getYearOfProduction());
        carRepository.save(c);
        return "redirect:/adminPanel";
    }

    @PostMapping({"/addImage","/addImage/{id}"})
    public String addImage(@PathVariable long id , @ModelAttribute("photos") List<MultipartFile> photos, Model model){
        photos.forEach( x -> {
            try {
                byte[] xd = x.getBytes();
                carPhotoRepository.save(new CarPhoto(xd,carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Car is empty!"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        model.addAttribute("id",id);
        return "carform";
    }

    @GetMapping({"/deleteCar","/deleteCar/{id}"})
    public String deleteCar(@PathVariable Optional<Long> id){
        if(id.isPresent()){
            Car c = carRepository.findById(id.get()).orElseThrow();
            List<CarPhoto> photos = carPhotoRepository.findPhotoWithCarId(c);
            photos.forEach(photo -> carPhotoRepository.deleteById(photo.getId()));
            carRepository.deleteById(id.get());
        }
        return "adminPanel";
    }

    @GetMapping("/offerform")
    public String offerForm(Model model){
        List<Car> cars = carRepository.find();
        model.addAttribute("cars",cars);
        return "offerform";
    }

    @PostMapping("/addOffer")
    public String addOffer(@ModelAttribute("offerForm") Offer offerForm){
        offerService.save(offerForm);
        return "redirect:/offer";
    }


    @GetMapping({"/locationform","/locationform/{id}"})
    public String locationForm(@PathVariable Optional<Long> id, Model model) {
        if(id.isEmpty()){
            Location newLocation = new Location();
            locationRepository.save(newLocation);
            model.addAttribute("id",newLocation.getId());
        }
        return "locationform";
    }

    @PostMapping({"/addLocation","/addLocation/{id}"})
    public String addLocation(@PathVariable long id,@ModelAttribute("locationForm") Location locationForm){
        Location l = locationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Empty location"));
        l.setMiasto(locationForm.getMiasto());
        l.setAdres(locationForm.getAdres());
        l.setTelefon(locationForm.getTelefon());
        locationRepository.save(l);
        return "redirect:/adminPanel";
    }

    @PostMapping({"/wypozycz","/wypozycz/{id}"})
    public String wypozycz(@PathVariable long id, Model model) {
        List<Location> locations = locationRepository.findAll();
        model.addAttribute("locations",locations);
        model.addAttribute("ide", id);
        return "wypozycz";
    }

    @PostMapping({"/podsumowanieWypozyczenia", "/podsumowanieWypozyczenia/{id}"})
    public String podsumowanieWypozyczenia(@PathVariable long id, HttpServletRequest request, Model model, @ModelAttribute("rentForm") Rent rentForm){

        String rentDate = request.getParameter("rentDate");
        String returnDate = request.getParameter("returnDate");
        String idRentLocation = request.getParameter("locationsW");
        String idReturnLocation = request.getParameter("locationsZ");

        //calculate number of days
        LocalDate rentDateL = LocalDate.parse(rentDate);
        LocalDate returnDateL = LocalDate.parse(returnDate);
        long numberOfDays = ChronoUnit.DAYS.between(rentDateL, returnDateL);
        if(numberOfDays == 0)
            numberOfDays = 1;

        //calculate price
        Offer o = offerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Empty offer"));
        int price = o.getPrice();
        Long kwota = numberOfDays * price;

        //show location
        Location lRent = locationRepository.findById(Long.valueOf(idRentLocation)).orElseThrow(() -> new IllegalArgumentException("Empty rent location"));
        String rentLocation = lRent.getMiasto() + ", ul. " +lRent.getAdres();
        Location lReturn = locationRepository.findById(Long.valueOf(idReturnLocation)).orElseThrow(() -> new IllegalArgumentException("Empty return location"));
        String returnLocation = lReturn.getMiasto() + ", ul. " +lReturn.getAdres();

        //show car
        Car c = carRepository.findById(o.getCar().getId()).orElseThrow(() -> new IllegalArgumentException("Empty car"));
        String nameCar = c.getMark() + " " +c.getModel();

        //add do database
        Rent r = new Rent();
        r.setKwota(Integer.valueOf(Math.toIntExact(kwota)));
        r.setMiejsceWypozyczenia(rentLocation);
        r.setMiejsceOddania(returnLocation);
        r.setDataWypozyczenia(rentDate);
        r.setDataOddania(returnDate);
        r.setStatus("Rezerwacja");

        //add to rent_offers
        HashSet<Offer> offers = new HashSet<>();
        offers.add(offerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Empty offer")));
        r.setOffers(offers);

        //add to rent_users
        String userName = request.getUserPrincipal().getName();
        HashSet<User> users = new HashSet<>();
        users.add(userRepository.findByUsername(userName));
        r.setUsers(users);
        rentRepository.save(r);

        //add order
        List<Product> list = new ArrayList<>();
        list.add(new Product(nameCar,kwota.toString()));
        Order order = new Order("Wypożyczenie "+nameCar,kwota.toString(),list);

        //show information in podsumowanieWypozyczenia.jsp
        model.addAttribute("order", order);
        model.addAttribute("kwota",kwota);
        model.addAttribute("rentDate", rentDate);
        model.addAttribute("returnDate", returnDate);
        model.addAttribute("rentLocation", rentLocation);
        model.addAttribute("returnLocation", returnLocation);
        model.addAttribute("nameCar", nameCar);

        return "podsumowanieWypozyczenia";
    }

}
