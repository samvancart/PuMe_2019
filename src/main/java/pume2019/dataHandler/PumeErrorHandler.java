package pume2019.dataHandler;

import java.io.File;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import pume2019.domain.Notification;
import pume2019.domain.Notifications;

public class PumeErrorHandler {

    public PumeErrorHandler() {

    }

    public boolean isCsv(String filePath) {
        String extension = "csv";
        return getFileExtension(filePath).equals(extension);
    }

    public String getFileExtension(String fullName) {
        if (fullName == null) {
            return null;
        }
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    public void checkForFileErrors(String name, String path, Notifications notifications) {
        if (path == null) {
            String message = name + ": No file path specified.";
            notifications.addNotification(new Notification(message));
        } else if (!this.isCsv(path)) {
            String format1 = this.getFileExtension(path);
            String format2 = "csv";
            String message = name + ": File format " + format1 + " is invalid. Required format is " + format2 + " (comma-separated values).";
            notifications.addNotification(new Notification(message));
        }
    }

    public Alert getErrorAlert(Notifications notifications) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        if (!notifications.getNotifications().isEmpty()) {
            errorAlert.setHeaderText("Error");
            List<Notification> notifList = notifications.getNotifications();
            String alert = "";
            for (Notification n : notifList) {
                alert += n.getMessage() + "\n";
            }
            errorAlert.setContentText(alert);
        } else {
            return null;
        }
        return errorAlert;
    }

    public void checkForRErrors(List<String> resultData, Notifications notifications) {
        for (String line : resultData) {
            String lowerCaseLine = line.toLowerCase();
            if (lowerCaseLine.contains("error")) {
                notifications.addNotification(new Notification(line));
            }
        }
    }

    public void serverSocketBindError(String msg,Notifications notifications) {
        String msg2 = ": Select free port to continue.";
        notifications.addNotification(new Notification(msg+msg2));
    }

}
