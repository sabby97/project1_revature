package com.trms.repository;

import com.trms.models.EventType;
import com.trms.models.GradeFormat;
import com.trms.util.HibernateUtil;
import com.trms.util.MyLogger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class GradeFormatRepoImpl implements GradeFormatRepo{

    @Override
    public GradeFormat addGradeFormat(GradeFormat g) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx= session.beginTransaction();
            g.setGradeId((int)session.save(g));
            tx.commit();
            MyLogger.logger.info("Adding a Grade");
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            if(tx != null) tx.rollback();
            MyLogger.logger.warn("Returning null");
            return null;
        }finally {
            session.close();
        }
        return g;
    }

    @Override
    public List<GradeFormat> getAllGradeFormats() {
        Session session = HibernateUtil.getSession();
        List<GradeFormat> gradeFormats = null;
        try {
            gradeFormats = session.createQuery("FROM GradeFormat").list();
            if (gradeFormats.size() <= 0){
                gradeFormats = null;
            }
            else{
                MyLogger.logger.info("Getting all GradeFormats");
            }
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return gradeFormats;
    }

    @Override
    public GradeFormat getGradeFormat(int gradeId) {
        Session session = HibernateUtil.getSession();
        GradeFormat g = null;
        try {
            g = session.get(GradeFormat.class, gradeId);
            MyLogger.logger.info("Got a GradeFormat");
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return g;
    }

    @Override
    public GradeFormat updateGradeFormat(GradeFormat change) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(change);
            tx.commit();
            MyLogger.logger.info("Updating a GradeFormat");
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
    public GradeFormat deleteGradeFormat(int gradeId) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        GradeFormat g = null;
        try {
            tx = session.beginTransaction();
            g = session.get(GradeFormat.class, gradeId);
            session.delete(g);
            tx.commit();
            MyLogger.logger.info("Deleting a EventType");
        }catch (HibernateException exception){
            MyLogger.logger.error(exception.getMessage());
            if(tx != null) tx.rollback();
            MyLogger.logger.warn("Returning null");
        }finally {
            session.close();
        }
        return g;
    }
}
