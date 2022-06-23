package com.triple.TripleSubject.services;

import com.triple.TripleSubject.dtos.UserPointResponse;
import com.triple.TripleSubject.entities.User;
import com.triple.TripleSubject.exceptions.DataNotFoundException;
import com.triple.TripleSubject.repositories.ReviewRepository;
import com.triple.TripleSubject.repositories.UserRepository;
import com.triple.TripleSubject.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    Validator validator;

    public List<UserPointResponse> getUsersPoint(){
        List<User> userList=userRepository.findAll();
        List<UserPointResponse> pointList=new ArrayList<>();

        for(User cur : userList){
            pointList.add(new UserPointResponse(cur.getUuid(),cur.getAchievePoint()));
        }

        return pointList;
    }

    public UserPointResponse getUserPointById(String userId){
        validator.validateUuid(userId,"userId 형식이 맞지 않습니다.");

        User user=userRepository.findByUuid(userId);
        if(user==null)throw new DataNotFoundException("일치하는 User가 없습니다.");
        System.out.println(userId+"==================================");
        return new UserPointResponse(user.getUuid(),user.getAchievePoint());
    }
}
