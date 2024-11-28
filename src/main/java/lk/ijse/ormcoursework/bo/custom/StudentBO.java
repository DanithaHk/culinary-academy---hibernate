package lk.ijse.ormcoursework.bo.custom;

import lk.ijse.ormcoursework.bo.SuperBO;
import lk.ijse.ormcoursework.dto.StudentDto;
import lk.ijse.ormcoursework.entity.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentBO extends SuperBO {
    public boolean save(StudentDto studentDto) throws SQLException, ClassNotFoundException;
    public boolean update(StudentDto studentDto)throws SQLException, ClassNotFoundException;
    public boolean delete(String id)throws SQLException, ClassNotFoundException;
    public boolean search(StudentDto studentDto);
    StudentDto searchStudent(String studentId);

    public List<StudentDto> getAllStudents() throws SQLException, ClassNotFoundException;

    String generateNewId();

    List<String> getAllStudentIds() throws SQLException, ClassNotFoundException;

    Student getStudentById(String selectedStudentId);
}
