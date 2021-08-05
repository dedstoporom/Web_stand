package com;

import com.Controllers.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@AutoConfigureMockMvc
@SpringBootTest
@WithUserDetails("user") //можно на класс или на метод
@TestPropertySource("/application-test.properties")
@Sql(value={"/create_user_before.sql","/messages_list_before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/messages_list_after.sql","/create_user_after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainTest
{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MainController controller;
    @Test
    public void mainPageTest() throws Exception
    {
        this.mockMvc
                .perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/nav/div/div/div/span").string("user"));
    }
    @Test
    public void messageTest() throws Exception
    {
        this.mockMvc
                .perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(4)); //возвращает кол-во узлов
                                            //div в конце для поиска div внутри контейнера
    }
    @Test
    public void filterTest() throws Exception
    {
        this.mockMvc
                .perform(get("/main").param("filter","hello"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=1]").exists())
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=3]").exists());
    }
    @Test
    public void addMessageToListTest() throws Exception
    {
        MockHttpServletRequestBuilder multipart=multipart("/main")
                .file("file","123".getBytes())
                .param("text","someone")
                .param("tag","#some")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(5))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=10]").exists());
//                .andExpect(xpath("//*[@id='message-list']/div[@data-id=10]/div/span").string("someone"))
//                .andExpect(xpath("//*[@id='message-list']/div[@data-id=10]/div/span/span").string("#some"));

    }
}
