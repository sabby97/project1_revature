package com.trms.repository;

import com.trms.models.EventType;

import java.util.List;


public interface EventTypeRepo {

    public EventType addEventType(EventType e);

    public List<EventType> getAllEventTypes();

    public EventType getEventType(int TypeId);

    public EventType updateEventType(EventType change);

    public EventType deleteEventType(int TypeId);
}
