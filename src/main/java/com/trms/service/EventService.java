package com.trms.service;

import com.trms.models.Event;

import java.util.List;

public interface EventService {
    public Event addEvent(Event e);
    public List<Event> getEventForRole(int employeeId);
    public Event deleteEvent(int eventId);
    public Event updateEvent(int userId, Event e);
}
