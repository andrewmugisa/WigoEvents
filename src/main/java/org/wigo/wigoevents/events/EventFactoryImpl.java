// EventFactoryImpl.java  (NEW)
package org.wigo.wigoevents.events;

import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;

@Component
public class EventFactoryImpl implements EventFactory {
    @Override
    public EventEntity create(String eventName, String eventDescription, String eventLocation,
                              OffsetDateTime eventStartDate, OffsetDateTime eventEndDate, int capacity) {
        // requires EventEntity to be non-abstract (or pick a concrete subtype)
        return new EventEntity(eventName, eventDescription, eventLocation,
                eventStartDate, eventEndDate, capacity);
    }
}