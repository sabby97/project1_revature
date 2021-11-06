package com.trms.repository;

import com.trms.models.DepartmentHead;
import com.trms.models.Employee;
import com.trms.util.HibernateUtil;
import com.trms.util.MyLogger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DepartmentHeadRepoImpl implements DepartmentHeadRepo{
    @Override
    public DepartmentHead addDepartmentHead(DepartmentHead d) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx= session.beginTransaction();
            session.persist(d);
            tx.commit();
            MyLogger.logger.info("Adding a DepartmentHead");
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            if(tx != null) tx.rollback();
            MyLogger.logger.warn("Returning null");
            return null;
        }finally {
            session.close();
        }
        return d;
    }

    @Override
    public List<DepartmentHead> getAllDepartmentHeads() {
        Session session = HibernateUtil.getSession();
        List<DepartmentHead> departmentHeads = null;
        try {
            departmentHeads = session.createQuery("FROM DepartmentHead").list();

            if (departmentHeads.size() <= 0){
                departmentHeads = null;
            }
            else{
                MyLogger.logger.info("Getting all DepartmentHead");
            }
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return departmentHeads;
    }

    @Override
    public DepartmentHead getDepartmentHead(int departmentId) {
        Session session = HibernateUtil.getSession();
        DepartmentHead d = null;
        try {
            d = session.get(DepartmentHead.class, departmentId);
            MyLogger.logger.info("Got a DepartmentHead");
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return d;
    }

    @Override
    public DepartmentHead ifIsDepartmentHead(int employeeId) {
        Session session = HibernateUtil.getSession();
        DepartmentHead departmentHead = null;

        try{
            String queryString = "FROM DepartmentHead AS D WHERE D.headId = :id";
            List result = session.createQuery(queryString).setParameter("id", employeeId).list();

            if(result.size() > 0){
                Object lol = result.get(0);
                //System.out.println(lol);
                departmentHead= (DepartmentHead) result.get(0);
                //System.out.println(e);
                MyLogger.logger.info("Found the Department Head");
            }
        } catch (HibernateException ex) {
            MyLogger.logger.error(ex.getMessage());
            MyLogger.logger.warn("returning null");
        }finally {
            session.close();
        }
        return departmentHead;
    }

    @Override
    public DepartmentHead updateDepartmentHead(DepartmentHead change) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(change);
            tx.commit();
            MyLogger.logger.info("Updating a DepartmentHead");
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            if(tx != null) tx.rollback();
            MyLogger.logger.warn("Returning null");
            return null;
        }finally {
            session.close();
        }
        return change;
    }

    @Override
    public DepartmentHead deleteDepartmentHead(int departmentId) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        DepartmentHead d = null;
        try {
            tx = session.beginTransaction();
            d = session.get(DepartmentHead.class, departmentId);
            session.delete(d);
            tx.commit();
            MyLogger.logger.info("Deleting a DepartmentHead");
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            if(tx != null) tx.rollback();
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return d;
    }
}
