package com.car.rental.project.web;

import com.car.rental.project.model.Car;
import com.car.rental.project.model.CarPhoto;
import com.car.rental.project.model.Location;
import com.car.rental.project.model.Offer;
import com.car.rental.project.repository.CarPhotoRepository;
import com.car.rental.project.repository.CarRepository;
import com.car.rental.project.repository.LocationRepository;
import com.car.rental.project.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class FormController {

    private final OfferService offerService;
    private final CarPhotoRepository carPhotoRepository;
    private final CarRepository carRepository;
    private final LocationRepository locationRepository;

    public FormController(OfferService offerService, CarPhotoRepository carPhotoRepository, CarRepository carRepository, LocationRepository locationRepository) {
        this.offerService = offerService;
        this.carPhotoRepository = carPhotoRepository;
        this.carRepository = carRepository;
        this.locationRepository = locationRepository;
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
}
