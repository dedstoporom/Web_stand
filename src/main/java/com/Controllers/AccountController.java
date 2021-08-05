package com.Controllers;

import com.Models.User;
import com.Models.dto.CaptchaResponceDto;
import com.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class AccountController
{
    private final static String CAPTCHA_URL="https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String secret;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    UserService userService;//Заменили repository на сервис по модели MVC ,где логика в сервисах
    @GetMapping("/registration")
    private String registration()
    {
    return "registration";
    }

    @PostMapping("/registration")
    private String add_user(
                            @RequestParam("g-recaptcha-response") String captchaResponse
                            ,@Valid User user,
                            BindingResult bindingResult,
                            Model model)// log/pass берутся из названия input
    {
       String url= String.format(CAPTCHA_URL,secret,captchaResponse);
      CaptchaResponceDto responce= restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponceDto.class);
      if(!responce.isSuccess())
      {
        model.addAttribute("captchaError","Fill captcha");
      }
        if(bindingResult.hasErrors())
        {
            Map <String,Object> errorsMap=ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            return "registration";
        }
        else {
            if (!userService.add_user(user)) //краткая запись ...==false
            {
                model.addAttribute("messages", "User is exists");
                return "registration";
            }
        }
            return "redirect:/login";
    }
    @GetMapping("/activate/{code}")
    private String activate(Map<String,Object> model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if(isActivated==true)
        {
            model.put("messages","User successfully activated");
        }
        else
        {
            model.put("messages","Activation code is not founded");

        }
        return "login";
    }
}
