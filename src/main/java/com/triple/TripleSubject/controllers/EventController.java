package com.triple.TripleSubject.controllers;

import com.triple.TripleSubject.dtos.EventDto;
import com.triple.TripleSubject.enums.EventAction;
import com.triple.TripleSubject.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private ReviewService reviewService;

    @RequestMapping(method = RequestMethod.POST)
    public EventDto postEvent(@RequestBody EventDto eventDto){

        if (EventAction.ADD.name().equals(eventDto.getAction())) {
            reviewService.postAddReview(eventDto);
        }else if(EventAction.MOD.name().equals(eventDto.getAction())){
            reviewService.postModReview(eventDto);
        }else if(EventAction.DELETE.name().equals(eventDto.getAction())){
            reviewService.postDeleteReview(eventDto);
        }

        return eventDto;
    }


}
