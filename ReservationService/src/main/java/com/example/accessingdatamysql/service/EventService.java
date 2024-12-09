package com.example.accessingdatamysql.service;


import com.example.accessingdatamysql.entity.EventEntity;

public interface EventService {
    EventEntity Details(Long id);
    EventEntity Update( EventEntity eventPatched);
}
