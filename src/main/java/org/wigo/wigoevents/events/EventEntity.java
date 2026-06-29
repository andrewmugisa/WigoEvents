package org.wigo.wigoevents.events;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.wigo.wigoevents.user.UserEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter

@Entity
@Table(name = "events")
public abstract class EventEntity {
    public enum EventStatus {
        DRAFT,
        PUBLISHED,
        CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name= "event_id", updatable = false, nullable = false)
    private UUID eventId;

    @Column(name= "event_name",nullable = false)
    private String eventName;

    @Column(name= "event_description")
    private String eventDescription;

    @Column(name= "event_location",nullable = false)
    private String eventLocation;

    @Column(name = "event_start_date", nullable = false)
    private OffsetDateTime eventStartDate;

    @Column(name = "event_end_date", nullable = false)
    private OffsetDateTime eventEndDate;

    @CreationTimestamp
    @Column(name= "created_at",updatable = false, nullable = false)
    private OffsetDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status", nullable = false)
    private EventStatus eventStatus = EventStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

    @Column(name = "capacity")
    private int capacity;


    public EventEntity(String eventName, String eventDescription, String eventLocation, OffsetDateTime eventStartDate, OffsetDateTime eventEndDate, int capacity) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.capacity = capacity;
    }

    public EventEntity() {}


    public void publish(){
        //publish event -- change status
    }

    public void cancel() {
        //Cancel event
    }

    public void getSearchIndex(){
        //search
    }
}
