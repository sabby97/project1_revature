package com.trms.models;

import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_email",nullable = false, unique = true)
    private String employeeEmail;

    @Column(name = "employee_password",nullable = false)
    private String employeePassword;

    @Column(name = "is_benco")
    private boolean isBenco;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Department department;

    public Employee() {
    }

    public Employee(int employeeId, String employeeName, String employeeEmail, String employeePassword, boolean isBenco, Department department) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeePassword = employeePassword;
        this.isBenco = isBenco;
        this.department = department;
    }

    public Employee(String employeeName, String employeeEmail, String employeePassword, boolean isBenco, Department department) {
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeePassword = employeePassword;
        this.isBenco = isBenco;
        this.department = department;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public boolean isBenco() {
        return isBenco;
    }

    public void setBenco(boolean benco) {
        isBenco = benco;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return getEmployeeId() == employee.getEmployeeId() && isBenco() == employee.isBenco() && Objects.equals(getEmployeeName(), employee.getEmployeeName()) && Objects.equals(getEmployeeEmail(), employee.getEmployeeEmail()) && Objects.equals(getEmployeePassword(), employee.getEmployeePassword()) && Objects.equals(getDepartment(), employee.getDepartment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployeeId(), getEmployeeName(), getEmployeeEmail(), getEmployeePassword(), isBenco(), getDepartment());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", employeePassword='" + employeePassword + '\'' +
                ", isBenco=" + isBenco +
                ", department=" + department +
                '}';
    }
}
