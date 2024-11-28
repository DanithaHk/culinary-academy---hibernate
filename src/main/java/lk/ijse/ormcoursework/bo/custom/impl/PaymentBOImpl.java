package lk.ijse.ormcoursework.bo.custom.impl;



import lk.ijse.ormcoursework.bo.custom.PaymentBO;
import lk.ijse.ormcoursework.dao.Custom.EnrollmentDAO;
import lk.ijse.ormcoursework.dao.Custom.PaymentDAO;
import lk.ijse.ormcoursework.dao.DAOFactory;
import lk.ijse.ormcoursework.dto.PaymentDto;
import lk.ijse.ormcoursework.entity.Enrollment;
import lk.ijse.ormcoursework.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    EnrollmentDAO enrollmentDAO = (EnrollmentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ENROLLMENT);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENT);
    @Override
    public List<PaymentDto> getAllPayment() throws SQLException, ClassNotFoundException {
        List<Payment> payments = paymentDAO.getAll();
        List<PaymentDto> dtos = new ArrayList<>();
        for (Payment payment : payments) {
            String enrollmentId = payment.getEnrollment() != null ? payment.getEnrollment().getEid() : null;
            dtos.add(new PaymentDto(payment.getId(),enrollmentId,payment.getAmount(),payment.getDate()));
        }
        return dtos;
    }

    @Override
    public String generateNewPaymentID() throws SQLException, ClassNotFoundException {
        return paymentDAO.generateNewID();
    }

    @Override
    public boolean PaymentIdExists(String PaymentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.IdExists(PaymentId);
    }

    @Override
    public boolean savePayment(PaymentDto paymentDTO) throws Exception {
        Enrollment enrollment = enrollmentDAO.findEnrollmentById(paymentDTO.getEid());
        return paymentDAO.save(new Payment(paymentDTO.getId(),enrollment,paymentDTO.getAmount(),paymentDTO.getDate()));
    }
}
