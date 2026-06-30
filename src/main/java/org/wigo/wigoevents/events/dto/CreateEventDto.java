package org.wigo.wigoevents.events.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CreateEventDto {
    @NotBlank(message = "Event Name cannot be empty")
    private String eventName;

    private String eventDescription;

    @NotBlank(message = "Event location cannot be empty")
    private String eventLocation;

    //private String category;
    //private String categoryName;

    @NotNull(message = "Event Start Date cannot be empty")
    private OffsetDateTime eventStartDate;

    @NotNull(message = "Event End Date cannot be empty")
    private OffsetDateTime eventEndDate;

    private int capacity;

}
