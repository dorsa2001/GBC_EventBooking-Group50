package ca.gbc.roomservice.service;

import ca.gbc.roomservice.dto.RoomRequest;
import ca.gbc.roomservice.dto.RoomResponse;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    RoomResponse createRoom(RoomRequest roomRequest);
    Optional<RoomResponse> getRoomById(Long id);
    List<RoomResponse> getAllRooms();
    List<RoomResponse> getAvailableRooms();
    RoomResponse updateRoom(Long id, RoomRequest roomRequest);
    void deleteRoom(Long id);
}
