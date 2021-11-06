package com.trms.repository;

import com.trms.models.DepartmentHead;
import com.trms.models.EventType;
import com.trms.util.HibernateUtil;
import com.trms.util.MyLogger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EventTypeRepoImpl implements EventTypeRepo{
    @Override
    public EventType addEventType(EventType e) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx= session.beginTransaction();
            e.setTypeId((int)session.save(e));
            tx.commit();
            MyLogger.logger.info("Adding a EventType");
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            if(tx != null) tx.rollback();
            MyLogger.logger.warn("Returning null");
            return null;
        }finally {
            session.close();
        }
        return e;
    }

    @Override
    public List<EventType> getAllEventTypes() {
        Session session = HibernateUtil.getSession();
        List<EventType> eventTypes = null;
        try {
            eventTypes = session.createQuery("FROM EventType").list();
            if (eventTypes.size() <= 0){
                eventTypes = null;
            }
            else{
                MyLogger.logger.info("Getting all EventTypes");
            }
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return eventTypes;
    }

    @Override
    public EventType getEventType(int TypeId) {
        Session session = HibernateUtil.getSession();
        EventType e = null;
        try {
            e = session.get(EventType.class, TypeId);
            MyLogger.logger.info("Got a EventType");
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return e;
    }

    @Override
    public EventType updateEventType(EventType change) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(change);
            tx.commit();
            MyLogger.logger.info("Updating a EventType");
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
    public EventType deleteEventType(int TypeId) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        EventType e = null;
        try {
            tx = session.beginTransaction();
            e = session.get(EventType.class, TypeId);
            session.delete(e);
            tx.commit();
            MyLogger.logger.info("Deleting a EventType");
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            if(tx != null) tx.rollback();
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return e;
    }
}
