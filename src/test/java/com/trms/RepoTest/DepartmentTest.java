package com.trms.RepoTest;

import com.trms.models.Department;
import com.trms.repository.DepartmentRepo;
import com.trms.repository.DepartmentRepoImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentTest {
    DepartmentRepo dr = new DepartmentRepoImpl();

    @Test
    public void getDepartment(){
        Department test = dr.getDepartment(1);
        System.out.println(test);
        assertNotNull(test);
    }

    @Test
    public void getAllDepartment(){
        List<Department> test = dr.getAllDepartments();
        System.out.println(test);
        assertNotNull(test);
    }
}
