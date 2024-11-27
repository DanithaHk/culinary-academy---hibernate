package lk.ijse.ormcoursework.dao.Custom;

import lk.ijse.ormcoursework.dao.CrudDAO;
import lk.ijse.ormcoursework.entity.User;

import java.io.IOException;

public interface UserDAO extends CrudDAO<User> {
    boolean checkPassword(String username, String password) throws IOException;

    User findUserByname(String username);
}
