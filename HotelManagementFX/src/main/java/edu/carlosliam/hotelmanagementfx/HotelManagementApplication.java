package edu.carlosliam.hotelmanagementfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;

public class HotelManagementApplication extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HotelManagementApplication.class.getResource("layout/home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(true);

        // Set the application icon
        InputStream iconStream = HotelManagementApplication.class.getResourceAsStream("images/nesteja_icon.png");
        if (iconStream != null) {
            stage.getIcons().add(new Image(iconStream));
        }

        primaryStage = stage;
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}