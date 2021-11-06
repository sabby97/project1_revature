package com.trms.repository;

import com.trms.models.Supervisor;

import java.util.List;

public interface SupervisorRepo {

    public Supervisor addSupervisor(Supervisor s);

    public List<Supervisor> getAllSupervisors();

    public List<Supervisor> getAllSupervisors(int supervisorId);

    public Supervisor getMySupervisor(int employeeId);

    public Supervisor updateSupervisor(Supervisor change);

    public Supervisor deleteSupervisor(int supervisorId);
}
