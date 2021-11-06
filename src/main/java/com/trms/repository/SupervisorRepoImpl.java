package com.trms.repository;

import com.trms.models.Supervisor;
import com.trms.util.HibernateUtil;
import com.trms.util.MyLogger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SupervisorRepoImpl implements SupervisorRepo{
    @Override
    public Supervisor addSupervisor(Supervisor s) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx= session.beginTransaction();
            session.persist(s);
            tx.commit();
            MyLogger.logger.info("Adding a Supervisor");
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            if(tx != null) tx.rollback();
            MyLogger.logger.warn("Returning null");
            return null;
        }finally {
            session.close();
        }
        return s;
    }

    @Override
    public List<Supervisor> getAllSupervisors() {
        Session session = HibernateUtil.getSession();
        List<Supervisor> supervisors = null;
        try {
            supervisors = session.createQuery("FROM Supervisor").list();
            if(supervisors.size() <= 0){
                supervisors = null;
            }else{
                MyLogger.logger.info("Getting all supervisors");
            }

        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return supervisors;
    }

    @Override
    public List<Supervisor> getAllSupervisors(int supervisorId) {
        Session session = HibernateUtil.getSession();
        List<Supervisor> supervisors = null;
        try {
            supervisors = session.createQuery("FROM Supervisor AS S WHERE S.supervisorId = :id").setParameter("id", supervisorId).list();
            if(supervisors.size() <= 0){
                supervisors = null;
            }else{
                MyLogger.logger.info("Getting all employees under a supervisor");
            }
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return supervisors;
    }

    @Override
    public Supervisor getMySupervisor(int employeeId) {
        Session session = HibernateUtil.getSession();
        Supervisor s = null;
        try {
            s = session.get(Supervisor.class, employeeId);
            MyLogger.logger.info("Got a Supervisor");
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return s;
    }

    @Override
    public Supervisor updateSupervisor(Supervisor change) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(change);
            tx.commit();
            MyLogger.logger.info("Updating a supervisor");
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
    public Supervisor deleteSupervisor(int supervisorId) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        Supervisor s = null;
        try {
            tx = session.beginTransaction();
            s = session.get(Supervisor.class, supervisorId);
            session.delete(s);
            tx.commit();
            MyLogger.logger.info("Deleting a supervisor");
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            if(tx != null) tx.rollback();
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return s;
    }
}
