package com.trms.RepoTest;
import com.trms.models.Status;
import com.trms.repository.StatusRepo;
import com.trms.repository.StatusRepoImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StatusRepoTest {

    StatusRepo sr = new StatusRepoImpl();

    @Test
    public void getAllStatus(){
        List<Status> test = sr.getAllStatuses();
        System.out.println(test);
        assertNotNull(test);
    }

    @Test
    public void getStatus(){
       Status test = sr.getStatus(1);
        System.out.println(test);
        assertNotNull(test);
        assertEquals(1,test.getStatusId());
    }
}
