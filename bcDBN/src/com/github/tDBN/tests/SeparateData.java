package com.github.tDBN.tests;

import java.util.ArrayList;
import java.util.List;

import com.github.tDBN.dbn.Observations;

public abstract class SeparateData {

	public static List<Observations> separate500(List<Observations> oList, int nrAtts, int nrTimeSlices){
		
		List<Observations> oList2 = new ArrayList<Observations>();
	
			for(int i = 0; i < oList.size(); i++) {
				Observations o10 = oList.get(i);
				int[][][] auxMatrix2 = o10.getObservationsMatrix();
				int[][][] auxMatrix = new int[25][20][nrAtts*2];
				
				
				for(int k = 0; k < 25; k++) {
					System.arraycopy(auxMatrix2[k][0], 0, auxMatrix[k][0], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+25][0], 0, auxMatrix[k][1], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+50][0], 0, auxMatrix[k][2], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+75][0], 0, auxMatrix[k][3], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+100][0], 0, auxMatrix[k][4], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+125][0], 0, auxMatrix[k][5], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+150][0], 0, auxMatrix[k][6], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+175][0], 0, auxMatrix[k][7], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+200][0], 0, auxMatrix[k][8], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+225][0], 0, auxMatrix[k][9], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+250][0], 0, auxMatrix[k][10], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+275][0], 0, auxMatrix[k][11], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+300][0], 0, auxMatrix[k][12], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+325][0], 0, auxMatrix[k][13], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+350][0], 0, auxMatrix[k][14], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+375][0], 0, auxMatrix[k][15], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+400][0], 0, auxMatrix[k][16], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+425][0], 0, auxMatrix[k][17], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+450][0], 0, auxMatrix[k][18], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+475][0], 0, auxMatrix[k][19], 0, nrAtts * 2);
				}			
				Observations o3 = new Observations(o10, auxMatrix);
				oList2.add(o3);
			}
			return oList2;
		}
		
		public static List<Observations> separate15(List<Observations> oList, int nrAtts, int nrTimeSlices){
			
			List<Observations> oList2 = new ArrayList<Observations>();
		
			for(int i = 0; i < oList.size(); i++) {
				Observations o10 = oList.get(i);
				int[][][] auxMatrix2 = o10.getObservationsMatrix();
				int[][][] auxMatrix = new int[5][3][nrAtts*2];
				
				
				for(int k = 0; k < 5; k++) {
					System.arraycopy(auxMatrix2[k][0], 0, auxMatrix[k][0], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+5][0], 0, auxMatrix[k][1], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+10][0], 0, auxMatrix[k][2], 0, nrAtts * 2);

				
				}			
				Observations o3 = new Observations(o10, auxMatrix);
				oList2.add(o3);
			}
		
		return oList2;
	}
		
		
		public static List<Observations> separate100(List<Observations> oList, int nrAtts, int nrTimeSlices){
			
		List<Observations> oList2 = new ArrayList<Observations>();
		for(int i = 0; i < oList.size(); i++) {
			Observations o10 = oList.get(i);
			int[][][] auxMatrix2 = o10.getObservationsMatrix();
			int[][][] auxMatrix = new int[5][20][nrAtts*2];
			
			
			for(int k = 0; k < 5; k++) {
				System.arraycopy(auxMatrix2[k][0], 0, auxMatrix[k][0], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+5][0], 0, auxMatrix[k][1], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+10][0], 0, auxMatrix[k][2], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+15][0], 0, auxMatrix[k][3], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+20][0], 0, auxMatrix[k][4], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+25][0], 0, auxMatrix[k][5], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+30][0], 0, auxMatrix[k][6], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+35][0], 0, auxMatrix[k][7], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+40][0], 0, auxMatrix[k][8], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+45][0], 0, auxMatrix[k][9], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+50][0], 0, auxMatrix[k][10], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+55][0], 0, auxMatrix[k][11], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+60][0], 0, auxMatrix[k][12], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+65][0], 0, auxMatrix[k][13], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+70][0], 0, auxMatrix[k][14], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+75][0], 0, auxMatrix[k][15], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+80][0], 0, auxMatrix[k][16], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+85][0], 0, auxMatrix[k][17], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+90][0], 0, auxMatrix[k][18], 0, nrAtts * 2);
				System.arraycopy(auxMatrix2[k+95][0], 0, auxMatrix[k][19], 0, nrAtts * 2);
			}			
			Observations o3 = new Observations(o10, auxMatrix);
			oList2.add(o3);
		}
		return oList2;
		
		}
		
		
		public static List<Observations> separate600(List<Observations> oList, int nrAtts, int nrTimeSlices){
			
			List<Observations> oList2 = new ArrayList<Observations>();
			for(int i = 0; i < oList.size(); i++) {
				Observations o10 = oList.get(i);
				int[][][] auxMatrix2 = o10.getObservationsMatrix();
				int[][][] auxMatrix = new int[nrTimeSlices-1][5][nrAtts*2];
				
				
				for(int k = 0; k < 50; k++) {
					System.arraycopy(auxMatrix2[k][0], 0, auxMatrix[k][0], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+10][0], 0, auxMatrix[k][1], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+20][0], 0, auxMatrix[k][2], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+30][0], 0, auxMatrix[k][3], 0, nrAtts * 2);
					System.arraycopy(auxMatrix2[k+40][0], 0, auxMatrix[k][4], 0, nrAtts * 2);
					System.out.println(k);
			
				}			
				Observations o3 = new Observations(o10, auxMatrix);
				oList2.add(o3);
			}
			return oList2;
			
			}
		
}
