package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.AppointmentEntity;
import com.example.accessingdatamysql.entity.EventEntity;
import com.example.accessingdatamysql.exceptions.AppointmentNotFoundException;
import com.example.accessingdatamysql.exceptions.EventNotFoundException;
import com.example.accessingdatamysql.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventServiceImpl implements  EventService{
    @Autowired
    private EventRepository eventRepository;
    @Override
    public EventEntity Details(Long id) {
        Optional<EventEntity> event = eventRepository.findById(id);

        if (event.isPresent())
            return event.get();

        throw new EventNotFoundException("Not found Event by id  " + id);
    }

    @Override
    public EventEntity Update(EventEntity eventPatched) {
        return eventRepository.save(eventPatched);
    }
}
