package com.Controllers;

import com.Models.Message;
import com.Models.User;
import com.Models.dto.MessageDto;
import com.Repository.MessageRepository;
import com.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController
{
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private  MessageService messageService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String home_page()
    {
        return "home";
    }
    @GetMapping("/main")
    public String main_page(@RequestParam(required = false) String tag_field,
                            Map<String,Object> model
                            ,@AuthenticationPrincipal User user)
    {
        Iterable<MessageDto> messages;
        messages= messageService.messageList(tag_field,user);
        model.put("messages",messages);

        return "main";
    }
    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user //текущий пользователь
//            ,@RequestParam String text
//            ,@RequestParam String tag //| заменяем оба на Message
            , @Valid Message message,
               BindingResult bindingResult //для обработки ошибок валидации после него Model
            , Model model
            // ,Map<String,Object> model
            , @RequestParam("file") MultipartFile file) {
        ////////////////////////////
        Date eq_date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm | dd.mm.yy");
        String date = dateFormat.format(eq_date);
        ///////////////////////////
//                Message message=new Message(text_box,tag_box,user,date);
        message.setAuthor(user);//походу так заполнются другие поля автоматом (и остается author)
        message.setDate(date);
        if (bindingResult.hasErrors()) {
            Map<String, Object> errorsMap = ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);//для заполнениея поля при ошибке
        }
        else
        {
            if (!file.isEmpty() && !file.getOriginalFilename().isEmpty())//по оригиналу имени так как файл не равен null
            {
                File uploadDir = new File(uploadPath);//объявление пути
                if (!uploadDir.exists())
                {
                    uploadDir.mkdir();
                }
                String uuid_file = UUID.randomUUID().toString();
                String result_filename = uuid_file + "_" + file.getOriginalFilename();
                try {
                    file.transferTo(new File(uploadPath + "/" + result_filename));//загрузка в директорию
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                message.setFilename(result_filename);
            }
            if (message.getTag().isEmpty()) {
                message.setTag(null);
            }
            messageRepository.save(message);
                }
            Iterable<MessageDto> messages = messageRepository.findAll(user);
            model.addAttribute("messages", messages);
            return "main";

        }

}
