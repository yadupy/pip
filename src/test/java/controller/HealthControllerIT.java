package controller;

import com.accenture.pip.customermanagement.CustomerManagementApplication;
import com.accenture.pip.customermanagement.dto.HealthStatus;
import com.accenture.pip.customermanagement.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import util.CustomerUtil;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = {CustomerManagementApplication.class})
@TestPropertySource("classpath:application-test.properties")
public class HealthControllerIT {

    public static final HealthStatus DEFAULT_STATUS = HealthStatus.UP;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void getHealth() throws Exception{

        mockMvc
                .perform(get("/health"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));


    }
}
