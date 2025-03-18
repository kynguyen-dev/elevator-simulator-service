package com.example.elevator.models;

import com.example.elevator.constants.AppConstants;
import com.example.elevator.constants.ElevatorDirection;

import java.util.List;

public class Elevator {
    // The unique identifier for the elevator.
    private int id;

    // The current floor where the elevator is located.
    private int currentFloor;

    // The current direction of the elevator (UP, DOWN, or NONE).
    private ElevatorDirection direction;

    // Indicates whether the elevator door is open (true) or closed (false)
    private boolean isDoorOpen;

    // The queue of requested floors for the elevator.
    private final ElevatorQueue elevatorQueue;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 1;
        this.direction = ElevatorDirection.NONE;
        this.isDoorOpen = false;
        this.elevatorQueue = new ElevatorQueue();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int floor) {
        validateFloor(floor);
        this.currentFloor = floor;
    }

    public ElevatorDirection getDirection() {
        return direction;
    }

    public void setDirection(ElevatorDirection direction) {
        this.direction = direction;
        sortQueue();
    }

    public boolean isDoorOpen() {
        return isDoorOpen;
    }

    public void setDoorOpen(boolean isDoorOpen) {
        this.isDoorOpen = isDoorOpen;
    }

    public List<ElevatorQueueItem> getQueue() {
        return elevatorQueue.getQueue();
    }

    public void setQueue(List<ElevatorQueueItem> queue) {
        elevatorQueue.setQueue(queue);
    }

    public void removeQueue(ElevatorQueueItem queueItem) {
        elevatorQueue.removeQueue(queueItem);
    }

    public void clearQueue() {
        elevatorQueue.clearQueue();
    }

    public void addToQueue(int floor, ElevatorDirection direction) {
        validateFloor(floor);
        elevatorQueue.addToQueue(floor, direction);
    }

    public void removeFirstQueue() {
        elevatorQueue.removeFirstQueue();
    }

    public void sortQueue() {
        elevatorQueue.sortQueue(this.direction);
    }

    /**
     * Validates that the floor number is within the valid range (1 to 10).
     *
     * @param floor The floor number to validate.
     * @throws IllegalArgumentException If the floor number is invalid.
     */
    private void validateFloor(int floor) {
        if (floor < AppConstants.MIN_FLOOR_NUMBER || floor > AppConstants.MAX_FLOOR_NUMBER) {
            throw new IllegalArgumentException("Floor must be between 1 and 10.");
        }
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "currentFloor=" + currentFloor +
                "direction=" + direction +
                "isDoorOpen=" + isDoorOpen +
                ", queueManager=" + elevatorQueue +
                '}';
    }
}
