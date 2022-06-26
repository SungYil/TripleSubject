package com.triple.TripleSubject.repositories;

import com.triple.TripleSubject.entities.Place;
import com.triple.TripleSubject.entities.Review;
import com.triple.TripleSubject.entities.User;
import com.triple.TripleSubject.enums.ReviewState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Review findByUuidAndState(String uuid,ReviewState state);

    List<Review> findByCreatorAndPlaceAndState(User user,Place place, ReviewState state);

    boolean existsByUuidAndState(String uuid,ReviewState state);

    boolean existsByCreatorAndPlaceAndState(User user,Place place,ReviewState state);

}
