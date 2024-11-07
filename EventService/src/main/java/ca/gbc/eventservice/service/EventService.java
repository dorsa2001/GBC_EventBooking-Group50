package ca.gbc.eventservice.service;

import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;

import java.util.List;
import java.util.Optional;

public interface EventService {
    EventResponse createEvent(EventRequest eventRequest);
    Optional<EventResponse> getEventById(String id);
    List<EventResponse> getAllEvents();
    List<EventResponse> getEventsByRoomId(String roomId);
    void deleteEvent(String id);
}
