package com.trms.RepoTest;

import com.trms.models.Employee;
import com.trms.repository.EmployeeRepo;
import com.trms.repository.EmployeeRepoImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeRepoTest {
    EmployeeRepo er = new EmployeeRepoImpl();

    @Test
    public void getEmployee(){
        Employee e = er.getEmployee(1);
        System.out.println(e);
        assertNotNull(e);
    }

    @Test
    public void getEmployeeEmail(){
        Employee e = er.getEmployee("adele@j.com");
        System.out.println(e);
        assertNotNull(e);
    }

    @Test
    public void getAllEmployee(){
        List<Employee> e = er.getAllEmployees(1);
        System.out.println(e);
        assertNotNull(e);
    }

}
