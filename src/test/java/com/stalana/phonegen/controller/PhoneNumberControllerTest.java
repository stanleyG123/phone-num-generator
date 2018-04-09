package com.stalana.phonegen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stalana.phonegen.PhoneNumGenApplication;
import com.stalana.phonegen.model.PhoneNumberRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * PhoneNumberControllerTest controller test
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PhoneNumGenApplication.class)
@WebAppConfiguration
public class PhoneNumberControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private MockMvc mockMvc;
    List<String> combos = new ArrayList<>();

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        combos.add("123");
        combos.add("456");
        combos.add("789");
        combos.add("111");
        combos.add("222");
    }

    @Test
    public void generateAlphaNumerics() throws Exception {
        PhoneNumberRequest req = new PhoneNumberRequest();
        req.setNumberPerPage(5);
        req.setPhoneNumber("3012134567");

        ObjectMapper mapper = new ObjectMapper();
        String jsonReq = mapper.writeValueAsString(req);

        mockMvc.perform(MockMvcRequestBuilders.post("/generate")
                .content(jsonReq)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfCombos", is(20480)))
                .andExpect(jsonPath("$.pageEnd", is(4)))
                .andExpect(jsonPath("$.combos", hasSize(5)))
                .andExpect(jsonPath("$.pageStart", is(0)));
    }

    @Test
    public void generateAlphaNumerics_badNum() throws Exception {
        PhoneNumberRequest req = new PhoneNumberRequest();
        req.setNumberPerPage(5);
        req.setPhoneNumber("aaa333");

        ObjectMapper mapper = new ObjectMapper();
        String jsonReq = mapper.writeValueAsString(req);

        mockMvc.perform(MockMvcRequestBuilders.post("/generate")
                .content(jsonReq)
                .contentType(contentType))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void fetchPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/fetchPage?pageEnd=4&pageStart=0")
                .sessionAttr("all_combos", combos)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.combos", hasSize(5)));
    }

    @Test
    public void fetchPageTest_outOfBounds() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/fetchPage?pageEnd=2&pageStart=-1")
                .sessionAttr("all_combos", combos)
                .contentType(contentType))
                .andExpect(status().isInternalServerError());
    }

    private static final class SessionHolder {
        private SessionWrapper session;


        public SessionWrapper getSession() {
            return session;
        }

        public void setSession(SessionWrapper session) {
            this.session = session;
        }
    }

    private static class SessionWrapper extends MockHttpSession {
        private final HttpSession httpSession;

        public SessionWrapper(HttpSession httpSession) {
            this.httpSession = httpSession;
        }

        @Override
        public Object getAttribute(String name) {
            return this.httpSession.getAttribute(name);
        }

    }


}