package com.trms.repository;

import com.trms.models.Department;

import java.util.List;

public interface DepartmentRepo {

    public Department addDepartment(Department d);

    public List<Department> getAllDepartments();

    public Department getDepartment(int departmentId);

    public Department updateDepartment(Department change);

    public Department deleteDepartment(int departmentId);
}
