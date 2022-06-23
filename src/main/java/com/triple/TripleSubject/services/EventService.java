package com.triple.TripleSubject.services;

import com.triple.TripleSubject.dtos.EventDto;
import com.triple.TripleSubject.entities.*;
import com.triple.TripleSubject.enums.ReviewState;
import com.triple.TripleSubject.exceptions.CheckedException;
import com.triple.TripleSubject.repositories.*;
import com.triple.TripleSubject.util.Duplicator;
import com.triple.TripleSubject.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        for(String imageUuid:eventDto.getAttachedPhotoIds()){
            validator.validateUuid(imageUuid,"imageId 형식이 유효하지 않습니다.");
        }

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

    @Transactional
    public EventDto postModEvent(EventDto eventDto){
        validator.validateUuid(eventDto.getUserId(),"userId 형식이 유효하지 않습니다.");
        validator.validateUuid(eventDto.getPlaceId(),"placeId 형식이 유효하지 않습니다.");
        validator.validateUuid(eventDto.getReviewId(),"reviewId 형식이 유효하지 않습니다.");
        for(String imageUuid:eventDto.getAttachedPhotoIds()){
            validator.validateUuid(imageUuid,"imageId 형식이 유효하지 않습니다.");
        }

        Place place = placeRepository.findByUuid(eventDto.getPlaceId());
        if(place==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"일치하는 placeId가 없습니다.");

        Review review = reviewRepository.findByUuid(eventDto.getReviewId());
        if(review == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"일치하는 reviewId가 없습니다.");

        User user = userRepository.findByUuid(eventDto.getUserId());
        if(user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"일치하는 userId가 없습니다.");

        //포인트 계산
        int point = 0;
        if(review.getContent().length() > 0 && eventDto.getContent().length()==0){
            point --;
        }
        if(review.getContent().length() == 0 && eventDto.getContent().length() > 0){
            point++;
        }

        List<Image> images=imageRepository.findByReviewId(review.getId());
        if(images.size()>0 && eventDto.getAttachedPhotoIds().size()==0){
            point --;
        }
        if(images.size()==0 && eventDto.getAttachedPhotoIds().size()>0){
            point++;
        }

        user.setAchievePoint(user.getAchievePoint()+point);
        userRepository.save(user);

        review.setState(ReviewState.notAlive);
        review.setUuid("");
        reviewRepository.save(review);
        review=Review.builder().uuid(eventDto.getReviewId()).creator(user)
                .place(place).state(ReviewState.alive).content(eventDto.getContent()).build();
        reviewRepository.save(review);

        Event event=Event.builder().review(review).user(user).event(eventDto).pointDelta(point).build();
        eventRepository.save(event);

        for(String imageUuid : eventDto.getAttachedPhotoIds()){
            Image image=Image.builder().uuid(imageUuid).review(review).build();
            imageRepository.save(image);
        }
        return eventDto;
    }
}
