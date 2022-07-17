package com.example.Account_Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.context.properties.ConfigurationPropertiesReportEndpoint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.validation.ConstraintViolationException;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {
//        ConfigurationPropertiesReportEndpoint.ApplicationConfigurationProperties.class
//})
//@WebAppConfiguration(value = "")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountServiceApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    AuthController authController = new AuthController();

    @Autowired
    UserController userController = new UserController();

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    ArrayList<String> arrayList = new ArrayList<>();


    User user = new User("Tatyana", "Stepyko",
            "johndoe@acme.com", "sdfsdfsdfsdf", arrayList);
    Payment payment = new Payment();

    List<Payment> list = new ArrayList<>();


//    @Test
//    public void givenMac_whenServletContext(){
//        ServletContext servletContext = webApplicationContext.getServletContext();
//
//        Assert.notNull(servletContext, "not null");
//        Assert.isTrue(servletContext instanceof MockServletContext, "is true");
//        //Assert.notNull(webApplicationContext.getBean(""));
//    }


    @Test
    public void registration() throws Exception {

//        this.mockMvc.perform(post("/api/auth/signup").content(String.valueOf(user))).andDo(print())
//                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(jsonPath("$user", is(user)));

        authController.PostApiSignup(user);
    }

    @Test
    public void postPayment() {
        user.setName("Tatyana");
        user.setLastname("Stepyko");
        user.setEmail("johndoe@acme.com");
        user.setPassword("sdfsdfsdfsdf");
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        payment.setEmployee(user.getEmail().toLowerCase());
        payment.setPeriod("31-2021");
        payment.setSalary((long) -5);
        list.add(payment);
        try {
            ResponseEntity<?> responseEntity = userController.postPayment(list);

            System.out.println(responseEntity.getBody());
            System.out.println(responseEntity.getHeaders());
            System.out.println(responseEntity.getStatusCode());
        } catch (ConstraintViolationException e) {
            System.out.println("!!!!!!!!!!!!!!!!!!");
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }


    }

    @Test
    public void getPayments() throws Exception {
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        MultiValueMap<String, String> mapParams = new LinkedMultiValueMap<>();
        mapParams.add("grant_type", "password");
        mapParams.add("username", userDetails.getUsername());
        mapParams.add("password", userDetails.getPassword());
        this.mockMvc.perform(get("/api/empl/payment").params(mapParams)
                        .queryParam("period", "01-2021"))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));


    }

}
