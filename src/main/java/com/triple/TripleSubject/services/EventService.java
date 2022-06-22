package com.triple.TripleSubject.services;

import com.triple.TripleSubject.dtos.EventDto;
import com.triple.TripleSubject.entities.*;
import com.triple.TripleSubject.enums.ReviewState;
import com.triple.TripleSubject.repositories.*;
import com.triple.TripleSubject.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private Validator validator;

    @Transactional
    public EventDto postAddEvent(EventDto eventDto){
        validator.validateUuid(eventDto.getUserId(),"userId 형식이 유효하지 않습니다.");
        validator.validateUuid(eventDto.getPlaceId(),"placeId 형식이 유효하지 않습니다.");
        validator.validateUuid(eventDto.getReviewId(),"reviewId 형식이 유효하지 않습니다.");

        Place place=Place.builder().uuid(eventDto.getPlaceId()).build();
        User user=User.builder().uuid(eventDto.getUserId()).achievePoint(0).build();
        Review review=Review.builder().uuid(eventDto.getReviewId()).creator(user)
                .place(place).state(ReviewState.alive).content(eventDto.getContent()).build();
        Event event=Event.builder().review(review).user(user).event(eventDto).pointDelta(0).build();

        userRepository.save(user);
        placeRepository.save(place);
        reviewRepository.save(review);
        for(String imageUuid : eventDto.getAttachedPhotoIds()){
            Image image=Image.builder().uuid(imageUuid).review(review).build();
            imageRepository.save(image);
        }
        eventRepository.save(event);

        return eventDto;
    }
}
