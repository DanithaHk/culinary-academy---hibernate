package lk.ijse.ormcoursework.bo.custom.impl;

import lk.ijse.ormcoursework.bo.custom.ProgramsBO;
import lk.ijse.ormcoursework.dao.Custom.ProgramsDAO;
import lk.ijse.ormcoursework.dao.DAOFactory;
import lk.ijse.ormcoursework.dto.ProgramsDto;
import lk.ijse.ormcoursework.entity.Programs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramsBOImpl implements ProgramsBO {

    ProgramsDAO programsDAO = (ProgramsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PROGRAMS);
    @Override
    public boolean save(ProgramsDto programDto) throws SQLException, ClassNotFoundException {
        return programsDAO.save(new Programs(programDto.getId(), programDto.getName(),programDto.getDuration(),programDto.getFee()));
    }

    @Override
    public boolean update(ProgramsDto programDto) throws SQLException, ClassNotFoundException {
        return programsDAO.update(new Programs(programDto.getId(), programDto.getName(), programDto.getDuration(), programDto.getFee()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return programsDAO.delete(id);
    }

    @Override
    public Programs search(String programCode) throws SQLException {
        return null;
    }

    @Override
    public List<ProgramsDto> getAllPrograms() throws SQLException, ClassNotFoundException {
        List<ProgramsDto> allPrograms= new ArrayList<>();
        List<Programs> all = programsDAO.getAll();
        for (Programs programs : all) {
            allPrograms.add(new ProgramsDto(programs.getId(), programs.getName(), programs.getDuration(), programs.getFee()));
        }
        return allPrograms;
    }

    @Override
    public List<String> getAllProgramIds() throws SQLException, ClassNotFoundException {
        List<String> programIds = new ArrayList<>();
        List<Programs> courses = programsDAO.getAll();
        for (Programs programs : courses) {
            programIds.add(programs.getId());
        }
        return programIds;
    }

    @Override
    public Programs getProgramById(String selectedProgramId) {
        return programsDAO.getProgramById(selectedProgramId);
    }
}
