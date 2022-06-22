package com.triple.TripleSubject.controllers;

import com.triple.TripleSubject.dtos.EventDto;
import com.triple.TripleSubject.exceptions.CheckedException;
import com.triple.TripleSubject.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.POST)
    public EventDto postEvent(@RequestBody EventDto eventDto){

        if ("ADD".equals(eventDto.getAction())) {
            eventService.postAddEvent(eventDto);
        }

        return eventDto;
    }


}
