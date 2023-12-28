package controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.accenture.pip.customermanagement.CustomerManagementApplication;
import com.accenture.pip.customermanagement.entity.Address;
import com.accenture.pip.customermanagement.entity.Customer;
import com.accenture.pip.customermanagement.mapper.CustomerMapper;
import com.accenture.pip.customermanagement.repository.AddressRepository;
import com.accenture.pip.customermanagement.repository.CustomerRepository;
import com.accenture.pip.customermanagement.service.CustomerManagementService;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import util.CustomerUtil;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = {CustomerManagementApplication.class})
@TestPropertySource("classpath:application-test.properties")
public class CustomerManagementControllerIT {


    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerManagementService customerManagementService;


    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CustomerMapper mapper;


    @Autowired
    MockMvc mockMvc;


    private static final String GOOD_JWT = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJUMXBoanNldTlWNjVscnFHZWl";

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";


    @Test
    public void testAddCustomerSuccess() throws Exception {
        String body = Resources.toString(Resources.getResource("payloads/addCustomer.json"), StandardCharsets.UTF_8);
        MockHttpServletRequestBuilder request = post("/customer/create")
                .content(body)
                .header(TOKEN_HEADER,(TOKEN_PREFIX+" "+GOOD_JWT))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testGetAllCustomers() throws Exception {

        Customer existingCustomer = CustomerUtil.createCustomer();

        MockHttpServletRequestBuilder request = get("/customer/getAll")
                .header(TOKEN_HEADER,(TOKEN_PREFIX+" "+GOOD_JWT))
                .contentType(MediaType.APPLICATION_JSON);

        Customer savedCustomer = customerRepository.saveAndFlush(existingCustomer);
        assertTrue(savedCustomer.getId()!=0);


        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testGetCustomerSuccess() throws Exception {

        Customer existingCustomer = CustomerUtil.createCustomer();
        Customer savedCustomer= customerRepository.saveAndFlush(existingCustomer);
        assertTrue(savedCustomer.getId()!=0);

        MockHttpServletRequestBuilder request = get("/customer/get/"+savedCustomer.getCustomerId())
                .header(TOKEN_HEADER,(TOKEN_PREFIX+" "+GOOD_JWT))
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testGetCustomerFailure() throws Exception {

        Customer existingCustomer = CustomerUtil.createCustomer();
        Customer savedCustomer= customerRepository.saveAndFlush(existingCustomer);
        assertTrue(savedCustomer.getId()!=0);

        MockHttpServletRequestBuilder request = get("/customer/get/"+UUID.randomUUID().toString())
                .header(TOKEN_HEADER,(TOKEN_PREFIX+" "+GOOD_JWT))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

 /*   @Test
    @Transactional
    public void testUpdateCustomerSuccess() throws Exception {

        String body = Resources.toString(Resources.getResource("payloads/updateCustomer.json"), StandardCharsets.UTF_8);
        Customer existingCustomer = CustomerUtil.createCustomer();
        Customer savedCustomer= customerRepository.saveAndFlush(existingCustomer);
        assertTrue(savedCustomer.getId()!=0);

        MockHttpServletRequestBuilder request = patch("/customer/update/"+savedCustomer.getCustomerId())
                .header(TOKEN_HEADER,(TOKEN_PREFIX+" "+GOOD_JWT))
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }*/

    @Test
    @Transactional
    public void testUpdateCustomerFailure() throws Exception {

        String body = Resources.toString(Resources.getResource("payloads/updateCustomer.json"), StandardCharsets.UTF_8);
        Customer existingCustomer = CustomerUtil.createCustomer();
        Customer savedCustomer= customerRepository.saveAndFlush(existingCustomer);
        assertTrue(savedCustomer.getId()!=0);

        MockHttpServletRequestBuilder request = patch("/customer/update/"+UUID.randomUUID().toString())
                .header(TOKEN_HEADER,(TOKEN_PREFIX+" "+GOOD_JWT))
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testDeleteCustomerSuccess() throws Exception {

        Customer existingCustomer = CustomerUtil.createCustomer();
        Customer savedCustomer= customerRepository.saveAndFlush(existingCustomer);
        assertTrue(savedCustomer.getId()!=0);

        MockHttpServletRequestBuilder request = delete("/customer/delete/customerId/"+savedCustomer.getCustomerId())
                .header(TOKEN_HEADER,(TOKEN_PREFIX+" "+GOOD_JWT))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testDeleteCustomerFailure() throws Exception {

        Customer existingCustomer = CustomerUtil.createCustomer();
        Customer savedCustomer= customerRepository.saveAndFlush(existingCustomer);
        assertTrue(savedCustomer.getId()!=0);

        MockHttpServletRequestBuilder request = delete("/customer/delete/customerId/"+UUID.randomUUID().toString())
                .header(TOKEN_HEADER,(TOKEN_PREFIX+" "+GOOD_JWT))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testGetCustomerWithSpecificAddressSuccess() throws Exception {

        Customer existingCustomer = CustomerUtil.createCustomer();
        Customer savedCustomer= customerRepository.saveAndFlush(existingCustomer);
        assertTrue(savedCustomer.getId()!=0);

        MockHttpServletRequestBuilder request = get("/customer/get/addressId/"+savedCustomer.getAddresses().get(0).getAddressId())
                .header(TOKEN_HEADER,(TOKEN_PREFIX+" "+GOOD_JWT))
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testGetCustomerWithSpecificAddressFailure() throws Exception {

        Customer existingCustomer = CustomerUtil.createCustomer();
        Customer savedCustomer= customerRepository.saveAndFlush(existingCustomer);
        assertTrue(savedCustomer.getId()!=0);

        MockHttpServletRequestBuilder request = get("/customer/get/addressId/"+UUID.randomUUID().toString())
                .header(TOKEN_HEADER,(TOKEN_PREFIX+" "+GOOD_JWT))
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}
