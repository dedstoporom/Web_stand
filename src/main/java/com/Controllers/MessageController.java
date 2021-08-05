package com.Controllers;

import com.Models.Message;
import com.Models.User;
import com.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Set;

@Controller
//@PreAuthorize("hasAnyAuthority('ADMIN')")
public class MessageController
{
    @Autowired
    MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;
    @GetMapping("/message/{message}")
    public String message_editor(@PathVariable Message message,
                                 Map<String,Object> model)
    {
        model.put("message",message);
        return "messageEditor";
    }
    @PostMapping("/message/{message}")
    public String edit(@RequestParam String text,
                       @RequestParam String tag,
                         @RequestParam("messageId") Message message
                        )
    {
        message.setTag(tag);
        message.setText(text);
        messageRepository.save(message);
        return "redirect:/main";
    }
        @GetMapping("/message/{message}/like")
        public String likeMessage(@PathVariable Message message
                                , @AuthenticationPrincipal User currentUser,
                                  RedirectAttributes redirectAttributes,
                                  @RequestHeader(required = false) String referer)
        {
            Set<User> likes=message.getLikes();
            if(likes.contains(currentUser))
            {
                likes.remove(currentUser);
            }
            else
            {
                likes.add(currentUser);
            }

            UriComponents components=UriComponentsBuilder.fromHttpUrl(referer).build();
            components.getQueryParams().entrySet()
                    .forEach(pair->redirectAttributes.addAttribute(pair.getKey(),pair.getValue()));
            return "redirect:"+components.getPath();
        }
}
