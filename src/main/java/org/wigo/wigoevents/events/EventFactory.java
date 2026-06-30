
package org.wigo.wigoevents.events;

import java.time.OffsetDateTime;

public interface EventFactory {
    EventEntity create(String eventName, String eventDescription, String eventLocation,
                       OffsetDateTime eventStartDate, OffsetDateTime eventEndDate, int capacity);
}