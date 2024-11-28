package lk.ijse.ormcoursework.dao;

import lk.ijse.ormcoursework.dao.Custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return daoFactory == null ? new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        USER,STUDENT,PROGRAMS,ENROLLMENT,PAYMENT
    }

    public SuperDao getDAO(DAOTypes types) {
        switch (types) {
            case USER:
                return new UserDAOImpl();
            case STUDENT:
                return new StudentDAOImpl();
            case PROGRAMS:
                return new ProgramsDAOImpl();
            case ENROLLMENT:
                return new EnrollmentDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();

            default:
                return null;
        }
    }
}
