package org.wigo.wigoevents.events;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.wigo.wigoevents.user.UserEntity;

public class EventService {
    private final EventFactory eventFactory;
    private final UserEntity userEntity;
    private final EventRepository eventRepository;

    public EventService(EventFactory eventFactory, UserEntity userEntity, EventRepository eventRepository) {
        this.eventFactory = eventFactory;
        this.userEntity = userEntity;
        this.eventRepository = eventRepository;
    }

    //crud
    //Create
    public EventEntity create(CreateEventDto input) {
        //check if event already exists
        UserEntity currentUser = (UserEntity) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        EventEntity event = eventFactory.create(
                input.getEventName(),
                input.getEventDescription(),
                input.getEventLocation(),
                input.getEventStartDate(),
                input.getEventEndDate(),
                input.getCapacity()
        );
        event.setCreatedAt(OffsetDateTime.from(Instant.now()));
        event.setCreatedBy(currentUser);

        EventEntity saved = (EventEntity) eventRepository.save(event);
        return null;
    }

    //Read
    public List<EventEntity> findByEventName(String EventName){
        return null;
    }

    //Update
    public List<EventEntity> findByEventNameAndEventType(String EventName,String EventType){
        return null;
    }


    //Delete
    public List<EventEntity> findByEventNameAndEventType(String EventName){
        return null;
    }
}
