package com.example.elevator.models;

import com.example.elevator.constants.ElevatorDirection;

public class ElevatorQueueItem {

    // The floor number associated with this queue item.
    private final int floor;

    // The direction (UP or DOWN) associated with this queue item.
    private final ElevatorDirection direction;

    public ElevatorQueueItem(int floor, ElevatorDirection direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public int getFloor() {
        return floor;
    }

    public ElevatorDirection getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "ElevatorQueueItem{" +
                "floor=" + floor +
                ", direction=" + direction +
                '}';
    }

}
