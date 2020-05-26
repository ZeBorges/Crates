package com.github.tDBN.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.tDBN.dbn.DynamicBayesNet;

public abstract class DBNUtils {
	// Smoothes variable probabilities according to their alphabet size and parameter ymin, normally ymin = 0.001
    // Receives a List of the probabilities of each variable at time slice (t+1) and parameter ymin
    public static void Smoothing(List<Double> probabilities, Double ymin, DynamicBayesNet dbn){
        int alphabet_size;
        for(int i=0; i < probabilities.size(); i++){ 
            //if(probabilities.get(i)==0){
            alphabet_size = dbn.getAttributes().get(i).size(); // i gives the index of the variable at slice (t+1)
            probabilities.set(i, (1 - alphabet_size * ymin)* probabilities.get(i) + ymin );
            //}
        }
    }
    
    public static <T> T mostCommon(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Entry<T, Integer> max = null;

        for (Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }
    

    
    public static Double getLL(DynamicBayesNet dbn1, int[] array) {
		List<Double> probabilities = StaticInferer.computeInference(dbn1, array);	
		DBNUtils.Smoothing(probabilities, 0.001, dbn1);
		
		List<Double> logs = new ArrayList<Double>(); // Stores the Log of the probability of each position (t+1)
        Double score = 0.0;
        for(int i=0; i < probabilities.size(); i++){
            logs.add(Math.log(probabilities.get(i)));
            score = score + logs.get(i);
        }
        
        return score;


	}
    
    public static Double [][] getLLN(List<DynamicBayesNet> dbnList, int nrChecks,int [][][] specEntriesMatrix){
    	

		Double [][] LL = new Double[dbnList.size()][dbnList.size()]; 
		Double [][] LLN = new Double[dbnList.size()][dbnList.size()]; 
		
		Double sumLL = 0.0;
		
		Double LLscoreTest[][] = new Double[dbnList.size()][nrChecks];
		Double sumLLs, medLLs = 0.0;
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		
		
		
		for(int i = 0; i < dbnList.size(); i++) {
			sumLLs = 0.0;
			medLLs = 0.0;
			indexList.clear();
			Double LLscore = 0.0;
			for(int l = 0; l < nrChecks; l++) {
				LLscoreTest[i][l] = DBNUtils.getLL(dbnList.get(i), specEntriesMatrix[i][l]);
				sumLLs += LLscoreTest[i][l];
			}
		    
			medLLs =  -100000000.0;//sumLLs/((double)nrChecks);
			
		    for(int checkIndex = 0; checkIndex < nrChecks; checkIndex++) {
		    	if(LLscoreTest[i][checkIndex] > medLLs) {
		    		indexList.add(checkIndex);
		    	}
		    }
			for(int j = 0; j < dbnList.size(); j++) {	
				try{
					if(indexList.size() % 2 == 0) {
						for(int k = 0; k < indexList.size()-1 ; k++) {
							if(k == 0) {
								LLscore = DBNUtils.getLL(dbnList.get(j), specEntriesMatrix[i][indexList.get(k)]); //LLscore of each DBN outputting other DBN's output
							}else {
								LLscore *= DBNUtils.getLL(dbnList.get(j), specEntriesMatrix[i][indexList.get(k)]);
							}
							
							
						}
					}else {
						for(int k = 0; k < indexList.size() ; k++) {
							if(k == 0) {
								LLscore = DBNUtils.getLL(dbnList.get(j), specEntriesMatrix[i][indexList.get(k)]); //LLscore of each DBN outputting other DBN's output
							}else {
								LLscore *= DBNUtils.getLL(dbnList.get(j), specEntriesMatrix[i][indexList.get(k)]);
							}
							
							
						}
					}
					LL[i][j] = LLscore;  ///Same column indicates how similar they are [!!!]
					//System.out.println("i:" + i + ", j:" + j);
				}catch(Exception e) {
					System.out.println(e);	
				}
				
					
			}
		}
		
 
		
		// Each Row of LL has the score of a single DBN evaluated along every observation,
		//with the i-th entry of the row corresponding to the i-th observation evaluated. 
		//nr Obs = nr DBN
			for(int i = 0; i < dbnList.size(); i++) {
				sumLL = 0.0;
				for(int j = 0; j < dbnList.size(); j++) {
					sumLL += LL[i][j];
				}
				
				for(int j = 0; j < dbnList.size(); j++) {
						//System.out.println("i:" + i + ", j:" + j + "sumLL:" + sumLL + "LL[i][j]:" + LL[i][j]);
						LLN[i][j]=LL[i][j]/sumLL;
				}
			}
		
			
    	
    	return LLN;
    }
    
}
