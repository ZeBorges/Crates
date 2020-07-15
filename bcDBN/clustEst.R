csvTest <- read.csv("2dbn20_2.csv")
classdoc <- "2dbn20_class_2.csv"
nrClusters=4
DKL = csvTest

library("cluster")
library("clusterSim")
library("clusterCrit")




km  <- kmeans(DKL,nrClusters)
pamMtx <- pam(DKL, nrClusters, diss = inherits(DKL, "dist"))
pamIndexVec <- pam(DKL, nrClusters, cluster.only = TRUE)
sil <- silhouette(pam(DKL,nrClusters))
sumsil<-0
for(i in 1:length(csvTest)){
  sumsil<-sumsil+sil[i,3]
}
medsil <- sumsil/length(csvTest)


dbi <- index.DB(DKL, pamMtx$clustering, DKL ,centrotypes = "medoids")

#plot(sil)
plot(hclust(daisy(DKL, metric = "euclidean"), method = "ward.D2"))
fanny(DKL, nrClusters, memb.exp = 1.5)

data <- data.frame()
data <- cbind(pamIndexVec)
print(data)

Mode <- function(x) {
  ux <- unique(x)
  ux[which.max(tabulate(match(x, ux)))]
}


# data2 <- data.frame()
# data2 <- cbind(c(1:length(data)))
# 
# 
# x=1;
# iters = 1;
# for(grp in 1:nrClusters){
#   if(x == 1){
#     data2[1:(length(data)/nrClusters)] = Mode(data[1:(length(data)/nrClusters)])
#     x = x + length(data)/nrClusters
#     iters = 2;
#   }else{
#     data2[x:(length(data) * iters/nrClusters)] = Mode(data[x:(length(data) * iters/nrClusters) ])
#     x = x + length(data)/nrClusters
#     
#     if(x > length(data))
#       break;
#     
#     iters = iters + 1
#     
#   }
# }

#data3 <- data.frame()
#data3 = data2 - data

#accuracy <- colSums(data3==0)/nrow(data3)*100

#library(mclust)
#fit <- Mclust(DKL)
#plot(fit) # plot results 
#summary(fit) # display the best model
classes <- read.csv(classdoc)
correctClass <- data.frame()
correctClass <- cbind(classes[[3]])

LUL<-caret::confusionMatrix(table(correctClass,data))




n <- length(data)

clusterILen <- table(data)
clusterJLen <- table(correctClass)


n00 = 0
n11 = 0
n10 = 0
n01 = 0
mij = 0



for(i in 1:length(data)){
  for(j in i:length(data)){
    if(i == j){
      next;
    }
    if(data[i] == data[j] && correctClass[i] == correctClass[j]){
      n11 = n11 + 1;
    }
    if(data[i] == data[j] && correctClass[i] != correctClass[j]){
      n10 = n10 + 1;
    }
    if(data[i] != data[j] && correctClass[i] == correctClass[j]){
      n01 = n01 + 1;
    }
    if(data[i] != data[j] && correctClass[i] != correctClass[j]){
      n00 = n00 + 1;
    }
  
  }
}


ChiSquared <- function(LUL, clusterILen, clusterJLen){
  
  a<-as.matrix(LUL[[2]])
  chisq<-0
  for(i in 1:length(clusterILen)){
    for(j in 1:length(clusterJLen)){
      chisq<-((a[i,j] - (clusterILen[[i]]*clusterJLen[[j]]/n))^2)/(clusterILen[[i]]*clusterJLen[[j]]/n) + chisq
    }
  }
  return(chisq)  
}

GeneralRandIndex <- function(n11,n00,n) {
  return((2*(n11+n00))/(n*(n-1)))
}

AdjustedRandIndex <- function(clusterILen,clusterJLen, n, LUL) {
  
  
  t1<-0
  t2<-0
  t3<-0
  sera<-0
  
  for(i in 1:length(clusterILen)){
    t1 <- choose(clusterILen[[i]],2) + t1
  }
  
  for(i in 1:length(clusterJLen)){
    t2 <- choose(clusterJLen[[i]],2) + t2
  }
  
  t3<-(2*t1*t2)/(n*(n-1))
  
  for(i in 1:length(LUL[[2]])){
    sera <- choose(LUL[[2]][i],2) + sera
  }
  
  return((sera-t3)/((0.5*(t1+t2))-t3))
}

FowlkesMallowsIndex <- function(n11,n10,n01) {
  return((n11)/(sqrt(((n11+n10)*(n11+n01)))))
}


MirkinMetric <- function(n11,n00,n) {
  return(n*(n-1)*(1-GeneralRandIndex(n11,n00,n)))
}

