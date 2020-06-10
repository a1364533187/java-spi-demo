package com.bigcow.com.spi.service.impl;

import com.bigcow.com.spi.service.GreetingService;

public class GreetingServiceImpl implements GreetingService {

    public String sayHi(String name) {
        return "true spi, hi. " + name;
    }
}
