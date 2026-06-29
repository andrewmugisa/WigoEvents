package org.wigo.wigoevents.events;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

import org.wigo.wigoevents.user.UserEntity;

public class EventService {
    private final EventFactory eventFactory;
    private final UserEntity userEntity;

    public EventService(EventFactory eventFactory, UserEntity userEntity) {
        this.eventFactory = eventFactory;
        this.userEntity = userEntity;
    }

    //crud
    //Create

    public EventEntity create(CreateEventDto input) {
        //check if event already existsservice
        UserEntity currentUser = userEntity;

        EventEntity event = eventFactory.create(
                input.getEventName(),
                input.getEventDescription(),
                input.getEventLocation(),
                input.getEventStartDate(),
                input.getEventEndDate(),
                input.getCapacity()
        );
        event.setCreatedAt(OffsetDateTime.from(Instant.now()));

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
