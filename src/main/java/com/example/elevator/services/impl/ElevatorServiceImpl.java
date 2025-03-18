package com.example.elevator.services.impl;

import com.example.elevator.constants.ElevatorDirection;
import com.example.elevator.models.Elevator;
import com.example.elevator.models.ElevatorManager;
import com.example.elevator.models.ElevatorQueueItem;
import com.example.elevator.services.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the ElevatorService that manages the state and movement of an elevator.
 * It handles elevator requests, movement logic, and door operations.
 */
@Service
public class ElevatorServiceImpl implements ElevatorService {
    @Autowired
    private ElevatorManager elevatorManager;

    /**
     * Retrieves the current state of the elevator.
     *
     * @param id The ID of the elevator.
     * @return The current state of the elevator as an {@link Elevator} object.
     */
    @Override
    public Elevator getElevator(int id) {
        return elevatorManager.getElevator(id);
    }

    /**
     * Requests the elevator to go to a specific floor with a given direction.
     * If the elevator is idle, it sets the direction and adds the floor to the queue.
     * If the elevator is already moving, it adds the floor to the queue without changing the direction.
     *
     * @param id        The ID of the elevator.
     * @param floor     The floor number to request.
     * @param direction The direction in which the elevator should move (UP or DOWN).
     */
    @Override
    public void requestElevator(int id, int floor, ElevatorDirection direction) {
        Elevator elevator = elevatorManager.getElevator(id);
        if (elevator.getDirection() == ElevatorDirection.NONE) {
            elevator.setDirection(getDirection(floor, elevator.getCurrentFloor()));
            elevator.clearQueue();
            elevator.addToQueue(floor, direction);
            elevator.setDoorOpen(false);
        } else {
            elevator.addToQueue(floor, direction);
        }
    }

    /**
     * Determines the direction based on the target floor and current floor.
     *
     * @param targetFloor  The target floor.
     * @param currentFloor The current floor of the elevator.
     * @return The direction (UP, DOWN, or NONE).
     */
    private ElevatorDirection getDirection(int targetFloor, int currentFloor) {
        if (targetFloor == currentFloor) return ElevatorDirection.NONE;
        return targetFloor > currentFloor ? ElevatorDirection.UP : ElevatorDirection.DOWN;
    }

    /**
     * Opens the elevator door.
     *
     * @param id The ID of the elevator.
     */
    @Override
    public void openDoor(int id) {
        Elevator elevator = elevatorManager.getElevator(id);
        elevator.setDoorOpen(true);
    }

    /**
     * Closes the elevator door.
     *
     * @param id The ID of the elevator.
     */
    @Override
    public void closeDoor(int id) {
        Elevator elevator = elevatorManager.getElevator(id);
        elevator.setDoorOpen(false);
    }

    /**
     * Moves the elevator to the next floor in the queue.
     * If the elevator reaches a floor in the queue, it stops, opens the door, and removes the floor from the queue.
     * If the elevator does not need to stop at the next floor, it continues moving.
     *
     * @param id The ID of the elevator.
     * @return The updated state of the elevator after moving.
     */
    @Override
    public Elevator moveElevator(int id) {
        Elevator elevator = elevatorManager.getElevator(id);
        if (elevator.getQueue().isEmpty() || elevator.isDoorOpen()) {
            return elevator;
        }

        int nextFloor = getNextFloor(elevator);
        if (shouldStopAtFloor(elevator, nextFloor)) {
            elevator.setCurrentFloor(nextFloor);
            elevator.setDoorOpen(true);
            elevator.removeQueue(elevator.getQueue().stream()
                    .filter(item -> item.getFloor() == nextFloor)
                    .findFirst()
                    .get());
            updateDirection(elevator);
        } else {
            elevator.setCurrentFloor(nextFloor);
        }

        return elevator;
    }

    /**
     * Resets the elevator to its default state.
     *
     * @param id The ID of the elevator.
     * @return The updated state of the elevator after reset.
     */
    @Override
    public Elevator resetElevatorById(int id) {
        Elevator elevator = elevatorManager.getElevator(id);
        elevator.setCurrentFloor(1);
        elevator.setDirection(ElevatorDirection.NONE);
        elevator.clearQueue();
        elevator.setDoorOpen(false);
        return elevator;
    }

    @Override
    public int getNumberOfElevators() {
        return elevatorManager.getAllElevators().size();
    }

    /**
     * Calculates the next floor based on the elevator's current direction.
     *
     * @param elevator The elevator object.
     * @return The next floor number.
     */
    private int getNextFloor(Elevator elevator) {
        if (elevator.getQueue().isEmpty()) {
            return elevator.getCurrentFloor();
        }

        ElevatorQueueItem nextItem = elevator.getQueue().get(0);
        if (nextItem.getFloor() == elevator.getCurrentFloor()) {
            return elevator.getCurrentFloor();
        }

        ElevatorDirection direction = nextItem.getFloor() > elevator.getCurrentFloor() ? ElevatorDirection.UP : ElevatorDirection.DOWN;
        return direction == ElevatorDirection.UP ?
                elevator.getCurrentFloor() + 1 :
                elevator.getCurrentFloor() - 1;
    }

    /**
     * Checks if the elevator should stop at the specified floor based on the current direction and queue.
     *
     * @param elevator  The elevator object.
     * @param nextFloor The floor number to check.
     * @return {@code true} if the elevator should stop at the floor, {@code false} otherwise.
     */
    private boolean shouldStopAtFloor(Elevator elevator, int nextFloor) {
        return elevator.getQueue().stream()
                .anyMatch(queueItem -> queueItem.getFloor() == nextFloor && shouldStopBasedOnDirection(elevator, nextFloor));
    }

    /**
     * Checks if the elevator should stop at the specified floor based on the current direction.
     *
     * @param elevator  The elevator object.
     * @param nextFloor The floor number to check.
     * @return {@code true} if the elevator should stop based on direction, {@code false} otherwise.
     */
    private boolean shouldStopBasedOnDirection(Elevator elevator, int nextFloor) {
        ElevatorDirection direction = elevator.getDirection();
        ElevatorDirection tempDirection = elevator.getQueue().stream()
                .filter(item -> item.getFloor() == nextFloor)
                .findFirst()
                .get()
                .getDirection();

        return direction.equals(tempDirection);
    }

    /**
     * Updates the elevator's direction after reaching a floor.
     * If the queue is empty, the direction is set to NONE.
     * Otherwise, the direction is set based on the next target floor in the queue.
     *
     * @param elevator The elevator object.
     */
    private void updateDirection(Elevator elevator) {
        if (elevator.getQueue().isEmpty()) {
            elevator.setDirection(ElevatorDirection.NONE);
        } else {
            int nextTarget = elevator.getQueue().get(0).getFloor();
            elevator.setDirection(nextTarget > elevator.getCurrentFloor() ? ElevatorDirection.UP : ElevatorDirection.DOWN);
        }
    }
}
