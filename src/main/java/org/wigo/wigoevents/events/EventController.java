package org.wigo.wigoevents.events;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.wigo.auth.response.ApiResponse;
import org.wigo.wigoevents.events.dto.CreateEventDto;
import org.wigo.wigoevents.events.dto.UpdateEventDto;
import org.wigo.wigoevents.user.UserEntity;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    private UserEntity currentUser() {
        return (UserEntity) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    }

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEvent(@Valid @RequestBody CreateEventDto createEventDto) {
        eventService.create(createEventDto);
        return ResponseEntity.ok().body(new ApiResponse("Event created successfully"));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<ApiResponse> updateEvent(
            @PathVariable UUID eventId,
            @RequestBody UpdateEventDto updateEventDto) {
        eventService.update(eventId, updateEventDto);
        return ResponseEntity.ok(new ApiResponse("Event updated successfully"));
    }

    //all user events
    @GetMapping("/")
    public ResponseEntity<List<EventEntity>> getMyEvents() {
        UserEntity currentUser = currentUser();
        return ResponseEntity.ok(eventService.findEvents(currentUser.getUserId()));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponse> deleteEvent(@PathVariable UUID eventId) {
        eventService.softDelete(eventId);
        return ResponseEntity.ok(new ApiResponse("Event deleted successfully"));
    }
}
