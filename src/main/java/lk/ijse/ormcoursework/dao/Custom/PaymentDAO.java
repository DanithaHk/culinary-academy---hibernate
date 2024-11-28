package lk.ijse.ormcoursework.dao.Custom;


import lk.ijse.ormcoursework.dao.CrudDAO;
import lk.ijse.ormcoursework.entity.Payment;

import java.sql.SQLException;

public interface PaymentDAO extends CrudDAO<Payment> {
    String generateNewID() throws SQLException, ClassNotFoundException;

    boolean IdExists(String paymentId);
}
