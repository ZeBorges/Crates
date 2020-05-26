package com.github.tDBN.tests;

import java.util.Arrays;
import java.util.List;

public abstract class Combinations {

	public static void makeCombinations(int[] min, int[] max, int[] data, List<int[]> comboList) {
		int[] dataInst = data.clone();
		int[] dataInst2 = data.clone();
		int i = 0;
		boolean finished = false;
		while(i <= data.length && !finished) {
			dataInst = dataInst2.clone();
			if(dataInst[i] < max[i]) {
				dataInst[i]++;
				comboList.add(dataInst);
				dataInst2 = dataInst.clone();
				
				finished = true;
								
			}else{
				dataInst[i] = min[i];
				dataInst2 = dataInst.clone();
				
				i++;	
			}
		}
	}
	
	public static void countCombos(int[] max, int[] min, int[] data, List<int[]> comboList) {
		int k = 0;
		while(!Arrays.equals(comboList.get(k), max)) {
			makeCombinations(min, max, comboList.get(k), comboList);
			k++;
		}
	}
	
}
