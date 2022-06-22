package com.triple.TripleSubject.util;

import com.triple.TripleSubject.entities.Place;
import com.triple.TripleSubject.entities.User;
import com.triple.TripleSubject.exceptions.DuplicatedException;
import com.triple.TripleSubject.repositories.PlaceRepository;
import com.triple.TripleSubject.repositories.ReviewRepository;
import com.triple.TripleSubject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Duplicator {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    public void reviewDuplicatedCheck(User user, Place place , String message){
        if(reviewRepository.findByUserIdWithPlaceId(user.getId(),place.getId())!=null)
            throw new DuplicatedException(message);
    }

    public boolean placeDuplicatedCheck(Place place){
        if(placeRepository.findByUuid(place.getUuid())==null)return true;
        return false;
    }

    public boolean userDuplicatedCheck(User user){
        if(userRepository.findByUuid(user.getUuid())==null)return true;
        return false;
    }
}
