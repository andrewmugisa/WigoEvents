package org.wigo.wigoevents.events;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.wigo.wigoevents.events.dto.CreateEventDto;
import org.wigo.wigoevents.events.dto.UpdateEventDto;
import org.wigo.wigoevents.user.UserEntity;

@Service
public class EventService {
    private final EventFactory eventFactory;
    private final EventRepository eventRepository;

    private UserEntity currentUser() {
        return (UserEntity) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    }

    public EventService(EventFactory eventFactory, EventRepository eventRepository) {
        this.eventFactory = eventFactory;
        this.eventRepository = eventRepository;
    }

    //crud
    //Create
    public EventEntity create(CreateEventDto input) {
        UserEntity currentUser = currentUser();

        //check if event already exists
        if (eventRepository.existsByEventNameAndCreatedByAndEventStartDateAndEventEndDate(
                input.getEventName(),
                currentUser,
                input.getEventStartDate(),
                input.getEventEndDate())) {
            throw new IllegalArgumentException("You already have this event scheduled for these dates");
        }

        //else create event
        EventEntity event = eventFactory.create(
                input.getEventName(),
                input.getEventDescription(),
                input.getEventLocation(),
                input.getEventStartDate(),
                input.getEventEndDate(),
                input.getCapacity()
        );

        event.setCreatedBy(currentUser);

        return eventRepository.save(event);
    }

    //Read
    public List<EventEntity> findEvents(UUID userId) {
        return eventRepository.findByCreatedBy_UserId(userId);
    }

    //Update
    public EventEntity update(UUID eventId, UpdateEventDto input) {
        UserEntity currentUser = currentUser();

        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        // ownership check — only the creator can edit
        if (!event.getCreatedBy().getUserId().equals(currentUser.getUserId())) {
            throw new IllegalStateException("You can only edit your own events");
        }

        if (input.getEventName() != null)        event.setEventName(input.getEventName());
        if (input.getEventDescription() != null) event.setEventDescription(input.getEventDescription());
        if (input.getEventLocation() != null)    event.setEventLocation(input.getEventLocation());
        if (input.getEventStartDate() != null)   event.setEventStartDate(input.getEventStartDate());
        if (input.getEventEndDate() != null)     event.setEventEndDate(input.getEventEndDate());
        if (input.getCapacity() != null)         event.setCapacity(input.getCapacity());

        return eventRepository.save(event);
    }


    //Delete
    //soft delete
    public void softDelete(UUID eventId) {
        UserEntity currentUser = currentUser();

        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (!event.getCreatedBy().getUserId().equals(currentUser.getUserId())) {
            throw new IllegalStateException("You can only delete your own events");
        }

        event.setDeleted(true);
        eventRepository.save(event);
    }


}
