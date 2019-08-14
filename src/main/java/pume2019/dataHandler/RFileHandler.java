package pume2019.dataHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println("homePath: " + homePath);
        System.out.println("exePath: " + exePath);
        System.out.println("inputPath: " + inputPath);
//        ProcessBuilder builder = new ProcessBuilder(
//                "cmd.exe", "/c", "cd " + homePath + " && " + exePath + " CMD BATCH " + inputPath + "");
//        builder.redirectErrorStream(true);
//        builder.start();
        String bat = "cmd.exe /c cd " + homePath + " && " + exePath + " CMD BATCH " + inputPath + "";
        try {
            Process p = Runtime.getRuntime().exec(bat);
            p.waitFor();
        } catch (Exception e) {
            System.out.println("BAT EXCEPTION: "+e.getMessage());   
        }
    }
//        public void runBat2() throws IOException {
//        String currentDirectory = System.getProperty("user.dir");
////        System.out.println("The current working directory is " + currentDirectory + "\\resources\\R-Portable");
//        System.out.println("The current working directory is " + currentDirectory);
//        ProcessBuilder builder = new ProcessBuilder(
//                "cmd.exe", "/c", "cd " + "\"" + currentDirectory + "\\src\\main\\resources\\R-Portable\" && "
//                + "\"" + currentDirectory + "\\src\\main\\resources\\R-Portable\\App\\R-Portable\\bin\\R.exe" + "\"" + " CMD BATCH " + "\"" + currentDirectory + "\\src\\main\\resources\\R-Portable\\input.R" + "\"" + "");
//        builder.redirectErrorStream(true);
//        builder.start();
//    }
}
