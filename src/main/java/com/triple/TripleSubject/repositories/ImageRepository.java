package com.triple.TripleSubject.repositories;

import com.triple.TripleSubject.entities.Image;
import com.triple.TripleSubject.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {

    Image findByUuid(String uuid);

    List<Image> findByReview(Review review);

    int countByReviewId(long reviewId);
}
