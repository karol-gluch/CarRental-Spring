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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/carform")
    public String carForm(Model model) {
        return "carform";
    }

    @PostMapping("/addCar")
    public String addCar( @ModelAttribute("photos") List<MultipartFile> photos, RedirectAttributes redirectAttributes, HttpServletRequest carForm) {
        Car c = new Car();
        c.setMark(carForm.getParameter("mark"));
        c.setModel(carForm.getParameter("model"));
        c.setYearOfProduction(carForm.getParameter("yearOfProduction"));
        c.setFuelType(carForm.getParameter("fuelType"));
        c.setEngineCapacity(carForm.getParameter("engineCapacity"));
        c.setBodyType(carForm.getParameter("bodyType"));
        c.setNumberOfPlaces(carForm.getParameter("numberOfPlaces"));
        carRepository.save(c);
        photos.forEach(x -> {
            try {
                byte[] xd = x.getBytes();
                carPhotoRepository.save(new CarPhoto(xd, carRepository.findById(c.getId()).orElseThrow(() -> new IllegalArgumentException("Car is empty!"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        redirectAttributes.addFlashAttribute("car", "true");
        return "redirect:/cars";
    }

    @PostMapping({"/addImage", "/addImage/{id}"})
    public String addImage(@PathVariable long id, @ModelAttribute("photos") List<MultipartFile> photos, Model model) {
        photos.forEach(x -> {
            try {
                byte[] xd = x.getBytes();
                carPhotoRepository.save(new CarPhoto(xd, carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Car is empty!"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        model.addAttribute("id", id);
        return "carform";
    }

    @GetMapping({"/deleteCar", "/deleteCar/{id}"})
    public String deleteCar(@PathVariable Optional<Long> id) {
        if (id.isPresent()) {
            Car c = carRepository.findById(id.get()).orElseThrow();
            List<CarPhoto> photos = carPhotoRepository.findPhotoWithCarId(c);
            photos.forEach(photo -> carPhotoRepository.deleteById(photo.getId()));
            carRepository.deleteById(id.get());
        }
        return "redirect:/cars";
    }

    @GetMapping("/offerform")
    public String offerForm(Model model) {
        List<Car> cars = carRepository.find();
        model.addAttribute("cars", cars);
        return "offerform";
    }

    @PostMapping("/addOffer")
    public String addOffer(@ModelAttribute("offerForm") Offer offerForm, RedirectAttributes redirectAttributes) {
        offerService.save(offerForm);
        redirectAttributes.addFlashAttribute("offer", "true");
        return "redirect:/adminPanel";
    }


    @GetMapping({"/locationform", "/locationform/{id}"})
    public String locationForm(@PathVariable Optional<Long> id, Model model) {
        if (id.isEmpty()) {
            Location newLocation = new Location();
            locationRepository.save(newLocation);
            model.addAttribute("id", newLocation.getId());
        }
        return "locationform";
    }

    @PostMapping({"/addLocation", "/addLocation/{id}"})
    public String addLocation(@PathVariable long id, RedirectAttributes redirectAttributes, @ModelAttribute("locationForm") Location locationForm) {
        Location l = locationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Empty location"));
        l.setMiasto(locationForm.getMiasto());
        l.setAdres(locationForm.getAdres());
        l.setTelefon(locationForm.getTelefon());
        locationRepository.save(l);
        redirectAttributes.addFlashAttribute("location", "true");
        return "redirect:/adminPanel";
    }

    @PostMapping({"/wypozycz", "/wypozycz/{id}"})
    public String wypozycz(@PathVariable long id, Model model) {
        List<Location> locations = locationRepository.findAll();

        List<Rent> r = rentRepository.find(id);
        List<String> date = new ArrayList<>();

        r.forEach(e -> {
            String odd = e.getDataOddania();
            String wyp = e.getDataWypozyczenia();
            LocalDate oddD = LocalDate.parse(odd);
            LocalDate wypD = LocalDate.parse(wyp);

            date.add(oddD.toString());
            date.add(wypD.toString());
            while (wypD.isBefore(oddD)) {
                wypD = wypD.plusDays(1);
                if (wypD.isBefore(oddD)) {
                    date.add(wypD.toString());
                }
            }
        });

        String dates = String.join("\",\"", date);
        dates = "[\"" + dates + "\"]";

        model.addAttribute("locations", locations);
        model.addAttribute("ide", id);
        model.addAttribute("dates", dates);
        return "wypozycz";
    }

    @PostMapping({"/podsumowanieWypozyczenia", "/podsumowanieWypozyczenia/{id}"})
    public String podsumowanieWypozyczenia(@PathVariable long id, HttpServletRequest request, Model model, @ModelAttribute("rentForm") Rent rentForm) {

        String rentDate = request.getParameter("rentDate");
        String returnDate = request.getParameter("returnDate");
        String idRentLocation = request.getParameter("locationsW");
        String idReturnLocation = request.getParameter("locationsZ");
        String rentHour = request.getParameter("rentHour");
        String returnHour = request.getParameter("returnHour");

        //calculate number of days
        LocalDate rentDateL = LocalDate.parse(rentDate);
        LocalDate returnDateL = LocalDate.parse(returnDate);
        long numberOfDays = ChronoUnit.DAYS.between(rentDateL, returnDateL);

        //calculate price
        Offer o = offerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Empty offer"));

        Long kwota;
        int price = o.getPrice();
        if (numberOfDays == 0)
        {
            Long liczbaGodzin = Long.parseLong(returnHour) - Long.parseLong(rentHour);

            if(liczbaGodzin == 0)
                liczbaGodzin = Long.valueOf(1);

            kwota = liczbaGodzin * price / 24;
        }
        else {
            if(numberOfDays > 13 && numberOfDays < 30)
                price = price * 9 / 10;

            if(numberOfDays > 29)
                price = price * 8 / 10;

             kwota = numberOfDays * price;

            //System.out.println("cena: " + price + " ile dni: " +numberOfDays);
        }

        //show location
        Location lRent = locationRepository.findById(Long.valueOf(idRentLocation)).orElseThrow(() -> new IllegalArgumentException("Empty rent location"));
        String rentLocation = lRent.getMiasto() + ", ul. " + lRent.getAdres();
        Location lReturn = locationRepository.findById(Long.valueOf(idReturnLocation)).orElseThrow(() -> new IllegalArgumentException("Empty return location"));
        String returnLocation = lReturn.getMiasto() + ", ul. " + lReturn.getAdres();

        //show car
        Car c = carRepository.findById(o.getCar().getId()).orElseThrow(() -> new IllegalArgumentException("Empty car"));
        String nameCar = c.getMark() + " " + c.getModel();

        //add do database
        Rent r = new Rent();
        r.setKwota(Integer.valueOf(Math.toIntExact(kwota)));
        r.setMiejsceWypozyczenia(rentLocation);
        r.setMiejsceOddania(returnLocation);
        r.setDataWypozyczenia(rentDate);
        r.setDataOddania(returnDate);
        r.setGodzinaWypozyczenia(rentHour + ":00");
        r.setGodzinaOddania(returnHour + ":00");
        r.setStatus("Rezerwacja");

        //add to rent_offers
        Offer offer = offerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Empty offer"));
        r.setOffer(offer);

        //add to rent_users
        String userName = request.getUserPrincipal().getName();
        HashSet<User> users = new HashSet<>();
        users.add(userRepository.findByUsername(userName));
        r.setUsers(users);
        rentRepository.save(r);

        //add order
        List<Product> list = new ArrayList<>();
        list.add(new Product(nameCar, kwota.toString()));
        Order order = new Order(r.getId().toString(),"Wypożyczenie " + nameCar, kwota.toString(), list);


        //show information in podsumowanieWypozyczenia.jsp
        model.addAttribute("order", order);
        model.addAttribute("kwota", kwota);
        model.addAttribute("rentDate", rentDate);
        model.addAttribute("returnDate", returnDate);
        model.addAttribute("rentLocation", rentLocation);
        model.addAttribute("returnLocation", returnLocation);
        model.addAttribute("rentHour", rentHour);
        model.addAttribute("returnHour", returnHour);
        model.addAttribute("nameCar", nameCar);

        return "podsumowanieWypozyczenia";
    }

    @PostMapping({"/searchCar"})
    public String searchCar(HttpServletRequest request, Model model) {

        String cenaOd = null;
        String cenaDo = null;
        String rodzajPaliwa = null;
        String typNadwozia = null;
        String rokOd = null;
        String rokDo = null;
        String pojemnoscOd = null;
        String pojemnoscDo = null;
        String liczbaOd = null;
        String liczbaDo = null;

        if(request.getParameter("cenaOd") != null && !request.getParameter("cenaOd").isEmpty()) {
             cenaOd = request.getParameter("cenaOd");
        }
        if(request.getParameter("cenaOd") == null && request.getParameter("cenaOd").isEmpty()) {
            cenaOd.concat("null");
        }

        if(request.getParameter("cenaDo") != null && !request.getParameter("cenaDo").isEmpty()) {
            cenaDo = request.getParameter("cenaDo");
        }
        if(request.getParameter("cenaDo") == null && request.getParameter("cenaDo").isEmpty()) {
            cenaDo.concat("null");
        }

        if(!request.getParameter("rodzajpaliwa").equals("Rodzaj paliwa")) {
            rodzajPaliwa = request.getParameter("rodzajpaliwa");
        }

        if(!request.getParameter("typnadwozia").equals("Typ nadwozia")) {
            typNadwozia = request.getParameter("typnadwozia");
        }

        if(request.getParameter("rokOd") != null && !request.getParameter("rokOd").isEmpty()) {
            rokOd = request.getParameter("rokOd");
        }
        if(request.getParameter("rokOd") == null && request.getParameter("rokOd").isEmpty()) {
            rokOd.concat("null");
        }

        if(request.getParameter("rokDo") != null && !request.getParameter("rokDo").isEmpty()) {
            rokDo = request.getParameter("rokDo");
        }
        if(request.getParameter("rokDo") == null && request.getParameter("rokDo").isEmpty()) {
            rokDo.concat("null");
        }

        if(request.getParameter("pojemnoscOd") != null && !request.getParameter("pojemnoscOd").isEmpty()) {
            pojemnoscOd = request.getParameter("pojemnoscOd");
        }
        if(request.getParameter("pojemnoscOd") == null && request.getParameter("pojemnoscOd").isEmpty()) {
            pojemnoscOd.concat("null");
        }

        if(request.getParameter("pojemnoscDo") != null && !request.getParameter("pojemnoscDo").isEmpty()) {
            pojemnoscDo = request.getParameter("pojemnoscDo");
        }
        if(request.getParameter("pojemnoscDo") == null && request.getParameter("pojemnoscDo").isEmpty()) {
            pojemnoscDo.concat("null");
        }

        if(request.getParameter("liczbaOd") != null && !request.getParameter("liczbaOd").isEmpty()) {
            liczbaOd = request.getParameter("liczbaOd");
        }
        if(request.getParameter("liczbaOd") == null && request.getParameter("liczbaOd").isEmpty()) {
            liczbaOd.concat("null");
        }

        if(request.getParameter("liczbaDo") != null && !request.getParameter("liczbaDo").isEmpty()) {
            liczbaDo = request.getParameter("liczbaDo");
        }
        if(request.getParameter("liczbaDo") == null && request.getParameter("liczbaDo").isEmpty()) {
            liczbaDo.concat("null");
        }

        System.out.println(cenaOd + " " + cenaDo+ " " +rodzajPaliwa+ " " +typNadwozia+ " " +rokOd+ " " +rokDo+ " " +pojemnoscOd+ " " +pojemnoscDo+ " " +liczbaOd+ " " +liczbaDo);

        List<Car> cars = carRepository.findOf(cenaOd, cenaDo, rodzajPaliwa, typNadwozia, rokOd, rokDo, pojemnoscOd, pojemnoscDo, liczbaOd, liczbaDo);

        model.addAttribute("cars", cars);

        return "searchCar";
    }

    @GetMapping({"/deleteUser", "/deleteUser/{id}"})
    public String deleteUser(@PathVariable Optional<Long> id, RedirectAttributes redirectAttributes) {
        if (id.isPresent()) {
            User u = userRepository.findById(id.get()).orElseThrow();
            u.getRent().clear();
            userRepository.deleteById(id.get());
            redirectAttributes.addFlashAttribute("success", "true");
        }
        return "redirect:/users";
    }

    @GetMapping("/deletebyname/{name}")
    public String deleteUserByName(@PathVariable String name, RedirectAttributes redirectAttributes) {
            User u = userRepository.findByUsername(name);
            userRepository.deleteById(u.getId());
            redirectAttributes.addFlashAttribute("success", "true");
        return "redirect:/logout";
    }

    @GetMapping("/panel/{name}")
    public String userPanel(@PathVariable String name, Model model) {
        User u = userRepository.findByUsername(name);
        Long id = u.getId();
        List<Rent> rents = rentRepository.findUserRents(id);
        model.addAttribute("rents", rents);

        return "userPanel";
    }

    @GetMapping({"/deleteCarPanel", "/deleteCarPanel/{id}"})
    public String deleteCar(@PathVariable Optional<Long> id, RedirectAttributes redirectAttributes) {
        if (id.isPresent()) {
            Car c = carRepository.findById(id.get()).orElseThrow();
            List<CarPhoto> photos = carPhotoRepository.findPhotoWithCarId(c);
            photos.forEach(photo -> carPhotoRepository.deleteById(photo.getId()));
            Offer o = c.getOffer();
            if (o != null)
                offerRepository.deleteById(o.getId());

            if (c != null)
                carRepository.delete(c);

            redirectAttributes.addFlashAttribute("deletecar", "true");
        }
        return "redirect:/cars";
    }

    @GetMapping("/callback/{id}")
    public String callback(@PathVariable String id, HttpServletRequest request){
        Rent r = rentRepository.findById(Long.valueOf(id)).orElseThrow();
        r.setStatus("Opłacone");
        rentRepository.save(r);
        String userName = request.getUserPrincipal().getName();
        return "redirect:/panel/"+userName;
    }

    @RequestMapping({"/modifyCar", "/modifyCar/{id}"})
    public ModelAndView modifyCar(@PathVariable Optional<Long> id) {

        ModelAndView mav = new ModelAndView("modifyCar");
        Car car = carRepository.findById(id.get()).orElseThrow();
        mav.addObject("car", car);

        return mav;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveModifyCar(@ModelAttribute("car") Car car, RedirectAttributes redirectAttributes) {
        carRepository.save(car);
        redirectAttributes.addFlashAttribute("modifycar", "true");
        return "redirect:/cars";
    }
}
