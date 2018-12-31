package com.entertainment.filmland.repository;

import com.entertainment.filmland.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberLoginRepository extends JpaRepository<Subscriber, String>{

    Subscriber findByEmail(String email);
}