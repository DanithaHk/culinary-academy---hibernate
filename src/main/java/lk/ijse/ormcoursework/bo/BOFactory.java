package lk.ijse.ormcoursework.bo;

import lk.ijse.ormcoursework.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory bOFactory;
    private BOFactory(){}

    public static BOFactory getInstance(){
        return (bOFactory==null)?bOFactory=new BOFactory():bOFactory;
    }

    public enum BOTypes{
        USER,STUDENT,PROGRAMS,ENROLLMENT,PAYMENT
    }

    public SuperBO getBO(BOTypes boTypes){
        switch(boTypes){
            case USER:
                return new UserBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            case PROGRAMS:
                return new ProgramsBOImpl();
            case ENROLLMENT:
                return new EnrollmentBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            default:
                return null;
        }
    }
}
