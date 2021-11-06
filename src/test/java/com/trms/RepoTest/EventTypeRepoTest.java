package com.trms.RepoTest;

import com.trms.models.EventType;
import com.trms.repository.EventTypeRepo;
import com.trms.repository.EventTypeRepoImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class EventTypeRepoTest {
    EventTypeRepo etr = new EventTypeRepoImpl();

    @Test
    public void getAllEventTypes(){
        List<EventType> test = etr.getAllEventTypes();
        System.out.println(test);
        assertNotNull(test);
    }

    @Test
    public void getEventTypes(){
        EventType test = etr.getEventType(1);
        System.out.println(test);
        assertNotNull(test);
        assertEquals(1,test.getTypeId());
    }

}
