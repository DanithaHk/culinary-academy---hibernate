package lk.ijse.ormcoursework.bo.custom;



import lk.ijse.ormcoursework.bo.SuperBO;
import lk.ijse.ormcoursework.dto.EnrollmentDto;
import lk.ijse.ormcoursework.entity.Enrollment;

import java.sql.SQLException;
import java.util.List;

public interface EnrollmentBO extends SuperBO {
    List<EnrollmentDto> getAllEnrollment() throws SQLException, ClassNotFoundException;

    boolean EnrollmentIdExists(String id) throws SQLException, ClassNotFoundException;

    boolean isStudentEnrolledInCourse(String studentId, String courseId) throws Exception;

    boolean saveEnrollment(EnrollmentDto enrollmentDto) throws Exception;

    String generateNewEnrollmentID() throws Exception;

    List<String> getAllEnrollmentIds() throws SQLException, ClassNotFoundException;

    Enrollment findEnrollmentById(String eid);

    double getRemainingFeeByEnrollmentId(String eid);

    boolean updateRemainingFee(String eid, double updatedRemainFee);
    int getEnrollmentCount() throws SQLException;
}
