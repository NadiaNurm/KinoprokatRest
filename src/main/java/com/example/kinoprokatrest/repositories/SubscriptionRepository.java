package com.example.kinoprokatrest.repositories;

import com.example.kinoprokatrest.models.Film;
import com.example.kinoprokatrest.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
