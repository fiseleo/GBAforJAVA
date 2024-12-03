package MainApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class GBAMainApp extends Application {
    private File selectedROM; // Store the selected ROM file

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
        TextArea outputArea = new TextArea(); // Display ROM details
        outputArea.setEditable(false);
        layout.getChildren().addAll(label, startButton, outputArea);

        // Set event listeners
        openItem.setOnAction(e -> {
            // Create a FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select a ROM File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("GBA Files (*.gba)", "*.gba")
            );

            // Show the FileChooser and get the selected file
            selectedROM = fileChooser.showOpenDialog(primaryStage);
            if (selectedROM != null) {
                outputArea.clear();
                outputArea.appendText("Selected ROM: " + selectedROM.getName() + "\n");
                outputArea.appendText("Path: " + selectedROM.getAbsolutePath() + "\n");
                outputArea.appendText("Size: " + selectedROM.length() + " bytes\n");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No File Selected");
                alert.setHeaderText(null);
                alert.setContentText("No file was selected.");
                alert.showAndWait();
            }
        });

        startButton.setOnAction(e -> {
            if (selectedROM != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Starting");
                alert.setHeaderText(null);
                alert.setContentText("Starting game with " + selectedROM.getName());
                alert.showAndWait();

                // Start the emulator in a new thread
                new Thread(() -> StartRAM.startEmulator(selectedROM)).start();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No ROM Loaded");
                alert.setHeaderText(null);
                alert.setContentText("Please select a ROM file before starting the game.");
                alert.showAndWait();
            }
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
