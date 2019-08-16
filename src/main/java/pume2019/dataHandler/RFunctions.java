package pume2019.dataHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RFunctions {

    public String tryCatchOpen() {
        String tryCatch = "tryCatch({";
        return tryCatch;
    }

    public List<String> catchSocketError() {
        List<String> lines = new ArrayList<>();
        lines.add("},");
        lines.add("error=function(cond) {");
        lines.add("data<-toString(cond)");
        lines.add("     write_resp <- writeLines(data,con)");
        lines.add("     return(NA)");
        lines.add("close(con)");
        return lines;
    }

    public List<String> catchError() {
        List<String> lines = new ArrayList<>();
        lines.add("},");
        lines.add("error=function(cond) {");
        lines.add("data<-toString(cond)");
        lines.add("     write_resp <- writeLines(data,con)");
        lines.add("     return(NA)");
        return lines;
    }

    public String finallyOpen() {
        String finallyOpen = "finally={";
        return finallyOpen;
    }

    public List<String> tryCatchClose() {
        List<String> lines = new ArrayList<>();
        lines.add("}");
        lines.add(")");
        return lines;
    }

    public String libraryRprebas() {
        String prebas = "library(Rprebas)";
        return prebas;
    }

    public String libraryRpreles() {
        String preles = "library(Rpreles)";
        return preles;
    }

    public String varResults() {
        String results = "results<-1:46";
        return results;
    }

    public String varTrees() {
        String trees = "trees<-1:3";
        return trees;
    }

    public String varCon() {
        String con = "con <- socketConnection(host=\"localhost\", port = 6011, blocking=TRUE,\n"
                + "  server=FALSE, open=\"r+\")";
        return con;
    }

    public String initCsv(String value, String path, int rowNames) {
        String text;
        if (rowNames != -1) {
            text = value + " <- read.csv(" + "\"" + path + "\"" + ",header = T, row.names = " + rowNames + ")";
        } else {
            text = value + " <- read.csv(" + "\"" + path + "\"" + ",header = T)";
        }
        return text;
    }

    public String weatherValues(String value) {
        String text = value + " = c(weather$" + value + ",weather$" + value + ",weather$" + value + ")";
        return text;
    }

    public String prebasOut(int years, int site, String thinning, int defaultThin, int ClCut) {
        String text = "PREBASout <- prebas("
                + "nYears=" + years + ","
                + "pCROBAS = pCROB,"
                + " pPRELES = pPREL,"
                + "siteInfo = c(1,1," + site + "),"
                + "thinning = " + thinning + ","
                + "PAR=PAR,TAir=TAir,VPD=VPD,Precip=Precip,CO2=CO2,"
                + "P0=NA,"
                + "initVar = as.matrix(initVar),"
                + "defaultThin = " + defaultThin + ".,"
                + "ClCut = " + ClCut + ".,"
                + "inDclct = NA,"
                + "inAclct = NA)";
        return text;
    }

    public List<String> createRClientSocketLines(int years, int site, String thinning, int defaultThin, int ClCut) {
        List<String> lines = new ArrayList<>();
        lines.add("client <- function(){");

        // prebasOut
        lines.add(prebasOut(years, site, thinning, defaultThin, ClCut));
        lines.addAll(responseLoop());
        lines.add("close(con)");
        lines.add("}");
        //open tryCatch
        lines.add(tryCatchOpen());
        lines.add("client()");
        //catchError
        lines.addAll(catchSocketError());
        //Close tryCatch
        lines.addAll(tryCatchClose());
        return lines;
    }

    public List<String> responseLoop() {
        List<String> lines = new ArrayList<>();
        lines.add("    for (i in results) {");
        lines.add("        for (j in trees) {");
        lines.add("	    for(k in PREBASout$output[ ,i,j,1]){");
        lines.add("                     data<-toString(k)");
        lines.add("	        write_resp <- writeLines(data, con)");
        lines.add("	    }");
        lines.add("        }");
        lines.add("    }");

        return lines;
    }

    public String getCurrentDirectory() throws IOException, URISyntaxException {
        String cur = System.getProperty("user.dir");
        Properties properties = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
        properties.load(is);
        String rPath = properties.getProperty("rPath");
        String currentDirectory = cur+"\\"+rPath;

        return currentDirectory;
    }

    public String getHomePath() throws IOException, URISyntaxException {
        String currentDirectory = getCurrentDirectory();
        String homePath = "\"" + currentDirectory + "\"";
        return homePath;
    }

    public String getExePath() throws IOException, URISyntaxException {
        String currentDirectory = getCurrentDirectory();
        String exePath = "\"" + currentDirectory + "\\App\\R-Portable\\bin\\R.exe" + "\"";
        return exePath;
    }

    public String getInputPath() throws IOException, URISyntaxException {
        String currentDirectory = getCurrentDirectory();
        String inputPath = "\"" + currentDirectory + "\\input.R" + "\"";
        return inputPath;
    }

}
