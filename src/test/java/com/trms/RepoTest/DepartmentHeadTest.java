package com.trms.RepoTest;

import com.trms.models.DepartmentHead;
import static org.junit.jupiter.api.Assertions.*;
import com.trms.repository.DepartmentHeadRepo;
import com.trms.repository.DepartmentHeadRepoImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DepartmentHeadTest {
    DepartmentHeadRepo dhr = new DepartmentHeadRepoImpl();

    @Test
    void getDepartmentHead(){
        DepartmentHead test = dhr.getDepartmentHead(1);
        assertEquals(1,test.getDepartmentId());
        assertNotNull(test);
    }

    @Test
    void getAllDepartmentHead(){
        List<DepartmentHead> test = dhr.getAllDepartmentHeads();
        assertNotNull(test);
    }

    @Test
    void checkHead(){
        DepartmentHead test = dhr.ifIsDepartmentHead(5);
        System.out.println(test);
        assertEquals(5,test.getHeadId());
        assertNotNull(test);
    }
}
