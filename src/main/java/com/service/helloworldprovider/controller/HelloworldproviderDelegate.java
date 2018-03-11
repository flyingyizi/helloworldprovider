package com.service.helloworldprovider.controller;

import org.springframework.stereotype.Component;


@Component
public class HelloworldproviderDelegate {

    public String helloworld(String name){

        // Do Some Magic Here!
        //return name;
    	return "welcome" + name;
    }
}
