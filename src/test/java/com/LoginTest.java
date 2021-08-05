package com;

import com.Controllers.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value={"/create_user_before.sql","/messages_list_before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/messages_list_after.sql","/create_user_after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LoginTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController controller;
    @Test
    public void homeTest() throws Exception
    {
        this.mockMvc
                .perform(get("/")) //запрос на главную страницу
                .andDo(print()) //pring log
                .andExpect(status().isOk()) //проверка возврата кода 200
                .andExpect(content().string(containsString("Home page"))); //проверка наличие записи
    }
    @Test
    public void redirectLoginTest() throws Exception
    {
        this.mockMvc
                .perform(get("/main"))
                .andDo(print())
                .andExpect(status().is3xxRedirection()) //код 300 (код перенаправление )
                .andExpect(redirectedUrl("http://localhost/login"));
    }
    @Test
    @Sql(value={"/create_user_before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value={"/create_user_after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void correctLogin() throws Exception
    {
        this.mockMvc
                .perform(formLogin().user("user").password("1")) //взятие формы логин SpringSecurity
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
    @Test
    public void badCredentials() throws Exception
    {
        this.mockMvc
                .perform(post("/login").param("user","Adolf"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    @WithUserDetails("user")
    public void accessToAdminPanel () throws Exception
    {
        this.mockMvc
                .perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/nav/div/div/ul/li[3]/a").exists());
    }
}

