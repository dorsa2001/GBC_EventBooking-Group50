package ca.gbc.roomservice.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomResponse {
    Long id;
    String name;
    int capacity;
    String features;
    boolean available;
}
