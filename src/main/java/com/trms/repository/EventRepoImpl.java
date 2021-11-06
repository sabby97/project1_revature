package com.trms.repository;

import com.trms.models.Employee;
import com.trms.models.Event;
import com.trms.models.Status;
import com.trms.util.HibernateUtil;
import com.trms.util.MyLogger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EventRepoImpl implements EventRepo{

    @Override
    public Event addEvent(Event e) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx= session.beginTransaction();
            e.setEventId((int)session.save(e));
            tx.commit();
            MyLogger.logger.info("Adding a Event");
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
    public List<Event> getAllEvents() {
        Session session = HibernateUtil.getSession();
        List<Event>  events = null;
        try {
            events = session.createQuery("FROM Event").list();
            if (events.size() <= 0){
                events = null;
            }
            else{
                MyLogger.logger.info("Getting all Events");
            }

        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return events;
    }

    @Override
    public List<Event> getAllEvents(int employeeId) {
        Session session = HibernateUtil.getSession();
        List<Event>  events = null;
        try {
            events = session.createQuery("FROM Event As E WHERE E.employeeId.employeeId = :id").setParameter("id",employeeId).list();
            if (events.size() <= 0){
                events = null;
            }
            else{
                MyLogger.logger.info("Getting all Events for this Employee");
            }
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return events;
    }

    @Override
    public List<Event> getAllEventsByStatus(int status) {
        Session session = HibernateUtil.getSession();
        List<Event>  events = null;
        try {
            events = session.createQuery("FROM Event As E WHERE E.eventCurrStatus.statusId = :id").setParameter("id",status).list();
            if (events.size() <= 0){
                events = null;
            }
            else{
                MyLogger.logger.info("Getting all Events by this status");
            }
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return events;
    }

    @Override
    public List<Event> getAllEventsByStatus(int employeeId, int status) {
        Session session = HibernateUtil.getSession();
        List<Event>  events = null;
        try {
            String queryString = "FROM Event As E WHERE E.employeeId.employeeId = :employeeId AND E.eventCurrStatus.statusId = :status";
            events = session.createQuery(queryString).setParameter("employeeId",employeeId).setParameter("status",status).list();
            if (events.size() <= 0){
                events = null;
            }
            else{
                MyLogger.logger.info("Getting all Events by employeeId and status");
            }
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return events;
    }

    @Override
    public Event getEvent(int eventId) {
        Session session = HibernateUtil.getSession();
        Event e = null;
        try{
            e = session.get(Event.class, eventId);
            MyLogger.logger.info("Got the Event");
        } catch (HibernateException ex) {
            MyLogger.logger.error(ex.getMessage());
            MyLogger.logger.warn("returning null");
        }finally {
            session.close();
        }
        return e;
    }

    @Override
    public Event updateEvent(Event change) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.merge(change);
            tx.commit();
            MyLogger.logger.info("Updated the Event");
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
    public Event deleteEvent(int eventId) {
        Session session = HibernateUtil.getSession();
        Transaction tx =null;
        Event e = null;
        try{
            tx = session.beginTransaction();
            e = session.get(Event.class, eventId);
            session.delete(e);
            tx.commit();
            MyLogger.logger.info("Deleted the event");
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
