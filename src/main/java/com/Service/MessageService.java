package com.Service;

import com.Models.Message;
import com.Models.User;
import com.Models.dto.MessageDto;
import com.Repository.MessageRepository;
import com.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class MessageService
{

    @Autowired
    MessageRepository messageRepository;
    public Iterable<MessageDto> messageList(String tag_field, User user)
    {
        if(tag_field!=null && tag_field!="")
        {
             return messageRepository.findByTag(tag_field,user);
        }
        else
        {
            return messageRepository.findAll(user);
        }
    }
}
