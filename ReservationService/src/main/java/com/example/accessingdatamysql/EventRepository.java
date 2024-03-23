
package com.example.accessingdatamysql;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<EventEntity, Long> {

    EventEntity findById(long id);
}

