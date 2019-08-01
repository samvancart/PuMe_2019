
library(Rprebas)
library(Rpreles)
results<-1:46
trees<-1:3
siteInfo <- read.csv("Rprebas_examples-master/inputs/siteInfo.csv",header = T)
thinning <- read.csv("C:/Users/Sam/Documents/NetBeansProjects/pume/PuMe2019/src/main/resources/R-Portable/Rprebas_examples-master/inputs/thinning.csv",header = T)
initVar <- read.csv("C:/Users/Sam/Documents/NetBeansProjects/pume/PuMe2019/src/main/resources/R-Portable/Rprebas_examples-master/inputs/initVar.csv",header = T, row.names = 1)
weather <- read.csv("C:/Users/Sam/Documents/NetBeansProjects/pume/PuMe2019/src/main/resources/R-Portable/Rprebas_examples-master/inputs/weather.csv",header = T)
PAR = c(weather$PAR,weather$PAR,weather$PAR)
TAir = c(weather$TAir,weather$TAir,weather$TAir)
Precip = c(weather$Precip,weather$Precip,weather$Precip)
VPD = c(weather$VPD,weather$VPD,weather$VPD)
CO2 = c(weather$CO2,weather$CO2,weather$CO2)
DOY = c(weather$DOY,weather$DOY,weather$DOY)
PREBASout <- prebas(nYears=129,pCROBAS = pCROB, pPRELES = pPREL,siteInfo = c(1,1,1),thinning = thinning,PAR=PAR,TAir=TAir,VPD=VPD,Precip=Precip,CO2=CO2,P0=NA,initVar = as.matrix(initVar),defaultThin = 0.,ClCut = 1.,inDclct = NA,inAclct = NA)
client <- function(){
    con <- socketConnection(host="localhost", port = 6011, blocking=TRUE,
  server=FALSE, open="r+")
    for (i in results) {
        for (j in trees) {
	    for(k in PREBASout$output[ ,i,j,1]){
                     data<-toString(k)
	        write_resp <- writeLines(data, con)
	    }
        }
    }
close(con)
}
client()
