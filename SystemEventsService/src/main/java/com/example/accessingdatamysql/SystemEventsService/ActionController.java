package com.example.accessingdatamysql.SystemEventsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/nwt")
public class ActionController {
    @Autowired
    private ActionRepository actionRepository;


    @GetMapping(path = "/system-events")
    public  @ResponseBody
    Iterable<Action> getAllSystemEvents(){
        return actionRepository.findAll();
    }
}
