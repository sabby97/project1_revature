package com.trms.repository;

import com.trms.models.Employee;

import java.util.List;

public interface EmployeeRepo {

    public Employee addEmployee(Employee e);

    public List<Employee> getAllEmployees();

    public List<Employee> getAllEmployees(int departmentId);

    public Employee getEmployee(int employeeId);

    public Employee getEmployee(String employeeEmail);

    public Employee updateEmployee(Employee change);

    public Employee deleteEmployee(int employeeId);
}
