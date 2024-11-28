package lk.ijse.ormcoursework.dao.Custom.impl;

import lk.ijse.ormcoursework.config.SessionFactoryConfig;
import lk.ijse.ormcoursework.dao.Custom.ProgramsDAO;
import lk.ijse.ormcoursework.entity.Programs;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.sql.SQLException;
import java.util.List;

public class ProgramsDAOImpl implements ProgramsDAO {
    @Override
    public List<Programs> getAll() throws SQLException, ClassNotFoundException {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM programs");
        nativeQuery.addEntity(Programs.class);
        List<Programs> programs = nativeQuery.getResultList();
        transaction.commit();
        session.close();
        return programs;
    }

    @Override
    public boolean save(Programs entity) throws SQLException, ClassNotFoundException {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Programs entity) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String sql = "DELETE FROM programs WHERE program_id = :id";
        NativeQuery<Programs> nativeQuery = session.createNativeQuery(sql);
        nativeQuery.setParameter("id", id);
        nativeQuery.executeUpdate();

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Programs search(String id) {
        return null;
    }

    @Override
    public Programs getProgramById(String courseId) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        try {
            // Fetch the course object based on the ID
            Programs programs = session.get(Programs.class, courseId);
            tx.commit();  // Commit the transaction
            return programs;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}


