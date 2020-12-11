import pl.coderslab.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDao {

    private static final String CREATE_USER_QUERY="insert into users(username, email, password) values(?,?,?) ";
    private static final String DELETE_USER_QUERY="delete from users where user_id=?";
    private static final String SELECT_USER_BY_ID="select*from users where user_id=?";
    private static final String UPDATE_USER="update workshop2.users set users.username=?,users.email=?,users.password=? where users.user_id=?";
    private static final String FIND_ALL="select * from users";

    public User createUser(User user) {
        try(Connection connection=DBUtil.connect()){
            PreparedStatement preparedStatement=connection.prepareStatement(CREATE_USER_QUERY,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.execute();
            ResultSet resultSet= preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                user.setId(resultSet.getInt(1));
            }
            return user;
        }catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public User selectById(long id){
        User user=null;
        try(Connection connection=DBUtil.connect()){
            PreparedStatement statement=connection.prepareStatement(SELECT_USER_BY_ID);
            statement.setLong(1,id);
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                user=new User(
                        resultSet.getLong("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return user;
    }

    public void deleteUser(long id) {
        try (   Connection connection=DBUtil.connect();
                PreparedStatement statement =connection.prepareStatement(DELETE_USER_QUERY)) {
            statement.setInt(1, Math.toIntExact(id));
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        Scanner scanner=new Scanner(System.in);
        String insertUsername;
        String insertEmail;
        String insertPassword;
        int INSERT_ID= Math.toIntExact(user.getId());

        System.out.println("Wprowadz username: ");
        insertUsername=scanner.next();
        System.out.println("Wprowadz email: ");
        insertEmail= scanner.next();
        System.out.println("Wprowadz password: ");
        insertPassword= scanner.next();
        scanner.nextLine();

        try(Connection connection=DBUtil.connect()) {
            DBUtil.insert(connection,UPDATE_USER,insertUsername,insertEmail,insertPassword,String.valueOf(INSERT_ID));
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public ArrayList<User> findAll(){
        ArrayList<User>users=new ArrayList<>();
        try(Connection connection=DBUtil.connect()){
            PreparedStatement statement=connection.prepareStatement(FIND_ALL);
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                User user=new User(
                        resultSet.getLong("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
                users.add(user);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            return users;
        }
        return users;
    }

}
