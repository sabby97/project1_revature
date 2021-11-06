package com.trms.repository;

import com.trms.models.Department;
import com.trms.util.HibernateUtil;
import com.trms.util.MyLogger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DepartmentRepoImpl implements DepartmentRepo{
    @Override
    public Department addDepartment(Department d) {

        Session session = HibernateUtil.getSession();
        Transaction tx = null;

        try{
            tx = session.getTransaction();
            d.setDepartmentId((int)session.save(d));
            MyLogger.logger.info("Adding a department");
            tx.commit();
        } catch (HibernateException e) {
            MyLogger.logger.error(e.getMessage());
            if(tx != null) {
                tx.rollback();
            }
            MyLogger.logger.warn("returning null");
            return null;
        } finally {
            session.close();
        }
        return d;
    }

    @Override
    public List<Department> getAllDepartments() {

        Session session = HibernateUtil.getSession();
        List<Department> departments = null;

        try{
            departments = session.createQuery("FROM Department").list();
            if (departments.size() <= 0){
                departments = null;
            }
            else{
                MyLogger.logger.info("got all departments");
            }
        }catch(HibernateException e){
            MyLogger.logger.error(e.getMessage());
            MyLogger.logger.warn("returning null");
        }finally {
            session.close();
        }
        return departments;
    }

    @Override
    public Department getDepartment(int departmentId) {
        Session session = HibernateUtil.getSession();
        Department d = null;
        try{
            d = session.get(Department.class, departmentId);
            MyLogger.logger.info("got an department");
        }catch (HibernateException e){
            MyLogger.logger.error(e.getMessage());
            MyLogger.logger.warn("returning null");
        }finally {
            session.close();
        }
        return d;
    }

    @Override
    public Department updateDepartment(Department change) {

        Session session = HibernateUtil.getSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            session.merge(change);
            tx.commit();
        }catch (HibernateException e){
            MyLogger.logger.error(e.getMessage());
            if(tx != null) {
                tx.rollback();
            }
            MyLogger.logger.warn("returning null");
            return null;
        }finally {
            session.close();
        }
        return change;
    }

    @Override
    public Department deleteDepartment(int departmentId) {

        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        Department d = null;

        try{
            tx = session.beginTransaction();
            d = session.get(Department.class, departmentId);
            session.delete(d);
            tx.commit();
            MyLogger.logger.info("Deleted an department");
        }catch (HibernateException e){
            MyLogger.logger.error(e.getMessage());
            if(tx != null) tx.rollback();
            MyLogger.logger.warn("returning null");
            return null;
        }finally {
            session.close();
        }
        return d;
    }
}
