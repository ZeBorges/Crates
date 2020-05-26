package com.github.tDBN.tests;

import com.github.tDBN.dbn.NominalAttribute;
import com.github.tDBN.dbn.NumericAttribute;
import com.github.tDBN.dbn.Attribute;
import com.github.tDBN.dbn.BayesNet;
import com.github.tDBN.dbn.DynamicBayesNet;
import com.github.tDBN.dbn.Observations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.github.tDBN.utils.Edge;


public class GenerateData {
	
	
	public static void main(String[] args) {
	
		int nrTimeFrames = 10;
		int nrSubjects = 50;
		
		
		Attribute a1 = new NominalAttribute();
		a1.setName("X1");
		a1.add("yes");
		a1.add("no");
		a1.add("ok");

		Attribute a2 = new NumericAttribute();
		a2.setName("X2");
		a2.add("10");
		a2.add("20");

		Attribute a3 = new NumericAttribute();
		a3.setName("X3");
		a3.add("0");
		a3.add("1");
		
		Attribute a4 = new NominalAttribute();
		a4.setName("X4");
		a4.add("yes1");
		a4.add("no1");

		Attribute a5 = new NumericAttribute();
		a5.setName("X5");
		a5.add("101");
		a5.add("201");		

		Attribute a6 = new NumericAttribute();
		a6.setName("X6");
		a6.add("0");
		a6.add("1");
		
		Attribute a7 = new NumericAttribute();
		a7.setName("X7");
		a7.add("0");
		a7.add("1");
		
		Attribute a8 = new NumericAttribute();
		a8.setName("X8");
		a8.add("0");
		a8.add("1");
		
		Attribute a9 = new NumericAttribute();
		a9.setName("X9");
		a9.add("0");
		a9.add("1");
		
		Attribute a10 = new NumericAttribute();
		a10.setName("X10");
		a10.add("0");
		a10.add("1");
		
		Attribute a11 = new NominalAttribute();
		a11.setName("X11");
		a11.add("yes");
		a11.add("no");

		Attribute a12 = new NumericAttribute();
		a12.setName("X12");
		a12.add("10");
		a12.add("20");

		Attribute a13 = new NumericAttribute();
		a13.setName("X13");
		a13.add("0");
		a13.add("1");
		
		Attribute a14 = new NominalAttribute();
		a14.setName("X14");
		a14.add("yes1");
		a14.add("no1");

		Attribute a15 = new NumericAttribute();
		a15.setName("X15");
		a15.add("101");
		a15.add("201");		

		Attribute a16 = new NumericAttribute();
		a16.setName("X16");
		a16.add("0");
		a16.add("1");
		
		Attribute a17 = new NumericAttribute();
		a17.setName("X17");
		a17.add("0");
		a17.add("1");
		
		Attribute a18 = new NumericAttribute();
		a18.setName("X18");
		a18.add("0");
		a18.add("1");
		
		Attribute a19 = new NumericAttribute();
		a19.setName("X19");
		a19.add("0");
		a19.add("1");
		
		Attribute a20 = new NumericAttribute();
		a20.setName("X20");
		a20.add("0");
		a20.add("1");

		
		
		List<Attribute> a = Arrays.asList(a1,a2,a3,a4,a5);
		List<Attribute> b = Arrays.asList(a1,a2,a3,a4,a5);
		List<Attribute> c = Arrays.asList(a1,a2,a3,a4,a5);
		List<Attribute> d = Arrays.asList(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10);
		List<Attribute> e = Arrays.asList(a1,a2,a3,a4,a5);
		List<Attribute> f = Arrays.asList(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10);
		
		
		Edge e01 = new Edge(0,1);
		Edge e12 = new Edge(1,2);
		Edge e23 = new Edge(2,3);
		Edge e34 = new Edge(3,4);
		Edge e11 = new Edge(1,1);
		Edge e22 = new Edge(2,2);
		Edge e00 = new Edge(0,0);
		Edge e33 = new Edge(3,3);
		Edge e44 = new Edge(4,4);
		Edge e41 = new Edge(4,1);
		
		Edge e55 = new Edge(5,5);
		Edge e66 = new Edge(6,6);
		Edge e77 = new Edge(7,7);
		Edge e88 = new Edge(8,8);
		Edge e99 = new Edge(9,9);
		Edge e45 = new Edge(4,5);
		Edge e56 = new Edge(5,6);
		Edge e67 = new Edge(6,7);
		Edge e78 = new Edge(7,8);
		Edge e89 = new Edge(8,9);
		Edge e91 = new Edge(9,1);
		
		
		
		
		Edge f10 = new Edge(1,0);
		Edge f21 = new Edge(2,1);
		Edge f32 = new Edge(3,2);
		Edge f43 = new Edge(4,3);
		Edge f41 = new Edge(4,1);
		Edge f12 = new Edge(1,2);
		Edge f23 = new Edge(2,3);
		Edge f34 = new Edge(3,4);
		Edge f01 = new Edge(0,1);
		Edge f14 = new Edge(1,4);
		
		Edge f54 = new Edge(5,4);
		Edge f65 = new Edge(6,5);
		Edge f76 = new Edge(7,6);
		Edge f87 = new Edge(8,7);
		Edge f98 = new Edge(9,8);
		Edge f45 = new Edge(4,5);
		Edge f56 = new Edge(5,6);
		Edge f67 = new Edge(6,7);
		Edge f78 = new Edge(7,8);
		Edge f89 = new Edge(8,9);
		Edge f92 = new Edge(9,2);
		Edge f29 = new Edge(2,9);
		
		
		Edge g00 = new Edge(0,0);
		Edge g11 = new Edge(1,1);
		Edge g120 = new Edge(1,2);
		Edge g23 = new Edge(2,3);
		Edge g34 = new Edge(3,4);
		Edge g44 = new Edge(4,4);
		Edge g011 = new Edge(0,1);
		Edge g121 = new Edge(1,2);
		Edge g241 = new Edge(2,4);
		Edge g231 = new Edge(2,3);
		Edge g301 = new Edge(3,0);
		
		Edge h02 = new Edge(0,2);
		Edge h14 = new Edge(1,4);
		Edge h00 = new Edge(0,0);
		Edge h01 = new Edge(0,1);
		Edge h11 = new Edge(1,1);
		Edge h22 = new Edge(2,2);
		Edge h23 = new Edge(2,3);
		Edge h34 = new Edge(3,4);
		Edge h44 = new Edge(4,4);
		Edge h42 = new Edge(4,2);
		Edge h30 = new Edge(3,0);
		
		
		
		List<Edge> prior = Arrays.asList(e01,e12,e23,e34);
		List<Edge> prior2 = Arrays.asList(f10,f21,f32,f43);
		List<Edge> prior3 = Arrays.asList(g120);
		List<Edge> prior4 = Arrays.asList(e01,e12,e23,e34,e45,e56,e67,e78,e89);
		List<Edge> prior5 = Arrays.asList(h02, h14);
		List<Edge> prior6 = Arrays.asList(f98,f87,f76,f65,f54,f43,f32,f21,f10);
		
		BayesNet b0 = new BayesNet(a, prior);
		BayesNet b1 = new BayesNet(b, prior2);
		BayesNet b2 = new BayesNet(c, prior3);
		BayesNet b3 = new BayesNet(d, prior4);
		BayesNet b4 = new BayesNet(e, prior5);
		BayesNet b5 = new BayesNet(f, prior6);
		
		
		b0.generateParameters();
		b1.generateParameters();
		b2.generateParameters();
		b3.generateParameters();
		b4.generateParameters();
		b5.generateParameters();
		
		
		
		List<Edge> inter = Arrays.asList(e00, e11, e22, e33, e44);
		List<Edge> intra = Arrays.asList(e41);
		
		
		List<Edge> inter2 = Arrays.asList(f01, f12, f23, f34, f41);
		List<Edge> intra2 = Arrays.asList(f14);
		
		List<Edge> inter3 = Arrays.asList(g00, g11, g23, g34, g44);
		List<Edge> intra3 = Arrays.asList(g011, g121, g241, g231, g301);
		
		
		List<Edge> inter4 = Arrays.asList(e00, e11, e22, e33, e44, e55, e66, e77, e88, e99);
		List<Edge> intra4 = Arrays.asList(e91);
		
		
		List<Edge> inter5 = Arrays.asList(h00,h11,h01,h22,h23,h34,h44);
		List<Edge> intra5 = Arrays.asList(h30,h42);

		List<Edge> inter6 = Arrays.asList(f01,f12,f23,f34,f45,f56,f67,f78,f89);
		List<Edge> intra6 = Arrays.asList(f29);
		
		BayesNet bt = new BayesNet(a, intra, inter);
		bt.generateParameters();
		
		BayesNet bt2 = new BayesNet(b, intra2, inter2);
		bt2.generateParameters();
		
		BayesNet bt3 = new BayesNet(c, inter3, intra3);
		bt3.generateParameters();
		
		BayesNet bt4 = new BayesNet(d, inter4, intra4);
		bt4.generateParameters();
		
		BayesNet bt5 = new BayesNet(e, inter5, intra5);
		bt5.generateParameters();
		
		BayesNet bt6 = new BayesNet(f, inter6, intra6);
		bt6.generateParameters();
		
		List<BayesNet> btList = new ArrayList<BayesNet>();
		for(int i = 0; i < nrTimeFrames; i++) {
			btList.add(bt);
		}
		
		List<BayesNet> btList2 = new ArrayList<BayesNet>();
		for(int i = 0; i < nrTimeFrames; i++) {
			btList2.add(bt2);
		}
		
		List<BayesNet> btList3 = new ArrayList<BayesNet>();
		for(int i = 0; i < nrTimeFrames; i++) {
			btList3.add(bt3);
		}
		
		List<BayesNet> btList4 = new ArrayList<BayesNet>();
		for(int i = 0; i < nrTimeFrames; i++) {
			btList4.add(bt4);
		}
		
		List<BayesNet> btList5 = new ArrayList<BayesNet>();
		for(int i = 0; i < nrTimeFrames; i++) {
			btList5.add(bt5);
		}
		
		List<BayesNet> btList6 = new ArrayList<BayesNet>();
		for(int i = 0; i < nrTimeFrames; i++) {
			btList6.add(bt6);
		}
		
		
		DynamicBayesNet dbn1 = new DynamicBayesNet(a, b0, btList);	
		DynamicBayesNet dbn2 = new DynamicBayesNet(b, b1, btList2);	
		DynamicBayesNet dbn3 = new DynamicBayesNet(c, b2, btList3);	
		DynamicBayesNet dbn4 = new DynamicBayesNet(d, b3, btList4);	
		DynamicBayesNet dbn5 = new DynamicBayesNet(e, b4, btList5);
		DynamicBayesNet dbn6 = new DynamicBayesNet(f, b5, btList6);
		
		
		Observations o = dbn1.generateObservations(nrSubjects);
		Observations o2 = dbn2.generateObservations(nrSubjects);
		Observations o3 = dbn3.generateObservations(nrSubjects);
		Observations o4 = dbn4.generateObservations(nrSubjects);
		Observations o5 = dbn5.generateObservations(nrSubjects);
		Observations o6 = dbn6.generateObservations(nrSubjects);
		
		o.writeToFile("output");
		o2.writeToFile("output2");
		o3.writeToFile("output3");
		o4.writeToFile("output4");
		o5.writeToFile("output5");
		o6.writeToFile("output6");
		
		
		System.out.println("1");
	}

	
	
}
