package pume2019.dataHandler;

import java.io.IOException;
import pume2019.domain.Info;

public class RInputHandler {

    public RInputHandler() {
    }

    public void getRInputs(RFileHandler fileHandler, RFunctions functions, Info info) throws IOException {
        fileHandler.clear();
        fileHandler.write(functions.libraryRprebas());
        fileHandler.write(functions.libraryRpreles());
        fileHandler.write(functions.varResults());
        fileHandler.write(functions.varTrees());
        //tryCatch open
        fileHandler.write(functions.tryCatchOpen());
        //create con object
        //tryCatch open
        fileHandler.write(functions.tryCatchOpen());
        fileHandler.write(functions.varCon());
        // handle error
        fileHandler.writeMany(functions.catchError());
        // close tryCatch
        fileHandler.writeMany(functions.tryCatchClose());
        fileHandler.write(functions.initCsv("siteInfo", "Rprebas_examples-master/inputs/siteInfo.csv", -1));
//        fileHandler.write(functions.initCsv("siteInfo", "C:/Users/Sam/Documents/PuMe2019_dev/PuMe_2019/R-Portable/Rprebas_examples-master/inputs/siteInfo.csv", -1));
        fileHandler.write(functions.initCsv("thinning", info.getManagPath(), -1));
        fileHandler.write(functions.initCsv("initVar", info.getInitPath(), 1));
        fileHandler.write(functions.initCsv("weather", info.getWeatherPath(), -1));
        fileHandler.write(functions.weatherValues("PAR"));
        fileHandler.write(functions.weatherValues("TAir"));
        fileHandler.write(functions.weatherValues("Precip"));
        fileHandler.write(functions.weatherValues("VPD"));
        fileHandler.write(functions.weatherValues("CO2"));
        fileHandler.write(functions.weatherValues("DOY"));
        // handle error
        fileHandler.writeMany(functions.catchError());
        // close tryCatch
        fileHandler.writeMany(functions.tryCatchClose());
        fileHandler.writeMany(functions.createRClientSocketLines(Integer.parseInt(info.getYears()),
                info.getHeath().getId(), info.getThinning().getRparam(), info.getThinning().getDefaultThin(), info.getThinning().getClCut()));
    }
}
