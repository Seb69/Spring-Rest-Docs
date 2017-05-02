package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApiDocumentationIntegrationTest {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(documentationConfiguration(this.restDocumentation)).build();
    }


    @Test
    public void crudCreateExample() throws Exception {
        Map<String, String> tag = new HashMap<>();
        tag.put("name", "CREATE");

        String tagLocation = this.mockMvc.perform(post("/crud").contentType(MediaType.APPLICATION_JSON_VALUE).content(this.objectMapper.writeValueAsString(tag))).andExpect(status().isCreated()).andReturn().getResponse().getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        this.mockMvc.perform(post("/crud").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(crud)))
                .andExpect(status().isCreated());

    }

    @Test
    public void crudPatchExample() throws Exception {

        Map<String, String> tag = new HashMap<>();
        tag.put("name", "PATCH");

        String tagLocation = this.mockMvc.perform(patch("/crud/10").contentType(MediaType.APPLICATION_JSON_VALUE).content(this.objectMapper.writeValueAsString(tag))).andExpect(status().isNoContent()).andReturn().getResponse().getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        this.mockMvc.perform(patch("/crud/10").contentType(MediaType.APPLICATION_JSON_VALUE).content(this.objectMapper.writeValueAsString(crud))).andExpect(status().isNoContent());
    }

    @Test
    public void crudPutExample() throws Exception {
        Map<String, String> tag = new HashMap<>();
        tag.put("name", "PUT");

        String tagLocation = this.mockMvc.perform(put("/crud/10").contentType(MediaType.APPLICATION_JSON_VALUE).content(this.objectMapper.writeValueAsString(tag))).andExpect(status().isAccepted()).andReturn().getResponse().getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        this.mockMvc.perform(put("/crud/10").contentType(MediaType.APPLICATION_JSON_VALUE).content(this.objectMapper.writeValueAsString(crud))).andExpect(status().isAccepted());
    }

    @Test
    public void contextLoads() {
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". ")));
        }
    }

}
