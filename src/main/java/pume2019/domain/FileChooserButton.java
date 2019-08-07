package pume2019.domain;

import java.io.File;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileChooserButton {

    private File file;

    public FileChooserButton() {
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void chooseFile(Stage stage, Info info, Button button) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            this.setFilePath(info, button, file);
            String path[] = file.getPath().split("\\\\");
            if (!path[0].equals("")) {
                button.setText(path[path.length - 1]);
            }
        }
    }

    public void setNoFileChosen(Stage stage, Info info, Button button) {
        this.setFile(null);
        this.setFilePath(info, button, file);
    }

    public void setFilePath(Info info, Button button, File file) {
        switch (button.getId()) {
            case "weatherBtn":
                if (file != null) {
                    info.setWeatherPath(file.getPath());
                } else {
                    info.setWeatherPath(null);
                }
                break;
            case "managBtn":
                if (file != null) {
                    info.setManagPath(file.getPath());
                } else {
                    info.setManagPath(null);
                }
                break;
            case "initBtn":
                if (file != null) {
                    info.setInitPath(file.getPath());
                } else {
                    info.setInitPath(null);
                }
                break;
            default:
                break;
        }
    }

}
