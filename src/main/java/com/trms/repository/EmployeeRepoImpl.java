package com.trms.repository;

import com.trms.models.Employee;
import com.trms.util.HibernateUtil;
import com.trms.util.MyLogger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.Query;
import java.util.List;

public class EmployeeRepoImpl implements EmployeeRepo{
    @Override
    public Employee addEmployee(Employee e) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            e.setEmployeeId((int)session.save(e));
            tx.commit();
            MyLogger.logger.info("Added a Employee");
        } catch (HibernateException ex) {
            MyLogger.logger.error(ex.getMessage());
            if(tx != null) tx.rollback();
            MyLogger.logger.warn("returning null");
            return null;
        }finally {
            session.close();
        }
        return e;
    }

    @Override
    public List<Employee> getAllEmployees() {
        Session session = HibernateUtil.getSession();
        List<Employee> employees = null;
        try{
            employees = session.createQuery("From Employee").list();
            if (employees.size() <= 0){
                employees = null;
            }
            else{
                MyLogger.logger.info("Got all employees");
            }
        } catch (HibernateException ex) {
            MyLogger.logger.error(ex.getMessage());
            MyLogger.logger.warn("returning null");
        }finally {
            session.close();
        }
        return employees;
    }

    @Override
    public List<Employee> getAllEmployees(int departmentId) {
        Session session = HibernateUtil.getSession();
        List<Employee> employees = null;
        try{
            //SELECT NEW Employee(E.employeeID,E.employeeName, E.employeeEmail, E.employeePassword,E.isBenco,E.department.departmentId,E.department.departmentName)
            String queryString = "FROM Employee AS E WHERE E.department.departmentId = :id";
            employees = session.createQuery(queryString).setParameter("id", departmentId).list();
            if (employees.size() <= 0){
                employees = null;
            }
            else{
                MyLogger.logger.info("Got all employees from a department");
            }
        } catch (HibernateException ex) {
            MyLogger.logger.error(ex.getMessage());
            MyLogger.logger.warn("returning null");
        }finally {
            session.close();
        }
        return employees;
    }

    @Override
    public Employee getEmployee(int employeeId) {
        Session session = HibernateUtil.getSession();
        Employee e = null;
        try{
            e = session.get(Employee.class, employeeId);
            MyLogger.logger.info("Got the Employee");
        } catch (HibernateException ex) {
            MyLogger.logger.error(ex.getMessage());
            MyLogger.logger.warn("returning null");
        }finally {
            session.close();
        }
        return e;
    }

    @Override
    public Employee getEmployee(String employeeEmail) {
        Session session = HibernateUtil.getSession();
        Employee e = null;
        try{
            //SELECT NEW Employee(E.employeeID,E.employeeName, E.employeeEmail, E.employeePassword,E.isBenco,E.department.departmentId,E.department.departmentName)
            String queryString = "FROM Employee AS E WHERE E.employeeEmail = :email";
            List result = session.createQuery(queryString).setParameter("email", employeeEmail).list();

            if(result.size() > 0){
                Object lol = result.get(0);
                //System.out.println(lol);
                e = (Employee) result.get(0);
                //System.out.println(e);
                MyLogger.logger.info("Found the employee");
            }
        } catch (HibernateException ex) {
            MyLogger.logger.error(ex.getMessage());
            MyLogger.logger.warn("returning null");
        }finally {
            session.close();
        }
        return e;
    }

    @Override
    public Employee updateEmployee(Employee change) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.merge(change);
            tx.commit();
            MyLogger.logger.info("Updated the employee");
        } catch (HibernateException ex) {
            MyLogger.logger.error(ex.getMessage());
            if(tx != null) tx.rollback();
            MyLogger.logger.warn("returning null");
            return null;
        }finally {
            session.close();
        }
        return change;
    }

    @Override
    public Employee deleteEmployee(int employeeId) {
        Session session = HibernateUtil.getSession();
        Transaction tx =null;
        Employee e = null;
        try{
            tx = session.beginTransaction();
            e = session.get(Employee.class, employeeId);
            session.delete(e);
            tx.commit();
            MyLogger.logger.info("Deleted the employee");
        } catch (HibernateException ex) {
            MyLogger.logger.error(ex.getMessage());
            if(tx != null) tx.rollback();
            MyLogger.logger.warn("returning null");
        }finally {
            session.close();
        }
        return e;
    }
}
