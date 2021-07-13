import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ChatApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlloader = new FXMLLoader();
        Parent root = fxmlloader.load(getClass().getResource("chat.fxml").openStream());
        Controller controller = (Controller) fxmlloader.getController();
//        Parent root = FXMLLoader.load(getClass().getResource("chat.fxml"));
        primaryStage.setTitle("Chat window");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                controller.sendCloseRequest();
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}