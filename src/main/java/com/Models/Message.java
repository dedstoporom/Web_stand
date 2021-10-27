package com.Models;
import com.Models.Util.MessageHelper;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Message
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank (message = "Enter message")
    @Length(max = 2048, message="Length of your message is too long")
    private String text;
    @Length(max = 255, message="Length of your tag is too long")
    private String tag;
    private String date;
    private String filename;
    @ManyToMany
    @JoinTable
            (
            name="message_likes",
                    joinColumns = {@JoinColumn(name = "message_id")},
                    inverseJoinColumns={@JoinColumn(name = "user_id")}
            )
    private Set<User> likes=new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User author;
    public Message()
    {
        this.text=text;
        this.tag=tag;
        this.date=date;
    }
    public Message(String text,String tag,User user,String date)
    {
        this.author=user;
        this.text=text;
        this.tag=tag;
        this.date=date;
    }
    public String getAuthorName()
    {
        return MessageHelper.getAuthorName(author);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getDate()
    {
        return date!=null?date:"null";
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }
}
