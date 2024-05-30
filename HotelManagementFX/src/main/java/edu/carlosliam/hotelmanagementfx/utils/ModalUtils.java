package edu.carlosliam.hotelmanagementfx.utils;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ModalUtils {
    public static Stage modalStage;

    public static void openModal(String layout) throws IOException {
        modalStage = new Stage();
        modalStage.initOwner(HotelManagementApplication.primaryStage);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader(HotelManagementApplication.class.getResource(layout));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        modalStage.setScene(scene);

        HotelManagementApplication.primaryStage.getScene().getRoot().setEffect(new ColorAdjust(0, -0.5, -0.5, 0));

        modalStage.showAndWait();

        HotelManagementApplication.primaryStage.getScene().getRoot().setEffect(null);
    }

    public static void openModalParent(Parent parent) {
        modalStage = new Stage();
        modalStage.initOwner(HotelManagementApplication.primaryStage);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.setResizable(false);

        Scene scene = new Scene(parent);
        modalStage.setScene(scene);

        HotelManagementApplication.primaryStage.getScene().getRoot().setEffect(new ColorAdjust(0, -0.5, -0.5, 0));

        modalStage.showAndWait();

        HotelManagementApplication.primaryStage.getScene().getRoot().setEffect(null);
    }
}
