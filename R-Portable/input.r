
library(Rprebas)
library(Rpreles)
results<-1:46
trees<-1:3
tryCatch({
tryCatch({
con <- socketConnection(host="localhost", port = 6011, blocking=TRUE,
  server=FALSE, open="r+")
},
error=function(cond) {
data<-toString(cond)
     write_resp <- writeLines(data,con)
     return(NA)
}
)
siteInfo <- read.csv("Rprebas_examples-master/inputs/siteInfo.csv",header = T)
thinning <- read.csv("Rprebas_examples-master/inputs/thinning.csv",header = T)
initVar <- read.csv("Rprebas_examples-master/inputs/initVar.csv",header = T, row.names = 1)
weather <- read.csv("Rprebas_examples-master/inputs/weather.csv",header = T)
PAR = c(weather$PAR,weather$PAR,weather$PAR)
TAir = c(weather$TAir,weather$TAir,weather$TAir)
Precip = c(weather$Precip,weather$Precip,weather$Precip)
VPD = c(weather$VPD,weather$VPD,weather$VPD)
CO2 = c(weather$CO2,weather$CO2,weather$CO2)
DOY = c(weather$DOY,weather$DOY,weather$DOY)
},
error=function(cond) {
data<-toString(cond)
     write_resp <- writeLines(data,con)
     return(NA)
}
)
client <- function(){
PREBASout <- prebas(nYears=100,pCROBAS = pCROB, pPRELES = pPREL,siteInfo = c(1,1,1),thinning = NA,PAR=PAR,TAir=TAir,VPD=VPD,Precip=Precip,CO2=CO2,P0=NA,initVar = as.matrix(initVar),defaultThin = 1.,ClCut = 1.,inDclct = NA,inAclct = NA)
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
tryCatch({
client()
},
error=function(cond) {
data<-toString(cond)
     write_resp <- writeLines(data,con)
     return(NA)
close(con)
}
)
