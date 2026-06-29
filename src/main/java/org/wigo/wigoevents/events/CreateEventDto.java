package org.wigo.wigoevents.events;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Event Start Date cannot be empty")
    private OffsetDateTime eventStartDate;

    @NotBlank(message = "Event End Date cannot be empty")
    private OffsetDateTime eventEndDate;

    private int capacity;

}
