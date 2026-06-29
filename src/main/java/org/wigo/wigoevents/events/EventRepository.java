package org.wigo.wigoevents.events;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Integer> {
    List<EventEntity> findByEventName(String EventName);
    //search by organizer
    //search by singer/ performer
    //search by date
    //search by location

}
