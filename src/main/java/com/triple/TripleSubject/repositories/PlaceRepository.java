package com.triple.TripleSubject.repositories;

import com.triple.TripleSubject.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {
    @Query(value="select p from Place p where p.uuid=:uuid")
    Place findByUuid(String uuid);
}
