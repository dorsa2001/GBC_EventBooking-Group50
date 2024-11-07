package ca.gbc.eventservice.service;

import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;
import ca.gbc.eventservice.model.Event;
import ca.gbc.eventservice.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public EventResponse createEvent(EventRequest eventRequest) {
        log.debug("Creating a new event {}", eventRequest.getEventName());

        Event event = Event.builder()
                .eventName(eventRequest.getEventName())
                .organizerId(eventRequest.getOrganizerId())
                .eventType(eventRequest.getEventType())
                .expectedAttendees(eventRequest.getExpectedAttendees())
                .roomId(eventRequest.getRoomId())
                .date(eventRequest.getDate())
                .startTime(eventRequest.getStartTime())
                .endTime(eventRequest.getEndTime())
                .build();

        event = eventRepository.save(event);
        return mapToEventResponse(event);
    }

    @Override
    public Optional<EventResponse> getEventById(String id) {
        return eventRepository.findById(id).map(this::mapToEventResponse);
    }

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::mapToEventResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventResponse> getEventsByRoomId(String roomId) {
        return eventRepository.findAll().stream()
                .filter(event -> event.getRoomId().equals(roomId))
                .map(this::mapToEventResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }

    private EventResponse mapToEventResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .eventName(event.getEventName())
                .organizerId(event.getOrganizerId())
                .eventType(event.getEventType())
                .expectedAttendees(event.getExpectedAttendees())
                .roomId(event.getRoomId())
                .date(event.getDate())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .build();
    }
}
