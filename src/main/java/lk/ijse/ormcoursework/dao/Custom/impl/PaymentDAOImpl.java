package lk.ijse.ormcoursework.dao.Custom.impl;


import lk.ijse.ormcoursework.config.SessionFactoryConfig;
import lk.ijse.ormcoursework.dao.Custom.PaymentDAO;
import lk.ijse.ormcoursework.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public List<Payment> getAll() throws SQLException, ClassNotFoundException {
        List<Payment> all = new ArrayList<>();
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        all = session.createQuery("from Payment ").list();
        transaction.commit();
        session.close();
        return all;
    }

    @Override
    public boolean save(Payment entity) throws SQLException, ClassNotFoundException {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction tx = session.beginTransaction();
        session.save(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Payment entity) throws SQLException, ClassNotFoundException {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction tx = session.beginTransaction();
        session.update(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Payment search(String id) {
        return null;
    }


    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String lastID = (String) session.createQuery("SELECT p.id FROM Payment p ORDER BY p.id DESC")
                    .setMaxResults(1)
                    .uniqueResult();

            if (lastID != null) {
                int id = Integer.parseInt(lastID.replace("P", "")) + 1;
                return "P" + String.format("", id);
            } else {
                return "P1";
            }
        }
    }

    @Override
    public boolean IdExists(String paymentId) {
        return false;
    }

}
