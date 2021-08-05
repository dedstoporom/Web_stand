package com.Controllers;

import com.Models.Message;
import com.Models.Role;
import com.Models.User;
import com.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@Controller
//@RequestMapping("/user")//Для каждого метода этого класса используется адрес /user
public class UserController
{
    @Autowired
    UserService userService;
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/user")
    public String user_list(Map<String,Object> model)
    {
        Iterable<User> user_list;
        user_list=userService.findAll();
        model.put("user_list",user_list);
        return "userList";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/user/{user}")
    public String edit_user(@PathVariable User user, Model model)
    {
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());
        return "userEditor";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public String save_edit(@RequestParam String username
            ,@RequestParam Map<String,String> form,
            @RequestParam("userId") User user)
    {
        userService.saveUser(user,username,form);
        return "redirect:/user";
    }
    @GetMapping("/user/myprofile")
    public String myProfile(@AuthenticationPrincipal User user, Map<String,Object> model)
    {
        model.put("user",user);
        return "myprofile";
    }
    @PostMapping("/user/myprofile")
    public String save_profile(@AuthenticationPrincipal User user,
                               @RequestParam String email,
                               @RequestParam String password)
    {
            userService.updateUser(user,email,password);
            return "redirect:/main";
    }
    @GetMapping("/user/profile/{user}")
    public String userProfile(@AuthenticationPrincipal User currentUser,
                              @PathVariable User user,
                              Model model)
    {
        Set<Message> messages=user.getMessages();
        Set <User> userList=user.getSubscribers();
        model.addAttribute("usersList",userList);
        model.addAttribute("messages",messages);
        model.addAttribute("userChannel",user);
        model.addAttribute("isSubscriber",user.getSubscribers().contains(currentUser));
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("Subscriptions",user.getSubscriptions().size());
        model.addAttribute("Subscribers",user.getSubscribers().size());
        model.addAttribute("isCurrentUser",currentUser.equals(user));
        return "profile";
    }
    @GetMapping("/user/profile/unsubscribe/{user}")
    public String channel_subscribe(@AuthenticationPrincipal User currentUser,
                                 @PathVariable User user)
    {
        userService.unsubscribe(user,currentUser);
        return "redirect:/user/profile/"+user.getId();
    }
    @GetMapping("/user/profile/subscribe/{user}")
    public String channel_unsubscribe(@AuthenticationPrincipal User currentUser,
                                 @PathVariable User user)
    {
        userService.subscribe(user,currentUser);
        return "redirect:/user/profile/"+user.getId();
    }
    @GetMapping("/user/profile/{type}/list/{user}")
    public String subscriber_list (@PathVariable User user,Model model,@PathVariable String type)
    {
        Set<User> userList;
        if(type.equals("subscribers"))
        {
            userList=user.getSubscribers();
        }
        else
            {
                userList =user.getSubscriptions();
            }
        model.addAttribute("userList",userList);
        model.addAttribute("user",user);
        return "subsList";
    }
}
