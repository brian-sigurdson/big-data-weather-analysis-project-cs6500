/**
 * This is the driver to pre-process data for the CS6500 project.
 */

// imports
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;



/**
 * @author brians
 *
 */
public class Preprocess_Data {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
						
		Date now = new Date();
		String startNow = now.toString();
		System.out.println(startNow);
		String toWriteOut = "Starting Preprocess_Data.main(String [] args)"; 
		System.out.println(toWriteOut);
		
		
		// input stream
		Scanner inputStream = null;
		// output stream for the individual processed weather station
		PrintWriter outputStreamSingle = null;
		// cumulative output file of weather station information
		PrintWriter outputStreamAll = null;
		// log file
		PrintWriter logFile = null;		
					
		for(int year = 1901; year < 1941; year++){
			
			// define some file related variables
			
			String inputFileName = new String(new Integer(year).toString());
			String fileExtension = ".txt";
			//String pathToInputFile = "ncdc_input_data/";
			String pathToInputFile = "/media/brians/debian_home/brians/bks001_shared/cs6500/from_desktop/Project/Data/ncdc_input_data/";
			//String pathToOutputFile = "ncdc_output_data/";
			String pathToOutputFile = "/media/brians/debian_home/brians/bks001_shared/cs6500/from_desktop/Project/Data/ncdc_output_data/";
			String fullInputFileName = inputFileName + fileExtension;
			String fullOutputFileNameSingle = 
					inputFileName + "_processed" + fileExtension;
			String fullOutputFileNameAll = "all_processed" + fileExtension;
			
			
			// setup io streams
			try{
				inputStream = 
						new Scanner(new FileInputStream(
								pathToInputFile + fullInputFileName));
						
				outputStreamSingle = 
						new PrintWriter(
								new FileOutputStream(
										pathToOutputFile + fullOutputFileNameSingle),
										true);	
				
				System.out.println("file opened: " + fullInputFileName);
				System.out.println("file opened: " + fullOutputFileNameSingle);	
				// write headers to current year's file 
				Preprocess_Data.PRINT_COLUMN_HEADINGS_CSV(outputStreamSingle);	
				
				// only initialize on first iteration
				if(year == 1901){
					outputStreamAll = 
							new PrintWriter(
									new FileOutputStream(
											pathToOutputFile + fullOutputFileNameAll), 
											true);
										
					System.out.println("file opened: " + fullOutputFileNameAll);
					// write headers to accumulation file
					Preprocess_Data.PRINT_COLUMN_HEADINGS_CSV(outputStreamAll);
					
					logFile = 
							new PrintWriter(
									new FileOutputStream(pathToOutputFile + 
											"logfile.txt"), 
											true);	
					Preprocess_Data.APPEND_LOG_FILE(logFile, startNow);
					Preprocess_Data.APPEND_LOG_FILE(logFile, toWriteOut);
				}
			}
			catch(FileNotFoundException e){
				System.out.println("Error opening file.  Printing exception and exiting.");
				System.out.println(e);
				System.exit(0);
			}			
							
			
			// id to test for change in stations read from the file
			String priorStationID = null;
			// this is a temporary id i'll need when encounter a new station
			String tempID = null;
			// is this the first line for the file
			Boolean isFirstLine = true;
			// is this the first record of the file
			Boolean isFirstRecord = true;
			// line in the file
			String theLine = null;
			// null weather station
			WeatherStation theStation = null;
			// create a list to hold the weather station instances for this station for this year
			ArrayList<WeatherStation> theList = new ArrayList<WeatherStation>(); 
				
			
			// process each line of the input file
			while(inputStream.hasNextLine()){
				
				//first line of the file?
				if(isFirstLine.booleanValue() == true){
					// discard the first line
					inputStream.nextLine();
					// set the flag
					isFirstLine = false;
					
	//				System.out.println("Column headings of " + fullInputFileName + 
	//						" discarded");
				}					
							
				// test to make sure file not empty
				if(!inputStream.hasNext()){
					System.out.println("Is " + fullInputFileName + " an empty file?");
					throw new Exception("Is " + fullInputFileName + " an empty file?");
				}
				
				// we've discarded the header line
				// we've tested that there are more lines to process
				
				// so get the next line in the file
				theLine = inputStream.nextLine();
				// create weather station object
				theStation = new WeatherStation(theLine);
				
				// test if we are on the first record of the file
				if(isFirstRecord.booleanValue() == true){				 
					
					// add the new station to the list
					theList.add(theStation);
					isFirstRecord = false;
					// then we don't need to test to see if there is a change
					// in station id's, just set the prior id variable
					priorStationID = theStation.getStationID();
					
	//				System.out.println("first record of station " + 
	//						theStation.getStationID() + " processed");
					
					//System.out.println("theList");
					//System.out.println(theList);
					//return;
					
				} else{
					
					// we are not on the first record of the file
					if( priorStationID.equals(theStation.getStationID()) ){
						// do nothing, because it is still the same station
						// add the new station to the list
						theList.add(theStation);
						
					}else{
						
						// new station encountered
						// temp id to pass to method below
						tempID = priorStationID;
						// update new prior station id
						priorStationID = theStation.getStationID();
						
						// process current weather station data by writing to
						// appropriate files
						Preprocess_Data.PROCESS_NEW_STATION(
								theList,
								outputStreamSingle,
								fullOutputFileNameSingle,
								outputStreamAll,
								fullOutputFileNameAll,
								theStation,
								tempID,
								logFile);
						 
						 // set flag back to true
						 isFirstRecord = true;
						 
	//					System.out.println("theList");
	//					System.out.println(theList);
	//					return;
						
					}				
				} // done processing some record
			} // end while loop
			
			// although we've exited the loop the last weather station is still
			// in the array list, so process it
			Preprocess_Data.PROCESS_NEW_STATION(
					theList,
					outputStreamSingle,
					fullOutputFileNameSingle,
					outputStreamAll,
					fullOutputFileNameAll,
					theStation,
					tempID,
					logFile);
			 			
			// close this year's files
			outputStreamSingle.close();	
			inputStream.close();
			
			System.out.println("\tClosed file: " + fullOutputFileNameSingle);
			System.out.println("\tClosed file: " + fullInputFileName);
			
		}// end for loop
		

