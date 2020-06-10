package com.bigcow.com.spi.sevice.impl1;

import com.bigcow.com.spi.service.GreetingService;

public class GreetingServiceImpl1 implements GreetingService {

    public String sayHi(String name) {
        return "true spi, hello. " + name;
    }
}
