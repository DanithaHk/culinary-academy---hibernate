package lk.ijse.ormcoursework.bo.custom;

import lk.ijse.ormcoursework.bo.SuperBO;
import lk.ijse.ormcoursework.dto.PaymentDto;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    List<PaymentDto> getAllPayment() throws SQLException, ClassNotFoundException;

    String generateNewPaymentID()throws SQLException, ClassNotFoundException;

    boolean PaymentIdExists(String id) throws SQLException, ClassNotFoundException;

    boolean savePayment(PaymentDto paymentDTO) throws Exception;
}
