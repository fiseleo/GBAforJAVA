package MainApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GBAMainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // 设置窗口标题
        primaryStage.setTitle("GBA 模拟器");

        // 创建菜单栏
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("文件");
        MenuItem openItem = new MenuItem("打开 ROM");
        MenuItem exitItem = new MenuItem("退出");
        fileMenu.getItems().addAll(openItem, exitItem);
        menuBar.getMenus().add(fileMenu);

        // 创建主布局
        VBox layout = new VBox();
        layout.getChildren().add(menuBar);

        // 添加其他控件
        Label label = new Label("欢迎使用 GBA 模拟器！");
        Button startButton = new Button("开始游戏");
        layout.getChildren().addAll(label, startButton);

        // 设置事件监听
        startButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("游戏开始！");
            alert.showAndWait();
        });
        exitItem.setOnAction(e -> System.exit(0));

        // 创建场景并显示
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
