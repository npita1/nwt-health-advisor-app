
package com.example.accessingdatamysql.repository;

import java.util.List;

import com.example.accessingdatamysql.entity.EventEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<EventEntity, Long> {

    EventEntity findById(long id);
}

