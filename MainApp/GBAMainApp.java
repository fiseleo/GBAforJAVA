package MainApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GBAMainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Set window title
        primaryStage.setTitle("GBA Emulator");

        // Create menu bar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open ROM");
        MenuItem exitItem = new MenuItem("Exit");
        fileMenu.getItems().addAll(openItem, exitItem);
        menuBar.getMenus().add(fileMenu);

        // Create main layout
        VBox layout = new VBox();
        layout.getChildren().add(menuBar);

        // Add other controls
        Label label = new Label("Welcome to GBA Emulator!");
        Button startButton = new Button("Start Game");
        layout.getChildren().addAll(label, startButton);

        // Set event listeners
        startButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText(null);
            alert.setContentText("Game started!");
            alert.showAndWait();
        });
        exitItem.setOnAction(e -> System.exit(0));

        // Create scene and show
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
