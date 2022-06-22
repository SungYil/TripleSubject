package com.triple.TripleSubject.repositories;

import com.triple.TripleSubject.entities.Image;
import com.triple.TripleSubject.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    @Query(value="select i from Image i where i.uuid=:uuid")
    Image findByUuid(String uuid);
}
