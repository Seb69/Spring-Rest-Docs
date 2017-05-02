package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(DetailsServiceClient2.class)
public class DetailsServiceClientIntegrationTest {

    @Autowired
    private DetailsServiceClient2 client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void whenCallingGetUserDetails_thenClientExecutesCorrectCall() throws Exception {

        String detailsString = objectMapper.writeValueAsString(new Details("John Smith", "john"));
        this.server.expect(requestTo("/john/details")).andExpect(method(HttpMethod.GET)).andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));
        Details details = this.client.getUserDetails("john");

        assertThat(details.getLogin()).isEqualTo("john");
        assertThat(details.getName()).isEqualTo("John Smith");
        server.verify();

    }

}
