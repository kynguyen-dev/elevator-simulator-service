package com.example.elevator.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ElevatorManager {

    // A map that stores all elevators in the system, where the key is the elevator ID (Integer)
    // and the value is the corresponding Elevator object.
    private final Map<Integer, Elevator> elevators;

    public ElevatorManager(@Value("${elevator.number-of-elevators}") int numberOfElevators) {
        this.elevators = new HashMap<>();
        for (int elevatorId = 1; elevatorId <= numberOfElevators; elevatorId++) {
            elevators.put(elevatorId, new Elevator(elevatorId));
        }
    }

    public Elevator getElevator(int id) {
        if (!elevators.containsKey(id)) {
            throw new IllegalArgumentException("Invalid elevator ID: " + id);
        }
        return elevators.get(id);
    }

    public Map<Integer, Elevator> getAllElevators() {
        return elevators;
    }
}