MirkinMetric2 <- function(clusterILen,clusterJLen,LUL){
  a<-0
  b<-0
  c<-0
  
  for(i in 1:length(clusterILen)){
    a <- clusterILen[[i]]^2 + a
    
  }
  for(i in 1:length(clusterJLen)){
    b <- clusterJLen[[i]]^2 + b
  }
  
  for(i in 1:length(LUL[[2]])){
    c <- LUL[[2]][i]^2 + c
  }
  
  return(a+b-(2*c))
  
}

JaccardIndex <- function(n11,n10,n01) {
  return((n11)/(n11+n10+n01))
}

PartitionDifference <- function(n00) {
  return(n00)
}


fMeasure <- function(clusterILen,clusterJLen,n,LUL) {
  Fsum<-0
  for(i in 1:length(clusterILen)){
    littleF<-0;
    neededF<-0;
    
    
    for(j in 1:length(clusterJLen)){
      littleF <- (2*LUL[[2]][i,j])/(clusterILen[[i]]+clusterJLen[[j]])
      if(littleF > neededF){
        neededF<-littleF
      }
    }
    
    Fsum <- clusterILen[[i]] * neededF + Fsum
  
  }
  a<-Fsum / n
  return(a)
  
 
}



MaximumMatchMeasure <- function(n00) {
  return()
}


VanDongenMeasure <- function(n,LUL) {
  
  return()
}



NormalizedMutualInformationSG <- function(LUL,n,clusterILen,clusterJLen) {
  
  Hi <- 0
  for(i in 1:length(clusterILen)){
    Hi <- (clusterILen[[i]]/n)*log2((clusterILen[[i]]/n)) + Hi
  }
  Hi <- Hi*-1
  Hj <- 0
  for(i in 1:length(clusterJLen)){
    Hj <- (clusterJLen[[i]]/n)*log2((clusterJLen[[i]]/n)) + Hj
  }
  Hj <- Hj*-1
  
  a<-as.matrix(LUL[[2]])
  
  Ix<-0
  
  for(i in 1:length(clusterILen)){
    for(j in 1:length(clusterJLen)){
      if(a[i,j]==0){
        a[i,j]=0.01
      }
      Ix <- (a[i,j]/n) *( log2((a[i,j]/n)/((clusterILen[[i]]/n)*(clusterJLen[[j]]/n))) ) + Ix
    }
  }
  
  
  return((Ix)/sqrt(Hi * Hj))
}

NormalizedMutualInformationFJ <- function(LUL,n,clusterILen,clusterJLen) {
  Hi <- 0
  for(i in 1:length(clusterILen)){
    Hi <- (clusterILen[[i]]/n)*log2((clusterILen[[i]]/n)) + Hi
  }
  Hi <- Hi*-1
  Hj <- 0
  for(i in 1:length(clusterJLen)){
    Hj <- (clusterJLen[[i]]/n)*log2((clusterJLen[[i]]/n)) + Hj
  }
  Hj <- Hj*-1
  
  a<-as.matrix(LUL[[2]])
  Ix<-0
  for(i in 1:length(clusterILen)){
    for(j in 1:length(clusterJLen)){
      if(a[i,j]==0){
        a[i,j]=0.01
      }
      Ix <- (a[i,j]/n) *( log2((a[i,j]/n)/((clusterILen[[i]]/n)*(clusterJLen[[j]]/n))) ) + Ix
    }
  }
  
  return((2*Ix)/(Hi + Hj))
}


VariationInformation<- function(LUL,n,clusterILen,clusterJLen) {
  Hi <- 0
  for(i in 1:length(clusterILen)){
    Hi <- (clusterILen[[i]]/n)*log2((clusterILen[[i]]/n)) + Hi
  }
  Hi <- Hi*-1
  Hj <- 0
  for(i in 1:length(clusterJLen)){
    Hj <- (clusterJLen[[i]]/n)*log2((clusterJLen[[i]]/n)) + Hj
  }
  Hj <- Hj*-1
  
  a<-as.matrix(LUL[[2]])
  Ix<-0
  for(i in 1:length(clusterILen)){
    for(j in 1:length(clusterJLen)){
      if(a[i,j]==0){
        a[i,j]=0.01
      }
      Ix <- (a[i,j]/n) *( log2((a[i,j]/n)/((clusterILen[[i]]/n)*(clusterJLen[[j]]/n))) ) + Ix
    }
  }
  return(Hj+Hi - (2*Ix))
}

part1 <- c(data)
part2 <- c(correctClass)
exemple<-extCriteria(part1,part2,"all")
exemple[]

library(mclust)
adjustedRandIndex(part1, part2)

library(mcclust)
vi.dist(part1,part2, parts= FALSE, base = 2)


library(aricode)
abc <- clustComp(part1,part2)

library(MLmetrics)
F1_Score(y_pred = part1, y_true = part2)

