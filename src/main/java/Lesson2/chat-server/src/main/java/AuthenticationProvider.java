import java.sql.SQLException;

public interface AuthenticationProvider {
    String getNickByLoginAndPassword(String login, String pass);
    void updateNickname(String login, String pass);
    void dropTable();
    void disconnect();
    void start();
    void insertUser(String nick, String username, String password);
}
