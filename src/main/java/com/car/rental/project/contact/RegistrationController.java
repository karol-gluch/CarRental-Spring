package com.car.rental.project.contact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;


@RestController
public class RegistrationController {

    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/sendMail")
    public RedirectView sendMailSuccess(HttpServletRequest request){

        String email = request.getParameter("email");
        String temat = request.getParameter("temat");
        String tresc = request.getParameter("tresc");

        // create user
        Mail mail = new Mail();
        mail.setEmail(email);
        mail.setTemat(temat);
        mail.setTresc(tresc);

        // send a notification
        try {
            notificationService.sendNotificaitoin(mail);
        }catch( Exception e ){
            // catch error
            logger.info("Error Sending Email: " + e.getMessage());
        }
        return new RedirectView("index");
    }

}