		outputStreamAll.close();
		System.out.println("Accumulation file closed.");
		
		now = new Date();
		toWriteOut = "Closing log file at " + now.toString();
		Preprocess_Data.APPEND_LOG_FILE(logFile, toWriteOut);
		logFile.close();
		System.out.println("Log file closed.");
		
	}

	/*
	 * method to test a list of station data to see if it meets the 
	 * filtration criteria of at least one data point per month for the year.
	 */
	public static Boolean TEST_STATION_FOR_FILTER(ArrayList<WeatherStation> theList){
		// test if the weather station has at least one data point 
		// in every month for the year
		
		// iterator to traverse the list
		Iterator<WeatherStation> listIter = theList.iterator();
		WeatherStation theStation = null;
		
		Boolean jan = false;
		Boolean feb = false;
		Boolean mar = false;
		Boolean apr = false;
		Boolean may = false;
		Boolean jun = false;
		Boolean jul = false;
		Boolean aug = false;
		Boolean sep = false;
		Boolean oct = false;
		Boolean nov = false;
		Boolean dec = false;
		
		while(listIter.hasNext()){
			
			theStation = listIter.next();
			
			switch(theStation.getMonth()){
				case 1: jan = true;	break;
				case 2: feb = true;	break;
				case 3: mar = true;	break;
				case 4: apr = true;	break;
				case 5: may = true;	break;
				case 6: jun = true;	break;
				case 7: jul = true;	break;
				case 8: aug = true;	break;
				case 9: sep = true;	break;
				case 10: oct = true;	break;
				case 11: nov = true;	break;
				case 12: dec = true;	break;
			}
		}
		
		return 
			(jan && feb && mar && apr && may && jun && 
			jul && aug && sep && oct && nov && dec);
	}
	
	/*
	 * method to write a filtered station data to the output file
	 */
	public static void WRITE_STATION_TO_FILE(ArrayList<WeatherStation> theList, 
			PrintWriter outputStream, String fullOutputFileName){
		
		// iterator to traverse the list
		Iterator<WeatherStation> listIter = theList.iterator(); 
		WeatherStation theStation = null;
		
		// write all of the station's data to the output file
		while(listIter.hasNext()){
			
			theStation = (WeatherStation)listIter.next();
			outputStream.println(theStation.printPrettyCSV());
		}
		
	}
	
	/*
	 * method to append filtered station data to the all output file
	 */
	public static void APPEND_STATION_TO_FILE(ArrayList<WeatherStation> theList, 
			PrintWriter outputStream, String fullOutputFileName){
		
		// iterator to traverse the list
		Iterator<WeatherStation> listIter = theList.iterator(); 
		WeatherStation theStation = null;
		
		// write all of the station's data to the output file
		while(listIter.hasNext()){
			
			theStation = (WeatherStation)listIter.next();
			outputStream.println(theStation.printPrettyCSV());
		}
	}
	
	/*
	 * method to append to log file
	 */
	public static void APPEND_LOG_FILE(
			PrintWriter logFile, String toWriteOut){
		
		logFile.println(toWriteOut);
	}
	
	/*
	 * method to process the non-first record of a new file
	 */
	public static void PROCESS_NEW_STATION(
			ArrayList<WeatherStation> theList,
			PrintWriter outputStreamSingle,
			String fullOutputFileNameSingle,
			PrintWriter outputStreamAll,
			String fullOutputFileNameAll,
			WeatherStation theStation,
			String priorStationId,
			PrintWriter logFile){
		
//		System.out.println("Testing station# " + priorStationId);
		
		// test if the current station is to be written to file
		// or discarded
		if( Preprocess_Data.TEST_STATION_FOR_FILTER(theList) ){
			
//			System.out.println("\tStation " + priorStationId + 
//					" passed filter.  Writing to file: " + fullOutputFileNameSingle);
			
			// write station data to file
			Preprocess_Data.WRITE_STATION_TO_FILE(theList, 
					outputStreamSingle, fullOutputFileNameSingle);				
			
//			System.out.println("\tWriting station " + priorStationId + 
//					" to " + fullOutputFileNameAll);
			
			// append station's data to cumulative file
			Preprocess_Data.APPEND_STATION_TO_FILE(theList, 
					outputStreamAll, fullOutputFileNameAll);
		}else{
			String toWriteOut = "\tStation " + priorStationId + 
					" did not pass filter.  Discard. " +
					" Year = " + fullOutputFileNameSingle.substring(0, 4);
					
			System.out.println(toWriteOut);
			Preprocess_Data.APPEND_LOG_FILE(logFile, toWriteOut);
		}		
		
		// regardless empty the current list
		theList.clear();
		// add the new station to the list
		theList.add(theStation);	

	}
	
	/*
	 * print column headers as the first line of the file to facilitate
	 * import into hive
	 */
	public static void PRINT_COLUMN_HEADINGS_CSV(PrintWriter outputStream){
		
		outputStream.println(
				"usaf" + "," +
				"wban" + "," + 
				"YYYYMMDDHHMM" + "," +
				"dir" + "," + 
				"spd" + "," + 
				"gus" + "," + 
				"clg" + "," + 
				"skc" + "," +
				"l" + "," +
				"m" + "," +
				"h" + "," +
				"vsb" + "," +
				"mw1" + "," +
				"mw2" + "," +
				"mw3" + "," +
				"mw4" + "," +
				"aw1" + "," +
				"aw2" + "," +
				"aw3" + "," +
				"aw4" + "," +
				"w" + "," +
				"temp" + "," +
				"dewp" + "," +
				"slp" + "," +
				"alt" + "," +
				"stp" + "," +
				"max" + "," +
				"min" + "," +
				"pcp01" + "," +
				"pcp06" + "," +
				"pcp24" + "," +
				"pcpxx" + "," +
				"sd");  		
	}
}
