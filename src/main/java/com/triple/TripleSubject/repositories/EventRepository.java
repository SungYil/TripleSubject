package com.triple.TripleSubject.repositories;

import com.triple.TripleSubject.entities.Event;
import com.triple.TripleSubject.entities.Place;
import com.triple.TripleSubject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findByUserAndPlace(User user, Place place);
}
