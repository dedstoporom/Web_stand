package com.Models.Util;

import com.Models.User;

public abstract class MessageHelper
{
    public static String getAuthorName(User author)
    {
        return author!=null? author.getUsername(): "[anon]";
    }
}
