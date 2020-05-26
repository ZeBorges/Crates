package com.github.tDBN.tests;

import java.util.List;

import com.github.tDBN.dbn.DynamicBayesNet;

public abstract class DKL {
	public static Double computeDistance(double[] p1, double[] p2) {
		
		final double log2 = Math.log(2);
		
		double klDiv = 0.0;

	      for (int i = 0; i < p1.length; ++i) {
	        if (p1[i] == 0) { continue; }
	        if (p2[i] == 0.0) { continue; } // Limin

	      klDiv += p1[i] * Math.log( p1[i] / p2[i] );
	      }

	      return klDiv / log2;
		
	}
	
	
	
}
