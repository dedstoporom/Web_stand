package com.Util;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
     if(modelAndView!=null)
     {
         String arguments=request.getQueryString()!=null?request.getQueryString():"";//аргументы после ? в строке запросе (не всегда могут быть)
         String url=request.getRequestURI().toString()+"?"+arguments;
         response.setHeader("Turbolinks-Location",url);
     }
    }
}
