package controller;

import com.accenture.pip.customermanagement.CustomerManagementApplication;
import com.accenture.pip.customermanagement.dto.AddressUpdateRequest;
import com.accenture.pip.customermanagement.entity.Address;
import com.accenture.pip.customermanagement.entity.Customer;
import com.accenture.pip.customermanagement.mapper.CustomerMapper;
import com.accenture.pip.customermanagement.repository.AddressRepository;
import com.accenture.pip.customermanagement.repository.CustomerRepository;
import com.accenture.pip.customermanagement.service.AddressService;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import util.CustomerUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = {CustomerManagementApplication.class})
@TestPropertySource("classpath:application-test.properties")
public class AddressControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AddressService addressService;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerMapper mapper;


    private static final String GOOD_JWT = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJUMXBoanNldTlWNjVscnFHZWl";

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    @Test
    public void testGetAddress_Success() throws Exception {
        Customer customer = CustomerUtil.createCustomer();

        Customer savedCustomer = customerRepository.saveAndFlush(customer);
        assertTrue(savedCustomer.getId()!=0);
        String addressId = savedCustomer.getAddresses().get(0).getAddressId();
        MockHttpServletRequestBuilder request = get("/address/get/"+addressId)
                .header(TOKEN_HEADER,(TOKEN_PREFIX+" "+GOOD_JWT))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAddress_Failure() throws Exception {
        Customer customer = CustomerUtil.createCustomer();
        Customer savedCustomer = customerRepository.saveAndFlush(customer);
        assertTrue(savedCustomer.getId()!=0);

        MockHttpServletRequestBuilder request = get("/address/get/"+ UUID.randomUUID().toString())
                .header(TOKEN_HEADER,(TOKEN_PREFIX+" "+GOOD_JWT))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateAddress_Success() throws Exception {

        Customer customer = CustomerUtil.createCustomer();
        Customer savedCustomer = customerRepository.saveAndFlush(customer);
        assertTrue(savedCustomer.getId()!=0);

        String body = Resources.toString(Resources.getResource("payloads/updateAddress.json"), StandardCharsets.UTF_8);
        String addressId = savedCustomer.getAddresses().get(0).getAddressId();

        MockHttpServletRequestBuilder request = patch("/address/update/"+addressId)
                .header(TOKEN_HEADER,(TOKEN_PREFIX+" "+GOOD_JWT))
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateAddress_Failure() throws Exception {
        Customer customer = CustomerUtil.createCustomer();
        Customer savedCustomer = customerRepository.saveAndFlush(customer);
        assertTrue(savedCustomer.getId()!=0);

        String body = Resources.toString(Resources.getResource("payloads/updateAddress.json"), StandardCharsets.UTF_8);

        MockHttpServletRequestBuilder request = patch("/address/update/"+ UUID.randomUUID().toString())
                .header(TOKEN_HEADER,(TOKEN_PREFIX+" "+GOOD_JWT))
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }



}
