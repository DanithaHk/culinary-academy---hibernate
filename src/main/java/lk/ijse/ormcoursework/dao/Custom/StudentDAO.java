package lk.ijse.ormcoursework.dao.Custom;

import lk.ijse.ormcoursework.dao.CrudDAO;
import lk.ijse.ormcoursework.entity.Student;

public interface StudentDAO extends CrudDAO<Student> {
    Student getStudentById(String sid);
//    List<Student> getAll() throws SQLException, ClassNotFoundException;


}
