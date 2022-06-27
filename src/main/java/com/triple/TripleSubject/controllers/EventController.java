package com.triple.TripleSubject.controllers;

import com.triple.TripleSubject.dtos.EventDto;
import com.triple.TripleSubject.enums.EventAction;
import com.triple.TripleSubject.exceptions.DataNotFoundException;
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
        if(eventDto==null)throw new DataNotFoundException("요청 데이터가 없습니다");
        try {
            switch (EventAction.valueOf(eventDto.getAction())) {
                case ADD:
                    reviewService.postAddReview(eventDto);
                    break;
                case MOD:
                    reviewService.postModReview(eventDto);
                    break;
                case DELETE:
                    reviewService.postDeleteReview(eventDto);
                    break;
            }
        }
        catch(IllegalArgumentException e){
            throw new DataNotFoundException("알수 없는 요청입니다.");
        }

        return eventDto;
    }


}
