package com.triple.TripleSubject.services;

import com.triple.TripleSubject.dtos.EventDto;
import com.triple.TripleSubject.entities.*;
import com.triple.TripleSubject.enums.ReviewState;
import com.triple.TripleSubject.repositories.*;
import com.triple.TripleSubject.util.Duplicator;
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

    @Autowired
    private Duplicator duplicator;

    @Transactional
    public EventDto postAddEvent(EventDto eventDto){
        validator.validateUuid(eventDto.getUserId(),"userId 형식이 유효하지 않습니다.");
        validator.validateUuid(eventDto.getPlaceId(),"placeId 형식이 유효하지 않습니다.");
        validator.validateUuid(eventDto.getReviewId(),"reviewId 형식이 유효하지 않습니다.");

        //장소가 이미 존재하는지 검사
        Place place=Place.builder().uuid(eventDto.getPlaceId()).build();
        if(duplicator.placeDuplicatedCheck(place))
            placeRepository.save(place);
        else
            place=placeRepository.findByUuid(eventDto.getPlaceId());

        //포인트 계산
        int point=0;
        if(reviewRepository.findByPlaceId(place.getId()).size()==0){
            point++;
        }
        if(eventDto.getAttachedPhotoIds().size()>0){
            point++;
        }
        if(eventDto.getContent().length()>0){
            point++;
        }

        //유저가 이미 존재하는지 검사
        User user=User.builder().uuid(eventDto.getUserId()).achievePoint(point).build();
        if(duplicator.userDuplicatedCheck(user))
            userRepository.save(user);
        else
            user=userRepository.findByUuid(eventDto.getUserId());

        duplicator.reviewDuplicatedCheck(user,place,"해당 장소에 대한 리뷰를 이미 작성했습니다.");

        Review review=Review.builder().uuid(eventDto.getReviewId()).creator(user)
                .place(place).state(ReviewState.alive).content(eventDto.getContent()).build();

        Event event=Event.builder().review(review).user(user).event(eventDto).pointDelta(point).build();

        reviewRepository.save(review);
        for(String imageUuid : eventDto.getAttachedPhotoIds()){
            Image image=Image.builder().uuid(imageUuid).review(review).build();
            imageRepository.save(image);
        }
        eventRepository.save(event);

        return eventDto;
    }
}
