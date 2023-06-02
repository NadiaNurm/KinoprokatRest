package com.example.kinoprokatrest.servises;

import com.example.kinoprokatrest.models.Subscription;
import com.example.kinoprokatrest.repositories.SubscriptionRepository;
import com.example.kinoprokatrest.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class SubscriptionService {
    /*
       Сервис связывает сущность Subscription и репозиторий SubscriptionRepository.
      */
    @Autowired
    SubscriptionRepository subscriptionRepository;

    public Long addSubscription(Subscription subscription) {
        subscription.setDate(LocalDate.now());
        subscription.setEndDate(Util.subscriptionEnd(subscription.getSubTypes()));
        return subscriptionRepository.save(subscription).getId();
    }

    public Subscription findSubByID(Long id) {
        // Функция, которая возвращает подписку по id
        return subscriptionRepository.findById(id).get();
    }
}
