package ca.gbc.roomservice.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomRequest {
    String name;
    int capacity;
    String features;
    boolean available;
}
