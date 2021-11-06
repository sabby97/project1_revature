package com.trms.repository;

import com.trms.models.DepartmentHead;

import java.util.List;

public interface DepartmentHeadRepo {

    public DepartmentHead addDepartmentHead(DepartmentHead d);

    public List<DepartmentHead> getAllDepartmentHeads();

    public DepartmentHead getDepartmentHead(int departmentId);

    public DepartmentHead ifIsDepartmentHead(int employeeId);

    public DepartmentHead updateDepartmentHead(DepartmentHead change);

    public DepartmentHead deleteDepartmentHead(int departmentId);
}
