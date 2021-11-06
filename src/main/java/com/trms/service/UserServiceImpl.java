package com.trms.service;

import com.trms.models.Employee;
import com.trms.repository.EmployeeRepo;
import com.trms.repository.EmployeeRepoImpl;
import com.trms.util.MyLogger;

public class UserServiceImpl implements UserService{
    EmployeeRepo employeeRepo;

    public UserServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public Employee login(Employee e) {
        Employee checker = employeeRepo.getEmployee(e.getEmployeeEmail());
        if(checker != null){
            if(checker.getEmployeePassword().equals(e.getEmployeePassword())){
                MyLogger.logger.info("Employee Logged in Success");
                return checker;
            }
        }
        MyLogger.logger.warn("Incorrect login information");
        return null;
    }
}