var1<-AdjustedRandIndex(clusterILen,clusterJLen,n,LUL)
var2<-F1_Score(y_pred = part1, y_true = part2)
var3<-MirkinMetric(n11,n00,n)
var4<-GeneralRandIndex(n11,n00,n)
var5<-JaccardIndex(n11,n10,n01)
var6<-FowlkesMallowsIndex(n11,n10,n01)
var7<-abc[3]
var8<-abc[9]
var9<-vi.dist(part1,part2, parts= FALSE, base = 2)
var10<-ChiSquared(LUL,clusterILen,clusterJLen)

results<-data.frame(matrix(0, ncol = 12, nrow = 1))
results[1,1]<-var1
results[1,2]<-var2
results[1,3]<-var3
results[1,4]<-var4
results[1,5]<-var5
results[1,6]<-var6
results[1,7]<-var7
results[1,8]<-var8
results[1,9]<-var9
results[1,10]<-var10
results[1,11]<-dbi[1]
results[1,12]<-medsil


library(mclustcomp)
mclustcomp(part1, part2, types = "all", tversky.param = list())


library(mvtsplot)
library(aricode)

mvtsplottest <- read.csv("expmts.csv")
mvtsplot(mvtsplottest, norm = "global")

write.csv(results, file = "RESULTS.csv")



dtwTest <- read.csv("Libras_dis.csv")

table(part1,part2)





#############################################################################
#############################################################################
#############################################################################


tsTest <- read.csv("JapaneseVowels_dis.csv")
classdoc2 <- "Libras_class_15"


nrClasses <- 12 #insert both manually manually
nrTimeSteps <-7
nrSubjects <-640
counterList <-1
listOMatrixes <- 0


ez<-nrClasses*nrTimeSteps

for(i in 1:nrSubjects){
  counterCol <-1
  counterRow <-1

  
  x <- matrix(0, nrow = nrTimeSteps, ncol = nrClasses)


  for(j in 2:ez){
    x[counterRow, counterCol] <- tsTest[i,j]
    counterCol = counterCol + 1
    if(counterCol>nrClasses){
      counterCol<-1
      counterRow<- counterRow + 1
      
    }
  }
  
  x[counterRow, counterCol] <- tsTest[i,ez+1]
  listOMatrixes[counterList] <- list(x)
  counterList <- counterList + 1
}

library(dtwclust)

dba <- DBA(listOMatrixes, centroid = NULL, window.size = NULL, norm = "L1",
           max.iter = 20L, delta = 0.001, error.check = TRUE, trace = FALSE,
           mv.ver = "by-variable")







dtw(listOMatrixes)

xx<-matrix(0, nrow = length(listOMatrixes), ncol = length(listOMatrixes))
for(k in 1:length(listOMatrixes)){
  for(l in 1:length(listOMatrixes)){
   xx[k,l] =  GAK(listOMatrixes[[k]],listOMatrixes[[l]])
  }
}

nrClusters2<-9


library("cluster")
library("clusterSim")
library("clusterCrit")

pamMtx2<-pam(xx, nrClusters2, diss = inherits(xx, "dist"))
km  <- kmeans(xx,nrClusters2)

sil2 <- silhouette(pam(xx,nrClusters2))
sumsil<-0
for(i in 1:length(tsTest)){
  sumsil<-sumsil+sil2[i,3]
}
medsil2 <- sumsil/length(tsTest)


dbi2 <- index.DB(xx, pamMtx2$clustering, xx ,centrotypes = "medoids")

pamIndexVec2 <- pam(xx, nrClusters2, cluster.only = TRUE)
data <- data.frame()
data <- cbind(pamIndexVec2)
print(data)

Mode <- function(x) {
  ux <- unique(x)
  ux[which.max(tabulate(match(x, ux)))]
}




classes <- read.csv(classdoc2)
correctClass2 <- data.frame()
correctClass2 <- cbind(classes[[3]])

LUL<-caret::confusionMatrix(table(correctClass,data))


var1<-AdjustedRandIndex(clusterILen,clusterJLen,n,LUL)
var2<-F1_Score(y_pred = part1, y_true = part2)
var3<-MirkinMetric(n11,n00,n)
var4<-GeneralRandIndex(n11,n00,n)
var5<-JaccardIndex(n11,n10,n01)
var6<-FowlkesMallowsIndex(n11,n10,n01)
var7<-abc[3]
var8<-abc[9]
var9<-vi.dist(part1,part2, parts= FALSE, base = 2)
var10<-ChiSquared(LUL,clusterILen,clusterJLen)

results<-data.frame(matrix(0, ncol = 12, nrow = 1))
results[1,1]<-var1
results[1,2]<-var2
results[1,3]<-var3
results[1,4]<-var4
results[1,5]<-var5
results[1,6]<-var6
results[1,7]<-var7
results[1,8]<-var8
results[1,9]<-var9
results[1,10]<-var10
results[1,11]<-dbi[1]
results[1,12]<-medsil
