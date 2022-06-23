package com.triple.TripleSubject.repositories;

import com.triple.TripleSubject.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    @Query(value="select e from Event e join User u on e.user = u.id join Place p on e.place = p.id where u.id=:userId and p.id=:placeId")
    List<Event> findByUserId(long userId,long placeId);
}
