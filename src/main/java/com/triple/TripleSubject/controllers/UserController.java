package com.triple.TripleSubject.controllers;

import com.triple.TripleSubject.dtos.UserPointResponse;
import com.triple.TripleSubject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/points",method = RequestMethod.GET)
    public List<UserPointResponse> getUserPoints(){
        return userService.getUsersPoint();
    }

    @RequestMapping(path ="/users/{userId}/points", method = RequestMethod.GET)
    public UserPointResponse getUserPoint(@PathVariable("userId")String userId){
        return userService.getUserPointById(userId);
    }
}
