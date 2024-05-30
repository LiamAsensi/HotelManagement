package edu.carlosliam.hotelmanagementfx.utils;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.control.PopOver;
import org.controlsfx.validation.ValidationSupport;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtils {
    public static void showErrorsOnNode(ValidationSupport validator, Node node, Node container) {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(5);
        PopOver popOver = new PopOver(vbox);

        container.setOnMouseEntered(e -> {
            if (validator.isInvalid()) {
                vbox.getChildren().clear();
                List<Label> errorFields = new ArrayList<>();
                errorFields.add(new Label("Please correct the following errors:"));
                validator.getValidationResult().getMessages().forEach(m -> {
                    Label errorLabel = new Label(m.getText());
                    errorLabel.setTextFill(Color.RED);
                    errorFields.add(errorLabel);
                });

                vbox.getChildren().addAll(errorFields);

                popOver.show(node);
            }
        });

        container.setOnMouseExited(e -> {
            popOver.hide();
        });
    }
}
