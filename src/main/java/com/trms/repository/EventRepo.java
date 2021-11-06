package com.trms.repository;

import com.trms.models.Event;

import java.util.List;

public interface EventRepo {

    public Event addEvent(Event e);

    public List<Event> getAllEvents();

    public List<Event> getAllEvents(int employeeId);

    public List<Event> getAllEventsByStatus(int status);

    public List<Event> getAllEventsByStatus(int employeeId, int status);

    public Event getEvent(int eventId);

    public Event updateEvent(Event change);

    public Event deleteEvent(int eventId);
}
