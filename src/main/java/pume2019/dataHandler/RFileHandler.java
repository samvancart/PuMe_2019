package pume2019.dataHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RFileHandler {

    private String file;

    public RFileHandler(String file) {
        this.file = file;
    }

    public RFileHandler() {

    }

    public void write(String input) throws IOException {
        try {
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter out = new BufferedWriter(writer);
            out.append(input);
            out.newLine();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMany(List<String> inputs) {
        try {
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter out = new BufferedWriter(writer);
            for (int i = 0; i < inputs.size(); i++) {
                out.append(inputs.get(i));
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(writer);
            out.write("");
            out.newLine();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runBat(String currentDirectory, String homePath, String exePath, String inputPath) throws IOException, InterruptedException {
        String bat = "cmd.exe /c cd " + homePath + " && " + exePath + " CMD BATCH " + inputPath + "";
        try {
            Process p = Runtime.getRuntime().exec(bat);
//            p.waitFor();
        p.waitFor(0, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            System.out.println("BAT EXCEPTION: " + e.getMessage());
        }
    }
}
