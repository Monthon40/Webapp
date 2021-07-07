package io.muzoo.ooc.webapp.basic.service;

import io.muzoo.ooc.webapp.basic.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    private static final String INSERT_USER_SQL = "INSERT INTO tbl_user (username, password, display_name) VALUES (?,?,?);";
    private static final String SELECT_USER_SQL = "SELECT * FROM tbl_user WHERE username = ?;";


    private DataBaseConnectionService dataBaseConnectionService;

    public void setDataBaseConnectionService(DataBaseConnectionService dataBaseConnectionService) {
        this.dataBaseConnectionService = dataBaseConnectionService;
    }

    //create new user
    public void createUser(String username, String password, String displayName) throws UserServiceException {
        try {
            Connection connection = dataBaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL);
            ps.setString(1, username);
            ps.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            ps.setString(3, displayName);
            ps.executeUpdate();
            // need to be manually committed to change
            connection.commit();
        } catch (SQLIntegrityConstraintViolationException e ){
            throw new UsernameNotUniqueException(String.format("Username %s has already been taken.", username));
        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }
    }

    public User findByUsername(String username) {
        try {
            Connection connection = dataBaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_SQL);
            ps.setString(1, username);

            boolean execute = ps.execute();
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),  //this is hashed password
                    resultSet.getString("display_name")

            );

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.setDataBaseConnectionService(new DataBaseConnectionService());
        User user = userService.findByUsername("strickwar");
        System.out.println(user.getUsername());
    }



    public Map<String, User> users = new HashMap<>();
    {
//        users.put("strickwar", new User("strickwar", "12345"));
//        users.put("admin", new User("admin","12345"));
    }


    public boolean checkIfUserExists(String username){
        return users.containsKey(username);
    }

}
