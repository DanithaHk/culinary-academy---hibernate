package lk.ijse.ormcoursework.bo.custom;

import lk.ijse.ormcoursework.bo.SuperBO;
import lk.ijse.ormcoursework.dto.ProgramsDto;
import lk.ijse.ormcoursework.entity.Programs;

import java.sql.SQLException;
import java.util.List;

public interface ProgramsBO extends SuperBO {
    boolean save(ProgramsDto programDto) throws SQLException, ClassNotFoundException;
    boolean update(ProgramsDto programDto) throws SQLException, ClassNotFoundException;
    boolean delete(String id)throws SQLException, ClassNotFoundException;
    Programs search(String programCode) throws SQLException;
    List<ProgramsDto> getAllPrograms() throws SQLException, ClassNotFoundException;
    List<String> getAllProgramIds() throws SQLException, ClassNotFoundException;

    Programs getProgramById(String selectedProgramId);
}
