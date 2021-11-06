package com.trms.RepoTest;

import com.trms.models.*;
import com.trms.repository.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventRepoTest {
    EventRepo er = new EventRepoImpl();
    EmployeeRepo employeeRepo = new EmployeeRepoImpl();
    StatusRepo statusRepo = new StatusRepoImpl();
    EventTypeRepo eventTypeRepo = new EventTypeRepoImpl();
    GradeFormatRepo gradeFormatRepo = new GradeFormatRepoImpl();

    @Test
    public void addEvent(){
        Employee employee = employeeRepo.getEmployee(5);
        EventType eventType = eventTypeRepo.getEventType(1);
        GradeFormat gradeFormat = gradeFormatRepo.getGradeFormat(1);
        Status status = statusRepo.getStatus(2);

        Event e = new Event(10,employee,"adele",65465464,"asfd","dfasdfa",500.0,eventType,"asdfasdf",gradeFormat,"",status,status,"",56655);

        er.addEvent(e);
        //assertEquals(1,e.getEventId());
    }

    @Test
    public void getAllEvents(){
        List<Event> test = er.getAllEvents();
        assertNotNull(test);
    }

    @Test
    public void getAllEventsEmployee(){
        List<Event> test = er.getAllEvents(1);
        assertNotNull(test);
    }

    @Test
    public void getAllEventsStatus(){
        List<Event> test = er.getAllEventsByStatus(1);
        assertNotNull(test);
    }

    @Test
    public void getAllEventsByIdAndStatus(){
        List<Event> test = er.getAllEventsByStatus(1,2);
        assertNotNull(test);
    }

    @Test
    public void getEvent(){
        Event test = er.getEvent(1);
        assertNotNull(test);
        assertEquals(1,test.getEventId());
    }

    @Test
    public void updateEvent(){
        Event test = er.getEvent(1);
        test.setAdminMsg("help");
        test = er.updateEvent(test);
        assertNotNull(test);
        assertEquals("help",test.getAdminMsg());
    }

    @Test
    public void deleteEvent(){
        Event test = er.deleteEvent(1);
        assertNotNull(test);
        assertEquals(1,test.getEventId());
    }
}
