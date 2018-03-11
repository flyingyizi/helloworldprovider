package com.service.helloworldprovider.controller;


import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.servicecomb.provider.rest.common.RestSchema;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.CseSpringDemoCodegen", date = "2018-02-03T22:02:12.269+08:00")

@RestSchema(schemaId = "helloworldprovider")
@RequestMapping(path = "/helloworldprovider", produces = MediaType.APPLICATION_JSON)
public class HelloworldproviderImpl {

    @Autowired
    private HelloworldproviderDelegate userHelloworldproviderDelegate;


    @RequestMapping(value = "/helloworld",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    public String helloworld( @RequestParam(value = "name", required = true) String name){

        return userHelloworldproviderDelegate.helloworld(name);
    }

}
