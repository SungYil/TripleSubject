package com.triple.TripleSubject.repositories;

import com.triple.TripleSubject.entities.Place;
import com.triple.TripleSubject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUuid(String uuid);

    boolean existsByUuid(String uuid);
}
