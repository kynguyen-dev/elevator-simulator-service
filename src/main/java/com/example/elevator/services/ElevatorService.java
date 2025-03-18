package com.example.elevator.services;

import com.example.elevator.constants.ElevatorDirection;
import com.example.elevator.models.Elevator;

/**
 * Service interface for managing elevator operations.
 * This service provides methods to control and monitor the state of elevators,
 * including adding floors to the queue, requesting elevators, opening/closing doors,
 * moving elevators, and resetting elevators to their default state.
 */
public interface ElevatorService {

        /**
         * Retrieves the current state of the specified elevator.
         *
         * @param id The ID of the elevator.
         * @return The current state of the elevator as an {@link Elevator} object.
         * @throws IllegalArgumentException If the elevator ID is invalid.
         */
        Elevator getElevator(int id);

        /**
         * Requests the elevator to go to a specific floor with a given direction.
         * If the elevator is idle, it sets the direction and adds the floor to the queue.
         * If the elevator is already moving, it adds the floor to the queue without changing the direction.
         *
         * @param id        The ID of the elevator.
         * @param floor     The floor number to request.
         * @param direction The direction in which the elevator should move (UP or DOWN).
         * @throws IllegalArgumentException If the floor number is invalid or the elevator ID is invalid.
         */
        void requestElevator(int id, int floor, ElevatorDirection direction);

        /**
         * Opens the door of the specified elevator.
         *
         * @param id The ID of the elevator.
         * @throws IllegalArgumentException If the elevator ID is invalid.
         */
        void openDoor(int id);

        /**
         * Closes the door of the specified elevator.
         *
         * @param id The ID of the elevator.
         * @throws IllegalArgumentException If the elevator ID is invalid.
         */
        void closeDoor(int id);

        /**
         * Moves the elevator to the next floor in its queue.
         * If the elevator reaches a floor in the queue, it stops, opens the door, and removes the floor from the queue.
         * If the elevator does not need to stop at the next floor, it continues moving.
         *
         * @param id The ID of the elevator.
         * @return The updated state of the elevator after moving.
         * @throws IllegalArgumentException If the elevator ID is invalid.
         */
        Elevator moveElevator(int id);

        /**
         * Resets the specified elevator to its default state.
         * The default state includes:
         * - Current floor set to 1.
         * - Direction set to NONE.
         * - Queue cleared.
         * - Door closed.
         *
         * @param id The ID of the elevator.
         * @return The updated state of the elevator after reset.
         * @throws IllegalArgumentException If the elevator ID is invalid.
         */
        Elevator resetElevatorById(int id);

        int getNumberOfElevators();
}
