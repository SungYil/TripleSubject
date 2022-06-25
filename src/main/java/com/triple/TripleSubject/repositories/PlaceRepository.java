package com.triple.TripleSubject.repositories;

import com.triple.TripleSubject.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {
    Place findByUuid(String uuid);

    boolean existsByUuid(String uuid);
}
