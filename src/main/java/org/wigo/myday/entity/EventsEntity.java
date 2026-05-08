package org.wigo.myday.entity;

import jakarta.persistence.*;

import java.time.Instant;
@Entity
@Table(name = "events")
public class EventsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;
    private String title;
    private String description;
    private String location;
    private String eventType;
    private Instant startTime;
    private Instant endTime;
    private Instant creationTime;
    private Integer userId;

    public EventsEntity(Integer userId, Instant creationTime, Instant endTime, Instant startTime, String eventType, String location, String description, Integer eventId, String title) {
        this.userId = userId;
        this.creationTime = creationTime;
        this.endTime = endTime;
        this.startTime = startTime;
        this.eventType = eventType;
        this.location = location;
        this.description = description;
        this.eventId = eventId;
        this.title = title;
    }

    public EventsEntity() {
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "EventsEntity{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", eventType='" + eventType + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", creationTime=" + creationTime +
                ", userId=" + userId +
                '}';
    }
}
