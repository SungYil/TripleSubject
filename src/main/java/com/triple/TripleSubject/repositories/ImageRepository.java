package com.triple.TripleSubject.repositories;

import com.triple.TripleSubject.entities.Image;
import com.triple.TripleSubject.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    @Query(value="select i from Image i where i.uuid=:uuid")
    Image findByUuid(String uuid);
    @Query(value="select i from Image i join Review r on i.review=r.id where r.id=:reviewId")
    List<Image> findByReviewId(long reviewId);
}
