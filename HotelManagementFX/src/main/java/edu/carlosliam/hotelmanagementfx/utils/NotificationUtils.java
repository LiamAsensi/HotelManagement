package edu.carlosliam.hotelmanagementfx.utils;

import javafx.scene.image.ImageView;
import org.controlsfx.control.Notifications;

public class NotificationUtils {
    public static void showNotificationError(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showError();
    }

    public static void showNotificationWarning(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showWarning();
    }

    public static void showNotificationSuccess(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showConfirm();
    }

    public static void showNotificationInfo(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showInformation();
    }

    public static void showNotificationCustom(String title, String message, String graphic) {
        Notifications.create()
                .title(title)
                .text(message)
                .graphic(new ImageView(graphic))
                .show();
    }
}
