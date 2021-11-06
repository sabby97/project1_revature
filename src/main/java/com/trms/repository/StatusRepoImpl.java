package com.trms.repository;

import com.trms.models.GradeFormat;
import com.trms.models.Status;
import com.trms.util.HibernateUtil;
import com.trms.util.MyLogger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StatusRepoImpl implements StatusRepo{

    @Override
    public Status addStatus(Status s) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx= session.beginTransaction();
            s.setStatusId((int)session.save(s));
            tx.commit();
            MyLogger.logger.info("Adding a Status");
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
    public List<Status> getAllStatuses() {
        Session session = HibernateUtil.getSession();
        List<Status>  statuses = null;
        try {
            statuses = session.createQuery("FROM Status").list();
            if (statuses.size() <= 0){
                statuses = null;
            }
            else{
                MyLogger.logger.info("Getting all Statuses");
            }
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return statuses;
    }

    @Override
    public Status getStatus(int statusId) {
        Session session = HibernateUtil.getSession();
        Status s = null;
        try {
            s = session.get(Status.class, statusId);
            MyLogger.logger.info("Got a Status");
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return s;
    }

    @Override
    public Status updateStatus(Status change) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(change);
            tx.commit();
            MyLogger.logger.info("Updating a Status");
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
    public Status deleteStatus(int statusId) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        Status s = null;
        try {
            tx = session.beginTransaction();
            s = session.get(Status.class, statusId);
            session.delete(s);
            tx.commit();
            MyLogger.logger.info("Deleting a Status");
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
