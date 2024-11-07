package ca.gbc.roomservice.service;

import ca.gbc.roomservice.dto.RoomRequest;
import ca.gbc.roomservice.dto.RoomResponse;
import ca.gbc.roomservice.model.Room;
import ca.gbc.roomservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public RoomResponse createRoom(RoomRequest roomRequest) {
        log.debug("Creating a new room {}", roomRequest.getName());

        Room room = Room.builder()
                .name(roomRequest.getName())
                .capacity(roomRequest.getCapacity())
                .features(roomRequest.getFeatures())
                .available(roomRequest.isAvailable())
                .build();

        room = roomRepository.save(room);
        return mapToRoomResponse(room);
    }

    @Override
    public Optional<RoomResponse> getRoomById(Long id) {
        return roomRepository.findById(id).map(this::mapToRoomResponse);
    }

    @Override
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::mapToRoomResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomResponse> getAvailableRooms() {
        return roomRepository.findAll().stream()
                .filter(Room::isAvailable)
                .map(this::mapToRoomResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponse updateRoom(Long id, RoomRequest roomRequest) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room with ID " + id + " not found"));

        room.setName(roomRequest.getName());
        room.setCapacity(roomRequest.getCapacity());
        room.setFeatures(roomRequest.getFeatures());
        room.setAvailable(roomRequest.isAvailable());

        roomRepository.save(room);
        return mapToRoomResponse(room);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    private RoomResponse mapToRoomResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .capacity(room.getCapacity())
                .features(room.getFeatures())
                .available(room.isAvailable())
                .build();
    }
}
