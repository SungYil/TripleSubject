package com.triple.TripleSubject.repositories;

import com.triple.TripleSubject.entities.Place;
import com.triple.TripleSubject.entities.Review;
import com.triple.TripleSubject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Review findByUuid(String uuid);

    @Query(value="select r from Review r,Place p,User u where p.id=:placeId AND u.id=:userId")
    Review findByUserIdWithPlaceId(long userId, long placeId);

    List<Review> findByPlaceId(long placeId);
}
