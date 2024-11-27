package lk.ijse.ormcoursework.bo.custom.impl;

import lk.ijse.ormcoursework.bo.custom.UserBO;
import lk.ijse.ormcoursework.dao.Custom.UserDAO;
import lk.ijse.ormcoursework.dao.DAOFactory;
import lk.ijse.ormcoursework.dto.UserDto;
import lk.ijse.ormcoursework.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public List<UserDto> getAllUsers() throws SQLException, ClassNotFoundException, IOException {
        List<UserDto> allUsers= new ArrayList<>();
        List<User> all = userDAO.getAll();
        for (User user : all) {
            allUsers.add(new UserDto(user.getUserId(), user.getUserName(), user.getPassword(), user.getRole()));
        }
        return allUsers;
    }

    @Override
    public boolean save(UserDto dto) throws SQLException, ClassNotFoundException, IOException {
        return userDAO.save(new User(dto.getUserId(), dto.getUserName(),dto.getPassword(),dto.getRole()));
    }

    @Override
    public boolean updateUser(UserDto dto) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException, IOException {
        return userDAO.delete(id);
    }

    @Override
    public String generateNewUserID() throws SQLException, ClassNotFoundException, IOException {
        return null;
    }

    @Override
    public User findUserByname(String username)  {
        return userDAO.findUserByname(username);
    }
}

