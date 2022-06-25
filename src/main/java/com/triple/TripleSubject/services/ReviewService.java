package com.triple.TripleSubject.services;

import com.triple.TripleSubject.dtos.EventDto;
import com.triple.TripleSubject.entities.*;
import com.triple.TripleSubject.enums.ReviewState;
import com.triple.TripleSubject.exceptions.DataNotFoundException;
import com.triple.TripleSubject.exceptions.DuplicatedException;
import com.triple.TripleSubject.exceptions.ValidationException;
import com.triple.TripleSubject.repositories.*;
import com.triple.TripleSubject.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReviewService {

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
    public EventDto postAddReview(EventDto eventDto){
        validating(eventDto);

        for(String imageUuid:eventDto.getAttachedPhotoIds()){
            validator.validateUuid(imageUuid,"imageId 형식이 유효하지 않습니다.");
        }

        //장소가 이미 존재하는지 검사
        Place place=Place.builder().uuid(eventDto.getPlaceId()).build();
        if(!placeRepository.existsByUuid(eventDto.getPlaceId()))
            placeRepository.save(place);
        else
            place=placeRepository.findByUuid(eventDto.getPlaceId());

        //포인트 계산
        int point=0;
        if(reviewRepository.findByPlaceIdAndState(place.getId(),ReviewState.alive).isEmpty()){
            point++;
        }
        if(!eventDto.getAttachedPhotoIds().isEmpty()){
            point++;
        }
        if(!eventDto.getContent().isEmpty()){
            point++;
        }

        //유저가 이미 존재하는지 검사
        User user=User.builder().uuid(eventDto.getUserId()).achievePoint(point).build();
        if(!userRepository.existsByUuid(eventDto.getUserId()))
            userRepository.save(user);
        else {
            user = userRepository.findByUuid(eventDto.getUserId());
            user.setAchievePoint(user.getAchievePoint() + point);
        }

        if(reviewRepository.existsByCreatorAndPlaceAndState(user,place,ReviewState.alive))
            throw new DuplicatedException("해당 장소에 대한 리뷰를 이미 작성했습니다.");

        Review review=Review.builder().uuid(eventDto.getReviewId()).creator(user)
                .place(place).state(ReviewState.alive).content(eventDto.getContent()).build();

        if(reviewRepository.existsByUuidAndState(eventDto.getReviewId(),ReviewState.alive))
            throw new ValidationException("reviewId가 존재합니다.");
        
        Event event=Event.builder().review(review).user(user).event(eventDto).pointDelta(point).place(place).build();

        reviewRepository.save(review);
        for(String imageUuid : eventDto.getAttachedPhotoIds()){
            Image image=Image.builder().uuid(imageUuid).review(review).build();
            imageRepository.save(image);
        }
        eventRepository.save(event);

        return eventDto;
    }

    @Transactional
    public EventDto postModReview(EventDto eventDto){
        validating(eventDto);

        for(String imageUuid:eventDto.getAttachedPhotoIds()){
            validator.validateUuid(imageUuid,"imageId 형식이 유효하지 않습니다.");
        }

        Place place = placeRepository.findByUuid(eventDto.getPlaceId());
        if(place==null) throw new DataNotFoundException("일치하는 placeId가 없습니다.");

        Review review = reviewRepository.findByUuidAndState(eventDto.getReviewId(),ReviewState.alive);
        if(review == null) throw new DataNotFoundException("일치하는 reviewId가 없습니다.");

        User user = userRepository.findByUuid(eventDto.getUserId());
        if(user == null) throw new DataNotFoundException("일치하는 userId가 없습니다.");

        if(!review.getCreator().getUuid().equals(user.getUuid()))
            throw new ValidationException("리뷰 작성자가 아닙니다.");

        if(!review.getPlace().getUuid().equals(place.getUuid()))
            throw new ValidationException("해당 장소에 대한 리뷰가 아닙니다.");

        //포인트 계산
        int point = 0;
        if(!review.getContent().isEmpty() && eventDto.getContent().isEmpty()){
            point --;
        }
        if(review.getContent().isEmpty() && !eventDto.getContent().isEmpty()){
            point++;
        }

        int imageSize=imageRepository.countByReviewId(review.getId());
        if(imageSize>0 && eventDto.getAttachedPhotoIds().isEmpty()){
            point --;
        }
        if(imageSize==0 && eventDto.getAttachedPhotoIds().size()>0){
            point++;
        }

        user.setAchievePoint(user.getAchievePoint()+point);
        userRepository.save(user);

        review.setState(ReviewState.notAlive);
        reviewRepository.save(review);

        review=Review.builder().uuid(eventDto.getReviewId()).creator(user)
                .place(place).state(ReviewState.alive).content(eventDto.getContent()).build();
        reviewRepository.save(review);

        Event event=Event.builder().review(review).user(user).event(eventDto).pointDelta(point).place(place).build();
        eventRepository.save(event);

        for(String imageUuid : eventDto.getAttachedPhotoIds()){
            Image image=Image.builder().uuid(imageUuid).review(review).build();
            imageRepository.save(image);
        }
        return eventDto;
    }

    @Transactional
    public void postDeleteReview(EventDto eventDto){
        validating(eventDto);

        Place place = placeRepository.findByUuid(eventDto.getPlaceId());
        if(place==null) throw new DataNotFoundException("일치하는 placeId가 없습니다.");

        User user = userRepository.findByUuid(eventDto.getUserId());
        if(user == null) throw new DataNotFoundException("일치하는 userId가 없습니다.");

        Review review = reviewRepository.findByUuidAndState(eventDto.getReviewId(),ReviewState.alive);
        if(review == null) throw new DataNotFoundException("일치하는 reviewId가 없습니다.");

        if(!review.getCreator().getUuid().equals(user.getUuid()))
            throw new ValidationException("리뷰 작성자가 아닙니다.");

        List<Event> event=eventRepository.findByUserAndPlace(user,place);
        if(event == null)return;
        int point=0;
        for(Event e: event) {
            point+=e.getPointDelta();
        }

        user.setAchievePoint(user.getAchievePoint()-point);
        userRepository.save(user);

        review.setState(ReviewState.notAlive);
        reviewRepository.save(review);

        Event e=Event.builder().review(review).user(user).event(eventDto).pointDelta(-1*point).place(place).build();
        eventRepository.save(e);
    }

    public void validating(EventDto eventDto){
        validator.validateUuid(eventDto.getUserId(),"userId 형식이 유효하지 않습니다.");
        validator.validateUuid(eventDto.getPlaceId(),"placeId 형식이 유효하지 않습니다.");
        validator.validateUuid(eventDto.getReviewId(),"reviewId 형식이 유효하지 않습니다.");
    }
}
