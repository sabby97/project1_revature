package com.trms.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "supervisors")
public class Supervisor {
    @Id
    @Column(name = "employee_id")
    private int employeeId; //user

    @Column(name = "supervisor_id")
    private int supervisorId;

    public Supervisor() {
    }

    public Supervisor(int employeeId, int supervisorId) {
        this.employeeId = employeeId;
        this.supervisorId = supervisorId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }

    @Override
    public String toString() {
        return "Supervisor{" +
                "employeeId=" + employeeId +
                ", supervisorId=" + supervisorId +
                '}';
    }
}
