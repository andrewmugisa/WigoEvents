package org.wigo.wigoevents.events;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {
    List<EventEntity> findByEventName(String EventName);
    List<EventEntity> findAllByStatus(EventStatus status);
    //search by organizer
    //search by singer/ performer
    //search by date
    //search by location
    EventEntity findByEventNameAndStatus(String EventName, EventStatus status);

    //@Override
   // EventEntity save(EventEntity event);
}
