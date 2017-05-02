package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Tester : MyBean
 *
 * @author ANDRE
 * @since 02/05/2017
 */
@RunWith(SpringRunner.class)
@RestClientTest
public class MyBeanTest {


    @Autowired
    private MyBean myBean;

    @Autowired
    private MockRestServiceServer server2;

    @Autowired
    private ObjectMapper objectMapper;




        @Test
        public void whenCallingGetUserDetails_thenClientExecutesCorrectCall() throws Exception {


        String detailsString = objectMapper.writeValueAsString(new Details("John Smith", "john"));
        this.server2.expect(requestTo("/john/details")).andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));
            Details details = this.myBean.getUserDetails("john");

            assertThat(details.getLogin()).isEqualTo("john");
            assertThat(details.getName()).isEqualTo("John Smith");

        }




}
