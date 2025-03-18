package com.example.elevator.models;

import com.example.elevator.constants.ElevatorDirection;

import java.util.*;

public class ElevatorQueue {

    // The list of ElevatorQueueItem objects representing the queue of requested floors.
    private final List<ElevatorQueueItem> queue;

    public ElevatorQueue() {
        this.queue = new ArrayList<>();
    }

    public List<ElevatorQueueItem> getQueue() {
        return Collections.unmodifiableList(queue);
    }

    public void setQueue(List<ElevatorQueueItem> queue) {
        this.queue.addAll(queue);
    }

    public void addToQueue(int floor, ElevatorDirection direction) {
        if (!queue.stream().anyMatch(item -> item.getFloor() == floor)) {
            queue.add(new ElevatorQueueItem(floor, direction));
            sortQueue(direction);
        }
    }

    public void removeFirstQueue() {
        if (!queue.isEmpty()) {
            queue.remove(0);
        }
    }

    public void removeQueue(ElevatorQueueItem queueItem) {
        if (!queue.isEmpty()) {
            queue.remove(queueItem);
        }
    }

    public void sortQueue(ElevatorDirection direction) {
        if (direction == ElevatorDirection.UP) {
            queue.sort(Comparator.comparingInt(ElevatorQueueItem::getFloor));
        } else if (direction == ElevatorDirection.DOWN) {
            queue.sort(Comparator.comparingInt(ElevatorQueueItem::getFloor).reversed());
        }
    }

    public void clearQueue() {
        queue.clear();
    }

    @Override
    public String toString() {
        return "ElevatorQueueManager{" +
                "queue=" + queue +
                '}';
    }
}
