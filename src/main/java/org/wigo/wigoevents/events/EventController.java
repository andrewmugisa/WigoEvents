package org.wigo.wigoevents.events;

import com.sun.jdi.request.EventRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wigo.auth.response.ApiResponse;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEvent(@Valid @RequestBody CreateEventDto createEventDto) {
        eventService.create(createEventDto);
        return ResponseEntity.ok().body(new ApiResponse("Event created successfully"));
    }
}
