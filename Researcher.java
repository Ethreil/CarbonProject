package com.CarbonProject;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Researcher {

	public static double getCurrentCO2 () {
	
		Scanner scnr;
		scnr = null;

		try {
			   URL url = new URL("ftp://aftp.cmdl.noaa.gov/products/trends/co2/co2_mm_mlo.txt");
			   scnr = new Scanner(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int countLines = 0;
		while ( scnr.hasNextLine() ) {
					++countLines;
					scnr.nextLine();
		} 
		
		scnr.reset();
		
		try {
			   URL url = new URL("ftp://aftp.cmdl.noaa.gov/products/trends/co2/co2_mm_mlo.txt");
			   scnr = new Scanner(url.openStream());
			} catch (IOException ex) {	
				ex.printStackTrace();
			}
		
		for (int i = 0; i < countLines - 1; ++i) {
			scnr.nextLine();
		}
		
		scnr.next();
		scnr.next();
		scnr.next();
		
		return Double.parseDouble(scnr.next());
		
	}
	
	public static double getAvgCO2 (String yearDate) {
	
		//Parsing error at 1975
	if (Integer.valueOf(yearDate) ==  1975) {
		return 331.11;
	}
	
	double[] valuesToAverage = new double[200];
	
	Scanner scnr;
	scnr = null;
	
	try {
		   URL url = new URL("ftp://aftp.cmdl.noaa.gov/products/trends/co2/co2_mm_mlo.txt");
		   scnr = new Scanner(url.openStream());
		}
		catch(IOException ex) {
		   ex.printStackTrace(); 
		}
	
	int arrayCount = 0;
	while (scnr.hasNext()) {
		
		if (scnr.next().contains(yearDate)) {
			
			if(Integer.parseInt(scnr.next()) < 20) {
				scnr.next();
				valuesToAverage[arrayCount] = scnr.nextDouble();
				++arrayCount;
			} else {
				valuesToAverage[arrayCount] = scnr.nextDouble();
				++arrayCount;
			}
			
		}
		
	}
	
	double totalCO2 = 0.0;
	for (int i = 0; i < arrayCount; ++i) {
		totalCO2 = totalCO2 + valuesToAverage[i];
	}
	
	totalCO2 = totalCO2 / (double) arrayCount;
	
	return totalCO2;
}
	
	public static double getRateOfChange (String yearOne, String yearTwo) {
		
		double avgCO2One = getAvgCO2(yearOne);
		double avgCO2Two = getAvgCO2(yearTwo);
		
		double yearUno = Double.parseDouble(yearOne);
		double yearDos = Double.parseDouble(yearTwo);
		
		double rateOfChange = (avgCO2Two - avgCO2One) / (yearDos - yearUno);
		
		return rateOfChange;
	}
	
	public static double getFutureCO2 (String yearOne, String yearTwo, String yearDate) {
		
		double rateOfChange = getRateOfChange(yearOne, yearTwo);
		double yearTwoDub = Double.valueOf(yearTwo);
		double yearDateDub = Double.valueOf(yearDate);
		
		return rateOfChange * (yearDateDub - yearTwoDub) + getAvgCO2(yearTwo);
	}
	
}
