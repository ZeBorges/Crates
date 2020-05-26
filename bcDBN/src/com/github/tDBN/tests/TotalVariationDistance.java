package com.github.tDBN.tests;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.github.tDBN.dbn.DynamicBayesNet;



public abstract class TotalVariationDistance {
	
	
	public static Double computeOdds(DynamicBayesNet dbn, List<int[]> comboList) {	
		
		Double prob = 0.0;	
		int nrAtts = dbn.getAttributes().size();
		int[] auxData = new int[nrAtts*2];
		
		for(int i = 0; i < comboList.size(); i++) {
			
			auxData = comboList.get(i).clone();
			
			List<Double> probList1 = StaticInferer.computeInference(dbn, auxData);

		
			for(int j = 0; j < probList1.size(); j++) {
				if(j == 0) {
					prob = probList1.get(j);
				}else {
					prob *= probList1.get(j);
				}
				
			}
		}
			return prob;
		
	}
	
	
	public static Double computeDistance(DynamicBayesNet dbn1, DynamicBayesNet dbn2, List<int[]> comboList) {
		Double distance = 0.0;
		Double maxDistance = -1.0;
		
		Double testDistance = 0.0;
		
		Double prob1 = 0.0;
		Double prob2 = 0.0;
		
		List<Double> distancesList = new ArrayList<Double>();
		Double medianList[]= new Double[comboList.size()];
		Double medianDistance = 0.0;
		int nrAtts = dbn1.getAttributes().size();

		int[] auxData = new int[nrAtts*2];
		
		for(int i = 0; i < comboList.size(); i++) {
			
			auxData = comboList.get(i).clone();
			
			List<Double> probList1 = StaticInferer.computeInference(dbn1, auxData);
			List<Double> probList2 = StaticInferer.computeInference(dbn2, auxData);
			
			DBNUtils.Smoothing(probList1, 0.001, dbn1);
			DBNUtils.Smoothing(probList2, 0.001, dbn2);
			
			
			
			for(int j = 0; j < probList1.size(); j++) {
				if(j == 0) {
					prob1 = probList1.get(j);
					prob2 = probList2.get(j);
				}else {
					prob1 *= probList1.get(j);
					prob2 *= probList2.get(j);
				}
				
			}
			
			distance = Math.abs(prob1-prob2);
			
			
			
			

			
			distancesList.add(distance);
			
			
			
			
			testDistance += distance;
			medianList[i] = distance;
			
			if(distance > maxDistance) {
				maxDistance = distance;
			}
		}
	Arrays.sort(medianList);
	medianDistance = medianList[comboList.size()/2]; //mediana
	
	
	testDistance = testDistance / comboList.size(); //media
	
	
	Double modeDistance = DBNUtils.mostCommon(distancesList);
	
	return modeDistance;
		
	}
	
	
}
