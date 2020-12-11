import pl.coderslab.entity.User;

public class Main {
    public static void main(String[] args) {
        UserDao userDao=new UserDao();
        System.out.println(userDao.findAll());
    }
}
