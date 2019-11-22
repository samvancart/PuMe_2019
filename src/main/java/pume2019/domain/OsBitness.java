package pume2019.domain;

public class OsBitness {

    private String arch = System.getenv("PROCESSOR_ARCHITECTURE");
    private String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");

    public OsBitness() {
    }

    public String getOsBitness() {
        String realArch = arch != null && arch.endsWith("64")
                || wow64Arch != null && wow64Arch.endsWith("64")
                ? "64" : "32";
        return realArch;
    }

}
