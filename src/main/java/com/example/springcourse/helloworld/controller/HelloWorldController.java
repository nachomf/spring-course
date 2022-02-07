package com.example.springcourse.helloworld.controller;

import com.example.springcourse.helloworld.model.HelloWorldBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@RestController
public class HelloWorldController {

    private final MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/hello-world")
    public String getHelloWorld() {
        return "Hello World";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean getHelloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    @GetMapping("/hello-world/{name}")
    public HelloWorldBean getHelloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    @GetMapping("/hello-world-internationalized")
    public String getHelloWorldInternationalized() {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String dayToday = DateFormat
                .getDateInstance(DateFormat.MEDIUM, currentLocale)
                .format(new Date());
        String[] params = new String[]{dayToday};
        return messageSource.getMessage(
                "good.morning.message",
                params,
                "Default Good Morning",
                currentLocale);
    }

}
