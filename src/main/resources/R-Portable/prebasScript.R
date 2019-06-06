library(Rprebas)
library(Rpreles)
nYears = 100
siteInfo <- read.csv("Rprebas_examples-master/inputs/siteInfo.csv",header = T)
thinning <- read.csv("Rprebas_examples-master/inputs/Thinning.csv",header = T)
initVar <- read.csv("Rprebas_examples-master/inputs/initVar.csv",header = T, row.names = 1)
obsData <- read.csv("Rprebas_examples-master/inputs/obsData.csv",header = T)
weather <- read.csv("Rprebas_examples-master/inputs/weather.csv",header = T)
PAR = c(weather$PAR,weather$PAR,weather$PAR)
TAir = c(weather$TAir,weather$TAir,weather$TAir)
Precip = c(weather$Precip,weather$Precip,weather$Precip)
VPD = c(weather$VPD,weather$VPD,weather$VPD)
CO2 = c(weather$CO2,weather$CO2,weather$CO2)
DOY = c(weather$DOY,weather$DOY,weather$DOY)

PREBASout <- prebas(nYears = 100, PAR=PAR,TAir=TAir,VPD=VPD,Precip=Precip,CO2=CO2)


sink("prebasScript.csv") print(PREBASout$output[ ,26,1,1],sep="\n") sink()