import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Controller {
    @FXML
    TextArea textArea;

    @FXML
    TextField textField, loginField;

    @FXML
    PasswordField passField;

    @FXML
    Label userNameLabel;

    @FXML
    HBox authPanel, msgPanel;

    @FXML
    ListView<String> clientsListView;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private File historyMsgFile;
    private BufferedOutputStream bos;
    private String nickname;
    private List<String> listOfMessageHistory = new LinkedList<>();
    private final int linesNumberOfMsgHistory = 100;

    // Метод скрытия-открытия панелей в зависимости от статуса авторизации
    private void setAuthorized(boolean authorized) {
        authPanel.setVisible(!authorized);
        authPanel.setManaged(!authorized);
        msgPanel.setVisible(authorized);
        msgPanel.setManaged(authorized);
        clientsListView.setVisible(authorized);
        clientsListView.setManaged(authorized);
    }

    public void btnAction() { // Метод отправки сообщений по кнопке и по нажатию Ентер
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            showError("Ошибка авторизации");
        }
    }

    public void sendCloseRequest() { // Метод отправки команды на выход из чата
        try {
            if (out != null && !socket.isClosed()) {
                out.writeUTF("/exit");
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void auth() { // Метод авторизации
        if (!connect()) { // Проверка на доступность сервера
            return;
        }
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passField.getText());
            loginField.clear();
            passField.clear();
        } catch (IOException e) {
            showError("Ошибка авторизации");
        }
    }

    public boolean connect() {
        if (socket != null && !socket.isClosed()) { // Если соединение не пустое и не закрыто
            return true;
        }
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Thread thread = new Thread(() -> {
                readMessage();
            });
            thread.start();
            return true;
        } catch (IOException e) {
            showError("Не удаётся подключится!");
            return false;
        }
    }

    private void readMessage() {
        try {
            while (true) { // Цикл чтения сообщений для авторизации
                String inputMsg = in.readUTF();
                // Сделать, чтобы можно было закрыть соединение при неудачной авторизации
                if (inputMsg.equals("/exit")) {
                    closeConnection();
                    break;
                }
                if (inputMsg.startsWith("/authOk ")) {
                    nickname = inputMsg.split("\\s+")[1];
                    Platform.runLater(() -> userNameLabel.setText(nickname));
                    setAuthorized(true);
                    createHistoryFile();
                    addHistoryToList(historyMsgFile, linesNumberOfMsgHistory);
                    break;
                }
                textArea.appendText(inputMsg + "\n");
            }
            while (true) { // Цикл чтения входящих сообщений
                String echoText = in.readUTF();
                if (echoText.startsWith("/")) {
                    if (echoText.equals("/exit")) {
                        break;
                    }
                    if (echoText.startsWith("/clients_list ")) {
                        Platform.runLater(() -> { // Передача выполнения кода в поток JavaFX
                            String[] clients_list = echoText.split("\\s+");
                            clientsListView.getItems().clear();
                            for (int i = 1; i < clients_list.length; i++) {
                                clientsListView.getItems().add(clients_list[i]);
                            }
                        });
                    }
                    continue;
                }
                textArea.appendText(echoText + "\n");
                writeHistoryToFile(historyMsgFile, echoText + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void showError(String msg) { // Всплывающее окно об ошибке
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }

    private void closeConnection() {
        setAuthorized(false);
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
        try {
            if (bos != null) {
                bos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод обработки двойного клика по списку пользователей
    @FXML
    private void doubleClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            String selectedUser = clientsListView.getSelectionModel().getSelectedItem();
            textField.setText("/w " + selectedUser + " ");
            textField.requestFocus();
            textField.selectEnd();
        }
    }

    // Создание файла для записи истории сообщений
    private void createHistoryFile() {
        historyMsgFile = new File("history_" + nickname + ".txt");
        if (!historyMsgFile.exists()) {
            try {
                historyMsgFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Запись истории сообщений в файл
    private void writeHistoryToFile(File historyMsgFile, String message) {
        byte[] bites = (message).getBytes();
        try {
            bos = new BufferedOutputStream(new FileOutputStream(historyMsgFile, true));
            bos.write(bites);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addHistoryToList(File historyMsgFile, int lines) {
        try (BufferedReader reader = new BufferedReader(new FileReader(historyMsgFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listOfMessageHistory.add(line);
                if (listOfMessageHistory.size() > lines) {
                    listOfMessageHistory.remove(0);
                }
            }
            for(String str : listOfMessageHistory) {
                textArea.appendText(str + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}