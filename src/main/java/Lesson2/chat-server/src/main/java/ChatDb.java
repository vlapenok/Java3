import java.sql.*;

public class ChatDb {

    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement ps;

    public static void main(String[] args) {
        try {
            connect();
            createTable();
            insertUser("Bob", "bob", "123");
            insertUser("Jack", "jack", "456");
            insertUser("John", "john", "789");
//            readTable();
//            dropTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:chat.db");
        statement = connection.createStatement();
        System.out.println("Подключились к базе");
    }

    public static void disconnect() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Отключились от базы");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "nick TEXT NOT NULL,\n" +
                "login TEXT NOT NULL,\n" +
                "pass TEXT NOT NULL);";
        statement.executeUpdate(sql);
        System.out.println("Создали таблицу");
    }

    public static void insertUser(String nick, String login, String pass) throws SQLException {
//        String sql = "INSERT INTO users (nick, login, pass) values ('" + nick + "', '" + login + "', '" + pass + "');";
//        statement.executeUpdate(sql);
        String sql = "INSERT INTO users (nick, login, pass) VALUES (?, ?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nick);
            ps.setString(2, login);
            ps.setString(3, pass);
            ps.execute();
        }
    }

    public static void dropTable() throws SQLException {
        statement.execute("DROP TABLE users");
        System.out.println("Удалили таблицу");
    }

    public static String readTable(String userName, String pass) throws SQLException {
        String sql = "SELECT * FROM users WHERE login = ? AND pass = ?;";
        ps = connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("nick");
        } else {
            return "/error";
        }
    }
    public static void updateUsers(String newNick, String login) {
        try(PreparedStatement ps = connection.prepareStatement("UPDATE users SET nick = ? WHERE login = ?;")) {
            ps.setString(1, newNick);
            ps.setString(2, login);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
