package com.example.elevator.controllers;

import com.example.elevator.constants.ElevatorDirection;
import com.example.elevator.models.Elevator;
import com.example.elevator.services.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/elevators")
@CrossOrigin(origins = "$elevator.cors.allowed-origins")
public class ElevatorController {
    @Autowired
    private ElevatorService elevatorService;

    @GetMapping("/{id}")
    public Elevator getElevator(@PathVariable int id) {
        try {
            return elevatorService.getElevator(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{id}/request")
    public void requestElevator(@PathVariable int id, @RequestParam int floor, @RequestParam ElevatorDirection direction) {
        try {
            elevatorService.requestElevator(id, floor, direction);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{id}/open-door")
    public void openDoor(@PathVariable int id) {
        try {
            elevatorService.openDoor(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{id}/close-door")
    public void closeDoor(@PathVariable int id) {
        try {
            elevatorService.closeDoor(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{id}/move")
    public Elevator moveElevator(@PathVariable int id) {
        try {
            return elevatorService.moveElevator(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{id}/reset")
    public Elevator resetElevator(@PathVariable int id) {
        try {
            return elevatorService.resetElevatorById(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/count")
    public int getNumberOfElevators() {
        return elevatorService.getNumberOfElevators();
    }
}
