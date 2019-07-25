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
        fileHandler.write(functions.initCsv("siteInfo", "Rprebas_examples-master/inputs/siteInfo.csv", -1));
        fileHandler.write(functions.initCsv("thinning", info.getManagPath(), -1));
        fileHandler.write(functions.initCsv("initVar", info.getInitPath(), 1));
//        fileHandler.write(functions.initCsv("initVar", "Rprebas_examples-master/inputs/initVar.csv", 1));
        fileHandler.write(functions.initCsv("weather", info.getWeatherPath(), -1));
        fileHandler.write(functions.weatherValues("PAR"));
        fileHandler.write(functions.weatherValues("TAir"));
        fileHandler.write(functions.weatherValues("Precip"));
        fileHandler.write(functions.weatherValues("VPD"));
        fileHandler.write(functions.weatherValues("CO2"));
        fileHandler.write(functions.weatherValues("DOY"));
        fileHandler.write(functions.prebasOut(Integer.parseInt(info.getYears()),
                info.getHeath().getId()));
        fileHandler.writeMany(functions.createRClientSocketLines());
    }
}
