package ca.gbc.eventservice.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EventRequest {
    String eventName;
    String organizerId;
    String eventType;
    int expectedAttendees;
    String roomId;
    String date;
    String startTime;
    String endTime;
}
