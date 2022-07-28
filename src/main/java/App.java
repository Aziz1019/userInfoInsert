import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {


    public static void main(String[] args) {
        List<Multi> multis = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        short n = 3;
        System.out.println("\nWelcome to User Registration");
        int flag = 0;
        while (true) {
            System.out.print("Please enter your Username: ");
            String s = scanner.next();

            System.out.print("Name: ");
            String s1 = scanner.next();

            System.out.print("Age: ");
            int s2 = scanner.nextInt();
            Multi t1 = new Multi(s, s1, s2);
            multis.add(t1);
            n--;
            if (n == 0) {
                break;
            }
            System.out.print("\nYou can fill " + n + " more users" + "\nPress 1 to fill a new user or 0 to exit: ");
            short t = scanner.nextShort();
            if (t == 0) {
                flag = 1;
                for (Multi multi : multis) {
                    multi.start();
                }
                break;
            }
        }

        if (flag == 0) {
            for (Multi multi : multis) {
                multi.start();
            }
        }
    }
}

class Multi extends Thread {
    public static Connection con = null;
    private String s;
    private String s1;
    private int s2;

    public Multi(String username, String name, int age) {
        s = username;
        s1 = name;
        s2 = age;
    }

    public Multi() {
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public int getS2() {
        return s2;
    }

    public void setS2(int s2) {
        this.s2 = s2;
    }

    public void run() {
        try {
            con = PostgresConnection.getInstance();
            String sql = "insert into users(username, name, age) " +
                "VALUES ('" + getS() + "', '" + getS1() + "', '" + getS2() + "')";
            con.createStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Successfully Inserted!");
    }
}
