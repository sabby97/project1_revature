package com.trms.RepoTest;

import com.trms.models.Supervisor;
import com.trms.repository.SupervisorRepo;
import com.trms.repository.SupervisorRepoImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class SupervisorRepoTest {
    SupervisorRepo sr = new SupervisorRepoImpl();

    @Test
    public void getMyUnder(){
        List<Supervisor> test = sr.getAllSupervisors(4);
        System.out.println(test);
        assertNotNull(test);
    }

    @Test
    public void getAllSupervisor(){
        List<Supervisor> test = sr.getAllSupervisors();
        System.out.println(test);
        assertNotNull(test);
    }

    @Test
    public void getMySupervisor(){
        Supervisor test = sr.getMySupervisor(1);
        System.out.println(test);
        assertNotNull(test);
    }

}
