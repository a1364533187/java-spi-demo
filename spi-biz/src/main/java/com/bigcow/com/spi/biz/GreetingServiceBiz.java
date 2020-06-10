package com.bigcow.com.spi.biz;

import java.util.ServiceLoader;

import com.bigcow.com.spi.service.GreetingService;

public class GreetingServiceBiz {

    public static void main(String[] args) {
        ServiceLoader<GreetingService> services = ServiceLoader.load(GreetingService.class);
        for (GreetingService service : services) {
            System.out.println(service.sayHi("susu"));
        }
    }
}
