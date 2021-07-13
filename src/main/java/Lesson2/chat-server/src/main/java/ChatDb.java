import java.sql.*;

public class ChatDb implements AuthenticationProvider {

    private Connection connection;
    private Statement statement;

    @Override
    public void start() {
        try {
            connect(); // Подключаемся к базе
            createTable(); // Создаем таблицу
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:chat.db");
        statement = connection.createStatement();
        System.out.println("Подключились к базе");
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "nick TEXT NOT NULL,\n" +
                "login TEXT NOT NULL,\n" +
                "pass TEXT NOT NULL);";
        statement.executeUpdate(sql);
        System.out.println("Создали таблицу");
    }

    @Override
    public void disconnect() {
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

    @Override
    public void insertUser(String nick, String login, String pass) {
//        String sql = "INSERT INTO users (nick, login, pass) values ('" + nick + "', '" + login + "', '" + pass + "');";
//        statement.executeUpdate(sql);
        String sql = "INSERT INTO users (nick, login, pass) VALUES (?, ?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nick);
            ps.setString(2, login);
            ps.setString(3, pass);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void dropTable() {
        try {
            statement.execute("DROP TABLE users");
            System.out.println("Удалили таблицу");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String getNickByLoginAndPassword(String login, String pass) {
        String sql = "SELECT * FROM users WHERE login = ? AND pass = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nick");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateNickname(String newNick, String login) {
        String sql = "UPDATE users SET nick = ? WHERE login = ?;";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newNick);
            ps.setString(2, login);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
