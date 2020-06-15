package com.car.rental.project.mail;


import com.car.rental.project.contact.Mail;
import com.car.rental.project.contact.RegistrationController;
import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.ServerSetup;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MailTest {

    @Autowired
    private MockMvc mockMvc;

    @Rule
    public GreenMailRule server = new GreenMailRule(new ServerSetup(25, "localhost", "smtp"));

    @Autowired
    RegistrationController registrationController;

    @Test
    public void shouldSendMail() throws Exception {
        Mail m = new Mail();
        m.setEmail("test@gmail.com");
        m.setTemat("testtemat");
        m.setTresc("testtresc");

        this.mockMvc.perform(post("/sendMail")
                .with(user("user").roles("ADMIN"))
                .param("email", m.getEmail())
                .param("temat", m.getTemat())
                .param("tresc", m.getTresc()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("kontakt"))
                .andExpect(flash().attribute("mail", Matchers.equalTo("true")));
    }


    @Test
    public void shouldReceivedMessage() throws Exception {
        registrationController.sendMail("testmail@o2.pl", "TematTest", "TrescTest");
        MimeMessage[] recMessage = server.getReceivedMessages();
        assertThat(recMessage.length).isEqualTo(1);
    }

    @Test
    public void shouldReceivedEmail() throws Exception {
        registrationController.sendMail("testmail@o2.pl", "TematTest", "TrescTest");
        MimeMessage[] recMessage = server.getReceivedMessages();
        MimeMessage recMessageMail = recMessage[0];
        assertThat(recMessageMail.getFrom()[0].toString()).isEqualTo("testmail@o2.pl");
    }

    @Test
    public void shouldReceivedSubject() throws Exception {
        registrationController.sendMail("testmail@o2.pl", "TematTest", "TrescTest");
        MimeMessage[] recMessage = server.getReceivedMessages();
        MimeMessage recMessageMail = recMessage[0];
        assertThat(recMessageMail.getSubject().toString()).isEqualTo("TematTest");
    }

    @Test
    public void shouldReceivedContent() throws Exception {
        registrationController.sendMail("testmail@o2.pl", "TematTest", "TrescTest");
        MimeMessage[] recMessage = server.getReceivedMessages();
        MimeMessage recMessageMail = recMessage[0];
        assertThat(recMessageMail.getContent().toString()).contains("TrescTest");
    }
}
