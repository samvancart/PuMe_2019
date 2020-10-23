package pume2019.dataHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class MyServerSocket {

    private Socket clientSocket;
    private ServerSocket serverSocket;
    private PrintWriter out;
    private BufferedReader in;
    private List<String> resultData;
    private final RFileHandler fileHandler;
    private final RFunctions functions;
    private boolean bindError = false;
    private String bindErrorMsg = "";

    // constructor with port 
    public MyServerSocket(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            bindError = true;
            bindErrorMsg = e.getMessage();
        }
        fileHandler = new RFileHandler();
        functions = new RFunctions();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public List<String> getResultData() {
        return resultData;
    }

    public boolean isBindError() {
        return bindError;
    }

    public String getBindErrorMsg() {
        return bindErrorMsg;
    }

    public void start() throws InterruptedException, URISyntaxException, IOException {
        try {

//            RUN BATCH JOB HERE
            fileHandler.runBat(functions.getCurrentDirectory(), functions.getHomePath(), functions.getExePath(), functions.getInputPath());

            Thread.sleep(1500);
            clientSocket = serverSocket.accept();

//            this.addInputStreamToResultData(clientSocket);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        resultData = new ArrayList<>();
        String line = "";
        while ((line = in.readLine()) != null) {
            resultData.add(line);
        }

        } catch (IOException e) {
            System.out.println("Exception_1: " + e.getMessage());

        }
    }

    public void addInputStreamToResultData(Socket clientSocket) throws IOException {
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        resultData = new ArrayList<>();
        String line = "";
        while ((line = in.readLine()) != null) {
            resultData.add(line);
        }
    }

    public void close() throws IOException {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Exception_2: " + e);
        }
    }

    public List<Map<Integer, List<String>>> getResultDataList(int years) {
        Map<Integer, List<String>> pineMap = new HashMap<>();
        Map<Integer, List<String>> spruceMap = new HashMap<>();
        Map<Integer, List<String>> birchMap = new HashMap<>();
        int start = 0;
        for (int i = 0; i < 46; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 0 && j == 0) {
                    start = 0;
                } else {
                    start += years;
                }
                int end = start + years;
                if (end > resultData.size()) {
                    end = resultData.size();
                }
                for (int k = start; k < end; k++) {
                    switch (j) {
                        case 0:
                            pineMap.putIfAbsent(i + 1, new ArrayList<>());
                            pineMap.get(i + 1).add(resultData.get(k));
                            break;
                        case 1:
                            spruceMap.putIfAbsent(i + 1, new ArrayList<>());
                            spruceMap.get(i + 1).add(resultData.get(k));
                            break;
                        case 2:
                            birchMap.putIfAbsent(i + 1, new ArrayList<>());
                            birchMap.get(i + 1).add(resultData.get(k));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        List<Map<Integer, List<String>>> maps = new ArrayList<>();
        maps.add(pineMap);
        maps.add(spruceMap);
        maps.add(birchMap);
        return maps;
    }
}
