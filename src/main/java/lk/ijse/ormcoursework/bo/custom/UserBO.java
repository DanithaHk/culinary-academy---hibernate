package lk.ijse.ormcoursework.bo.custom;

import lk.ijse.ormcoursework.bo.SuperBO;
import lk.ijse.ormcoursework.dto.UserDto;
import lk.ijse.ormcoursework.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    public List<UserDto> getAllUsers() throws SQLException, ClassNotFoundException, IOException;
    public boolean save(UserDto dto) throws SQLException, ClassNotFoundException, IOException;

    public boolean updateUser(UserDto dto) throws SQLException, ClassNotFoundException, IOException;

    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException, IOException;

    public String generateNewUserID() throws SQLException, ClassNotFoundException, IOException;

    public User findUserByname(String username);
}
