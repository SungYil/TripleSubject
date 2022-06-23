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
    @Query(value="select r from Review r where r.state=0")
    Review existsByUuid(String uuid);

    @Query(value="select r from Review r join Place p on r.place = p.id join User u on r.creator = u.id where p.id=:placeId AND u.id=:userId AND r.state=0")
    Review findByUserIdWithPlaceId(long userId, long placeId);

    List<Review> findByPlaceId(long placeId);
}
