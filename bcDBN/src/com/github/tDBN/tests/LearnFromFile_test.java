package com.github.tDBN.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


import com.github.tDBN.dbn.NominalAttribute;
import com.github.tDBN.dbn.NumericAttribute;
import com.github.tDBN.dbn.Attribute;
import com.github.tDBN.dbn.BayesNet;
import com.github.tDBN.dbn.Configuration;
import com.github.tDBN.dbn.DynamicBayesNet;
import com.github.tDBN.dbn.LLScoringFunction;
import com.github.tDBN.dbn.MDLScoringFunction;
import com.github.tDBN.dbn.ScoringFunction;
import com.github.tDBN.dbn.Observations;
import com.github.tDBN.dbn.Scores;
import com.github.tDBN.utils.Utils;

public class LearnFromFile_test {
	@SuppressWarnings({ "static-access" })
	public static void main(String[] args) {


		// create Options object
		Options options = new Options();


		Option inputFile = Option.builder("i")
				.longOpt("file")
				.desc("Input CSV file to be used for network learning.")
				.hasArg()
				.argName("file")
				.build();

		Option numParents = Option.builder("p")
				.longOpt("numParents")
				.desc("Maximum number of parents from preceding time-slice(s).")
				.hasArg()
				.argName("int")
				.build();

		Option outputFile = Option.builder("o")
				.longOpt("outputFile")
				.desc("Writes output to <file>. If not supplied, output is written to terminal.")
				.hasArg()
				.argName("file")
				.build();

		Option rootNode = Option.builder("r")
				.longOpt("root")
				.desc("Root node of the intra-slice tree. By default, root is arbitrary.")
				.hasArg()
				.argName("int")
				.build();

		Option scoringFunction = Option.builder("s")
				.longOpt("scoringFunction")
				.desc("Scoring function to be used, either MDL or LL. MDL is used by default.")
				.hasArg()
				.build();

		Option dotFormat = Option.builder("d")
				.longOpt("dotFormat")
				.desc("Outputs network in dot format, allowing direct redirection into Graphviz to visualize the graph.")
				.build();


		Option compact = Option.builder("c")
				.longOpt("compact")
				.desc("Outputs network in compact format, omitting intra-slice edges. Only works if specified together with -d and with --markovLag 1.")
				.build();

		Option maxMarkovLag = Option.builder("m")
				.longOpt("markovLag")
				.desc("Maximum Markov lag to be considered, which is the longest distance between connected time-slices. Default is 1, allowing edges from one preceding slice.")
				.hasArg()
				.argName("int")
				.build();

		Option spanningTree = Option.builder("sp")
				.longOpt("nonStationary")
				.desc("Forces intra-slice connectivity to be a tree instead of a forest, eventually producing a structure with a lower score.")
				.build();

		Option nonStationary = Option.builder("ns")
				.longOpt("nonStationary")
				.desc("Learns a non-stationary network (one transition network per time transition). By default, a stationary DBN is learnt.")
				.build();

		Option parameters= Option.builder("pm")
				.longOpt("parameters")
				.desc("Learns and outputs the network parameters.")
				.build();


		Option bcDBN= Option.builder("bcDBN")
				.longOpt("bcDBN")
				.desc("Learns a bcDBN structure.")
				.build();
		
		Option cDBN= Option.builder("cDBN")
				.longOpt("cDBN")
				.desc("Learns a cDBN structure.")
				.build();

		Option intra_in= Option.builder("ind")
				.longOpt("intra_in")
				.desc("In-degree of the intra-slice network")
				.hasArg()
				.argName("int")
				.build();

		options.addOption(inputFile);
		options.addOption(numParents);
		options.addOption(outputFile);
		options.addOption(rootNode);
		options.addOption(scoringFunction);
		options.addOption(dotFormat);
		options.addOption(compact);
		options.addOption(maxMarkovLag);
		options.addOption(spanningTree);
		options.addOption(nonStationary);
		options.addOption(parameters);
		options.addOption(bcDBN);
		options.addOption(cDBN);
		options.addOption(intra_in);

		CommandLineParser parser = new DefaultParser();
		try {	
			
			CommandLine cmd = parser.parse(options, args);


			boolean verbose = !cmd.hasOption("d");
			boolean stationary = !cmd.hasOption("nonStationary");
			boolean spanning = cmd.hasOption("spanning");
			boolean printParameters = cmd.hasOption("parameters");
			boolean is_bcDBN = cmd.hasOption("bcDBN");
			boolean is_cDBN = cmd.hasOption("cDBN");
			int intra_ind = Integer.parseInt(cmd.getOptionValue("ind","2"));

			// TODO: check sanity
			int markovLag = Integer.parseInt(cmd.getOptionValue("m", "1"));
			int root = Integer.parseInt(cmd.getOptionValue("r", "-1"));

			
			Observations o1 = new Observations(cmd.getOptionValue("i"), markovLag);
			List<Observations> oList = new ArrayList<Observations>();
			
			int [][][] obsMatrixTest = o1.getObservationsMatrix();
			
			int nrSubjects = obsMatrixTest[0].length-1; //Stationary -> [0]
			int nrTimeSlices = obsMatrixTest.length;
			int nrAtts = o1.getAttributes().size();
					
			
			int nrChecks = nrTimeSlices; 
			int [][][] specEntriesMatrix = new int [nrSubjects][nrChecks][nrAtts*2];
			
			for (int i = 0; i < nrSubjects; i++) {
				for(int LUL = 0; LUL < nrChecks; LUL++) {
					System.arraycopy(obsMatrixTest[LUL][i], 0,specEntriesMatrix[i][LUL], 0, nrAtts * 2);
				}
			}
			
				for(int i = 0; i < nrSubjects ; i++) {
					
					
					int[][][] auxMatrix = new int[nrTimeSlices][1][nrAtts*2]; // Complete info about a patient
					for(int j=0; j<nrTimeSlices; j++) {
						System.arraycopy(obsMatrixTest[j][i], 0, auxMatrix[j][0], 0, nrAtts * 2);
					}
					Observations o2 = new Observations(o1, auxMatrix);  // An observation corresponding to a singular patient
					oList.add(o2); // List of patients
				}
				
				List<DynamicBayesNet> dbnList = new ArrayList<DynamicBayesNet>();
				
				List<Observations> oList2 = oList;
				//List<Observations> oList2 = SeparateData.separate500(oList, nrAtts, nrTimeSlices); //-> teste4, max = 1 , 171
				//List<Observations> oList2 = SeparateData.separate15(oList, nrAtts, nrTimeSlices); // -> expCSV, max = 2 , 500~~
				//List<Observations> oList2 = SeparateData.separate100(oList, nrAtts, nrTimeSlices); // teste3, max = 1 , 178 // 731 -> 600 3 dif
			
				
				
				for(int i=0; i<oList2.size();i++) {
					Observations o = oList2.get(i);
					
					
					System.out.println(i);
					Scores s = new Scores(o, Integer.parseInt(cmd.getOptionValue("p")), stationary, verbose);

					
					ScoringFunction sf;
		
				
					if (cmd.hasOption("s") && cmd.getOptionValue("s").equalsIgnoreCase("ll")) {
						sf = new LLScoringFunction();
						//System.out.println(cmd.getOptionValue("s"));
						if (verbose)
							System.out.println("Evaluating network with LL score.");
						s.evaluate(new LLScoringFunction());
					} else {
						sf = new MDLScoringFunction();
						//System.out.println(cmd.getOptionValue("s"));
						if (verbose)
							System.out.println("Evaluating network with MDL score.");
						s.evaluate(new MDLScoringFunction());
					}
		
		
		
					// if (verbose)
					// System.out.println(s);
		
					DynamicBayesNet dbn;
		
					if (verbose) {
						if (cmd.hasOption("r"))
							System.out.println("Root node specified: " + root);
						if (spanning)
							System.out.println("Finding a maximum spanning tree.");
						else
							System.out.println("Finding a maximum branching.");
					}
		
		
					if(is_bcDBN) {
						System.out.println("Learning bcDBN networks.");
						dbn=s.to_bcDBN(sf,intra_ind);
		
					}
					
					else {
						
						if(is_cDBN) {
							System.out.println("Learning cDBN networks.");
							dbn=s.to_cDBN(sf,intra_ind);}
						
						
					else {
						System.out.println("Learning tDBN networks.");
						dbn = s.toDBN(root, spanning);
		
					}
		
						
					}
		
		
					if (printParameters)
						dbn.learnParameters(o);
		
					String output;
		
					dbnList.add(dbn);
					
					if (cmd.hasOption("d")) {
						if (cmd.hasOption("c") && markovLag == 1)
							output = dbn.toDot(true);
						else
							output = dbn.toDot(false);
					} else
						output = dbn.toString(printParameters);
		
					if (cmd.hasOption("o")) {
						try {
							Utils.writeToFile(cmd.getOptionValue("o"), output);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					} else {
						if (verbose) {
							System.out.println();
							System.out.println("-----------------");
							System.out.println();
						}
						System.out.println(output);
					}
				}
				

				
				//////////////////
				
				long startTime = System.nanoTime();
				//code
				
				System.out.println("1");
				
				Double [][] distanceMatrix = new Double[dbnList.size()][dbnList.size()];
				
				int distanceFlag = 2; // TVD == 1 KLD == 2 HD == 3 BTCD == 4 MHLD == 5
				
				if(distanceFlag == 1) {
					List<int[]> comboList = new ArrayList<int[]>();
					
					int[] data = new int[nrAtts * 2];
					int[] min = new int[nrAtts * 2];
					int[] max = new int[nrAtts * 2];
					
					
					
					
					Arrays.fill(min, 0);
					Arrays.fill(data, 0);
					Arrays.fill(max, 1);
					
					for(int fillerIndex = 0; fillerIndex < nrAtts*2; fillerIndex++) {
						for(int attributeIndex = 0; attributeIndex < nrAtts; attributeIndex++) {
							max[fillerIndex] = dbnList.get(0).getAttributes().get(attributeIndex).size() - 1;

						}
					}
					
					
					comboList.add(data);
					
					Combinations.countCombos(max, min, data, comboList);
					
	
					for(int i = 0; i < dbnList.size(); i++) {
						for(int j = i; j < dbnList.size(); j++) {
							try{
								if(i == j) {
									distanceMatrix[i][j] = 0.0;
									continue;
								}
								distanceMatrix[i][j] =  TotalVariationDistance.computeDistance(dbnList.get(i), dbnList.get(j), comboList);
								distanceMatrix[j][i] = distanceMatrix[i][j];
							}catch(Exception e) {
								System.out.println(e);	
							}
								
						}
					}
				}else if(distanceFlag == 2){
					
					Double [][] LLN = new Double[dbnList.size()][dbnList.size()];
					LLN = DBNUtils.getLLN(dbnList, nrChecks, specEntriesMatrix);
					
					for(int i = 0; i < dbnList.size(); i++) {
						for(int j = 0; j < dbnList.size(); j++) {
							double x = 0, y = 0;
							for(int LUL = 0; LUL < dbnList.size(); LUL++) {
								x += LLN[i][LUL] * (Math.log(LLN[i][LUL] / LLN [j][LUL]));
								y += LLN[j][LUL] * (Math.log(LLN[j][LUL] / LLN [i][LUL]));
							}
							distanceMatrix[i][j] = (x + y)/2;
						}
					}
	
				}else if(distanceFlag == 3) {
					Double [][] LLN = new Double[dbnList.size()][dbnList.size()];
					LLN = DBNUtils.getLLN(dbnList, nrChecks, specEntriesMatrix);
					double sqrt2 = 1/Math.sqrt(2);
					
					
					for(int i = 0; i < dbnList.size(); i++) {
						for(int j = 0; j < dbnList.size(); j++) {
							double sumHL = 0.0;
							double firstVal, secondVal = 0.0;
							
							
							for(int LUL = 0; LUL < dbnList.size(); LUL++) {
								firstVal = Math.sqrt(LLN[i][LUL]);
								secondVal = Math.sqrt(LLN[j][LUL]);
								sumHL += Math.pow(firstVal - secondVal, 2);
							}
							sumHL = (1/sqrt2) * Math.sqrt(sumHL);
							distanceMatrix[i][j] = sumHL;
						}
					}
					
				}else if(distanceFlag == 4) {
					Double [][] LLN = new Double[dbnList.size()][dbnList.size()];
					LLN = DBNUtils.getLLN(dbnList, nrChecks, specEntriesMatrix);
	
					
					
					for(int i = 0; i < dbnList.size(); i++) {
						for(int j = 0; j < dbnList.size(); j++) {
							double sumHL = 0.0;
							double firstVal, secondVal = 0.0;
							
							for(int LUL = 0; LUL < dbnList.size(); LUL++) {
								firstVal = LLN[i][LUL];
								secondVal = LLN[j][LUL];
								sumHL += Math.sqrt(firstVal * secondVal);
							}
							sumHL = (-1)*Math.log(sumHL);
							distanceMatrix[i][j] = sumHL;
							
							

						}
					}
					
				}else if(distanceFlag == 5) {
		
					
				}
				
				long endTime = System.nanoTime();
				System.out.println("Took "+(endTime - startTime) + " ns"); 
				
				 try {
					 	File file = new File("DISTANCEM.csv");  
				        if ( !file.exists() )
				            file.createNewFile();

				        FileWriter fw = new FileWriter(file);
				        BufferedWriter bw = new BufferedWriter( fw ); 
					 	//BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

					 	for (int i = 0; i < distanceMatrix.length; i++) {
					 		if(i == distanceMatrix.length -1) {
					 			bw.write("X"+i);
					 		}
					 		else {
					 			bw.write("X"+i + ",");
					 		}
					 		
			
					 	}
					 		bw.write("\n");
					 
				        for (int i = 0; i < distanceMatrix.length; i++) {
				        	for (int j = 0; j < distanceMatrix[i].length; j++) {
				        	    bw.write(distanceMatrix[i][j] + ((j == distanceMatrix[i].length-1) ? "" : ","));
				        	}
				        	if(i == distanceMatrix.length - 1 && distanceMatrix[i].length == distanceMatrix.length) {
				        		break;
				        	}
				            bw.newLine();
				        }
				        
				        bw.flush();
				    } catch (IOException e) {}
				 
				 
				    
				///////////////////////
				
				
				System.out.println("0");

				
				
				
				
				
				
				
			} catch (ParseException e) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("cDBN", options);
				System.out.println(e);
			} 
	
	}

}
