package com.Models.dto;

import com.Models.Message;
import com.Models.User;
import com.Models.Util.MessageHelper;

public class MessageDto
{
    private Long id;
    private String text;
    private String tag;
    private String date;
    private String filename;
    private User author;
    private Long likes;
    private Boolean meLiked;

    public MessageDto(Message message,Long likes, Boolean meLiked)
    {
        this.id=message.getId();
        this.text=message.getText();
        this.tag=message.getTag();
        this.date=message.getDate();
        this.author=message.getAuthor();
        this.filename=message.getFilename();
        this.likes = likes;
        this.meLiked = meLiked;
    }
    public String getAuthorName()
    {
        return MessageHelper.getAuthorName(author);
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }

    public String getDate() {
        return date;
    }

    public String getFilename() {
        return filename;
    }

    public Long getLikes() {
        return likes;
    }

    public Boolean getMeLiked() {
        return meLiked;
    }
    //необязательно [вывод в консоль]
    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", author=" + author +
                ", likes=" + likes +
                ", meLiked=" + meLiked +
                '}';
    }
}
