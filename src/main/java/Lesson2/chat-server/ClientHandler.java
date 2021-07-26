import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private String nickName;
    private String login;
    private String password;
    private DataInputStream in;
    private DataOutputStream out;
    private ExecutorService fixedExecutor;

    public String getNickName() {
        return nickName;
    }

    ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            fixedExecutor = Executors.newSingleThreadExecutor();
            // Поток для чтения сообщений от сервера
//            new Thread(() -> logic()).start();
            fixedExecutor.execute(() -> logic());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logic() {
        try {
            while (!authentication(in.readUTF())) ;
            while (readIncomingMsg(in.readUTF())) ;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Клиент " + nickName + " отключился");
            server.unsubscribe(this); // Если клиент отключился, то удаляем его из списка
            closeConnection();
        }
    }

    private void closeConnection() { // Метод закрытия потоков и соединения
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fixedExecutor.shutdown();
    }

    // Метод отправки сообщений неавторизованному клиенту
    public void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для чтения сообщений об авторизации
    private boolean authentication(String msg) throws SQLException {
        if (msg.startsWith("/auth ")) { // Получение команды для авторизации на сервере
            String[] authText = msg.split("\\s+"); // Разбитие строки по пробелам
            if (authText.length != 3) {
                sendMessage("Server: Неверная комбинация логи/пароль");
                return false;
            } else if (authText[1].startsWith("/")) {
                sendMessage("Server: Имя не может начинаться на \"/\"");
                return false;
            } else {
                login = authText[1];
                password = authText[2];
                nickName = server.getAuthenticationProvider().getNickByLoginAndPassword(login, password);
                if (nickName != null) { // Вызов метода обращения к БД
                    if (server.chekUserName(nickName)) {
                        sendMessage("/authOk " + nickName); // Отправка команды об успешной авторизации
                        server.subscribe(this); // Добавление данного клиента в список
                        return true;
                    } else {
                        sendMessage("Server: Пользователь с такими данными уже в находится в чате");
                        return false;
                    }
                } else {
                    sendMessage("Server: Пользователь с такими данными не найден");
                    return false;
                }
            }
        } else {
            sendMessage("Server: Вам необходимо авторизоваться");
            return false;
        }
    }

    private boolean readIncomingMsg(String msg) throws SQLException {
        if (msg.startsWith("/")) {
            if (msg.equals("/exit")) { // команда для выхода пользователя из чата
                sendMessage(msg);
                return false;
            } else if (msg.startsWith("/w ")) { // команда для отправки персонального сообщения
                // Доработать, чтобы сообщение не могло быть нулевым
                String[] personalText = msg.split("\\s+", 3);
                if(personalText.length < 3) {
                    sendMessage("Server: Вы не ввели сообщение");
                    return true;
                }
                server.personalSendMessage(this, personalText[1], personalText[2]);
                return true;
            } else if(msg.startsWith("/ch ")) { // Команда для смены никнейма
                String[] newNick = msg.split("\\s+");
                server.getAuthenticationProvider().updateNickname(newNick[1], login);
//                nickName = server.getAuthenticationProvider().getNickByLoginAndPassword() ДОДЕЛАТЬ смену ника
                sendMessage("Server: Авторизуйтесь заново для обновления");
                sendMessage("/exit");
                return false;
            }
            return true;
        }
        server.broadcastMessage(nickName + ": " + msg);
        return true;
    }
}
