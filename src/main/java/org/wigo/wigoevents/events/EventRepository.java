package org.wigo.wigoevents.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wigo.wigoevents.user.UserEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {

    // Find by name
    List<EventEntity> findByEventName(String eventName);

    // Find by status
    List<EventEntity> findByStatus(EventStatus status);

    // Find all events created by a given user
    List<EventEntity> findByCreatedBy_UserId(UUID userId);

    // Duplicate guard: same user, same name, same start and end
    boolean existsByEventNameAndCreatedByAndEventStartDateAndEventEndDate(
            String eventName,
            UserEntity createdBy,
            OffsetDateTime eventStartDate,
            OffsetDateTime eventEndDate
    );

    //search by organizer
    //search by singer/ performer
    //search by date
    //search by location
}