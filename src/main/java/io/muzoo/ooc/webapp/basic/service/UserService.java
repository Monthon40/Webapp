package io.muzoo.ooc.webapp.basic.service;

import io.muzoo.ooc.webapp.basic.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final String INSERT_USER_SQL = "INSERT INTO tbl_user (username, password, display_name) VALUES (?,?,?);";
    private static final String SELECT_USER_SQL = "SELECT * FROM tbl_user WHERE username = ?;";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM tbl_user;";
    private static final String DELETE_USER_SQL = "DELETE FROM tbl_user WHERE username = ?;";


    private static UserService service;

    private DataBaseConnectionService dataBaseConnectionService;


    private UserService() {

    }

    public static UserService getInstance() {
        if (service == null) {
            service = new UserService();
            service.setDataBaseConnectionService(DataBaseConnectionService.getInstance());
        }
        return service;
    }

    public void setDataBaseConnectionService(DataBaseConnectionService dataBaseConnectionService) {
        this.dataBaseConnectionService = dataBaseConnectionService;
    }

    //create new user
    public void createUser(String username, String password, String displayName) throws UserServiceException {
        try (
                Connection connection = dataBaseConnectionService.getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL);) {

            ps.setString(1, username);
            ps.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            ps.setString(3, displayName);
            ps.executeUpdate();
            // need to be manually committed to change
            connection.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new UsernameNotUniqueException(String.format("Username %s has already been taken.", username));
        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }
    }

    public User findByUsername(String username) {
        try (
                Connection connection = dataBaseConnectionService.getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_USER_SQL);) {
            ps.setString(1, username);

            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),  //this is hashed password
                    resultSet.getString("display_name")

            );

        } catch (SQLException throwables) {
            return null;
        }
    }


    /**
     * list all users in database
     *
     * @return list of users, never return null
     */
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = dataBaseConnectionService.getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS_SQL);) {

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                users.add(
                        new User(
                                resultSet.getLong("id"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),  //this is hashed password
                                resultSet.getString("display_name")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    //delete user
    public boolean deleteUserByUsername(String username) {
        try (
                Connection connection = dataBaseConnectionService.getConnection();
                PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL);
                ) {
            ps.setString(1, username);
            int deleteCount = ps.executeUpdate();
            connection.commit();
            return deleteCount > 0;
        } catch (SQLException throwables) {
            return false;
        }
//        throw new UnsupportedOperationException("not yet done");
    }


    /**
     * Users can only change their display name when updating profile.
     *
     * @param id
     * @param displayName
     */
    public void updateUserById(long id, String displayName) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Change password method is separated from update user method because user normally
     * never change password and update profile at the same time.
     *
     * @param newPassword
     */
    public void changePassword(String newPassword) {
        throw new UnsupportedOperationException("not yet implemented");
    }


    public static void main(String[] args) {
        UserService userService = UserService.getInstance();
        try {
            userService.createUser("superuser","asdf","SuperAdmin");
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
//        UserService userService1 = new UserService();
//        userService1.setDataBaseConnectionService(new DataBaseConnectionService());
//        User users = userService1.findByUsername("strickwar");
//        System.out.println(users);
//        for(User user : users){
//            System.out.println(user.getUsername());
//        }
    }
}
