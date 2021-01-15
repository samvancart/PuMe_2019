package pume2019.dataHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class RFileHandler {

    private String file;

    public RFileHandler(String file) {
        this.file = file;
    }

    public RFileHandler() {

    }

    public String getFilePath() throws IOException {
        Properties properties = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
        properties.load(is);
        String pathCsv = properties.getProperty("pathCsv");
        String pathR = properties.getProperty("rPath");
        return pathR + "\\" + pathCsv + "\\" + file;

    }

    public List<String> read() throws IOException {
        List<String> output = new ArrayList<>();
        String filePath = this.getFilePath();
        try {
            FileReader reader = new FileReader(filePath);
            BufferedReader in = new BufferedReader(reader);
            String currentLine;
            while ((currentLine = in.readLine()) != null) {
                output.add(currentLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
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
