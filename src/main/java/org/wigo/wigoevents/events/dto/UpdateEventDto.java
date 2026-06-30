package org.wigo.wigoevents.events.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.wigo.wigoevents.events.EventStatus;

import java.time.OffsetDateTime;

@Getter
@Setter
public class UpdateEventDto {
    private String eventName;

    private String eventDescription;

    private String eventLocation;

    private OffsetDateTime eventStartDate;

    private OffsetDateTime eventEndDate;

    private EventStatus status;

    private Integer capacity;
}
