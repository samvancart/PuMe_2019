plot(bissell$Length, bissell$Flaws, xlim=c(0,1000), pch="o")
beta <- sum(bissell$Flaws)/sum(bissell$Length)
x <- seq(0, 1000, length=50)
lines(x, beta*x, lty=3)
h <- 100
W<-sm.weight(bissell$Length, x, h, options=list(poly.index=0))  
sm.beta <- (W %*% bissell$Flaws)/(W %*% bissell$Length)
lines(x,sm.beta*x)
lines(x,sm.beta*x+2*sqrt(sm.beta*x),lty=3)
lines(x,pmax(0,sm.beta*x-2*sqrt(sm.beta*x)),lty=3)