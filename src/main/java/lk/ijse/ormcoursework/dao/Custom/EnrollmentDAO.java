package lk.ijse.ormcoursework.dao.Custom;


import lk.ijse.ormcoursework.dao.CrudDAO;
import lk.ijse.ormcoursework.entity.Enrollment;

public interface EnrollmentDAO extends CrudDAO<Enrollment> {
    public boolean isStudentEnrolledInCourse(String studentId, String courseId) throws Exception;

    String generateNewID()throws Exception;

    Enrollment findEnrollmentById(String enrollmentId);

    double getRemainingFeeByEnrollmentId(String enrollmentId);

    boolean updateRemainingFee(String enrollmentId, double newFee);

    boolean IdExists(String enrollmentId);
}


