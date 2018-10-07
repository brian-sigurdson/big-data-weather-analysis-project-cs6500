/**
 * CS6500 Project
 * 
 * class to hold weather station data 
 */

/**
 * @author brians
 *
 */
public class WeatherStation {
	
	// the following is taken from ish-abbreviated.txt and used to create variables
	/*
	COLUMN  DATA DESCRIPTION

	01-06   USAF = AIR FORCE CATALOG STATION NUMBER   
	08-12   WBAN = NCDC WBAN NUMBER
	14-25   YR--MODAHRMN = YEAR-MONTH-DAY-HOUR-MINUTE IN GREENWICH MEAN TIME (GMT)
	27-29   DIR = WIND DIRECTION IN COMPASS DEGREES, 990 = VARIABLE, REPORTED AS
	        '***' WHEN AIR IS CALM (SPD WILL THEN BE 000)
	31-37   SPD & GUS = WIND SPEED & GUST IN MILES PER HOUR  
	39-41   CLG = CLOUD CEILING--LOWEST OPAQUE LAYER
	        WITH 5/8 OR GREATER COVERAGE, IN HUNDREDS OF FEET,
	        722 = UNLIMITED 
	43-45   SKC = SKY COVER -- CLR-CLEAR, SCT-SCATTERED-1/8 TO 4/8,
	        BKN-BROKEN-5/8 TO 7/8, OVC-OVERCAST, 
	        OBS-OBSCURED, POB-PARTIAL OBSCURATION   
	47-47   L = LOW CLOUD TYPE, SEE BELOW
	49-49   M = MIDDLE CLOUD TYPE, SEE BELOW
	51-51   H = HIGH CLOUD TYPE, SEE BELOW  
	53-56   VSB = VISIBILITY IN STATUTE MILES TO NEAREST TENTH 
	        NOTE: FOR SOME STATIONS, VISIBILITY IS REPORTED ONLY UP TO A
	        MAXIMUM OF 7 OR 10 MILES IN METAR OBSERVATIONS, BUT TO HIGHER
	        VALUES IN SYNOPTIC OBSERVATIONS, WHICH CAUSES THE VALUES TO 
	        FLUCTUATE FROM ONE DATA RECORD TO THE NEXT.  ALSO, VALUES
	        ORIGINALLY REPORTED AS '10' MAY APPEAR AS '10.1' DUE TO DATA
	        BEING ARCHIVED IN METRIC UNITS AND CONVERTED BACK TO ENGLISH.
	58-68   MW MW MW MW = MANUALLY OBSERVED PRESENT WEATHER--LISTED BELOW IN MANUALLY OBSERVED PRESENT WEATHER TABLE
	70-80   AW AW AW AW = AUTO-OBSERVED PRESENT WEATHER--LISTED BELOW IN AUTO-OBSERVED PRESENT WEATHER TABLE
	82-82   W = PAST WEATHER INDICATOR, SEE BELOW
	84-92   TEMP & DEWP = TEMPERATURE & DEW POINT IN FAHRENHEIT 
	94-99   SLP = SEA LEVEL PRESSURE IN MILLIBARS TO NEAREST TENTH 
	101-105   ALT = ALTIMETER SETTING IN INCHES TO NEAREST HUNDREDTH 
	107-112   STP = STATION PRESSURE IN MILLIBARS TO NEAREST TENTH
	114-116  MAX = MAXIMUM TEMPERATURE IN FAHRENHEIT (TIME PERIOD VARIES)
	118-120 MIN = MINIMUM TEMPERATURE IN FAHRENHEIT (TIME PERIOD VARIES)
	122-126 PCP01 = 1-HOUR LIQUID PRECIP REPORT IN INCHES AND HUNDREDTHS --
	        THAT IS, THE PRECIP FOR THE PRECEDING 1 HOUR PERIOD
	128-132 PCP06 = 6-HOUR LIQUID PRECIP REPORT IN INCHES AND HUNDREDTHS --
	        THAT IS, THE PRECIP FOR THE PRECEDING 6 HOUR PERIOD
	134-138 PCP24 = 24-HOUR LIQUID PRECIP REPORT IN INCHES AND HUNDREDTHS
	        THAT IS, THE PRECIP FOR THE PRECEDING 24 HOUR PERIOD
	140-144 PCPXX = LIQUID PRECIP REPORT IN INCHES AND HUNDREDTHS, FOR 
	        A PERIOD OTHER THAN 1, 6, OR 24 HOURS (USUALLY FOR 12 HOUR PERIOD
	        FOR STATIONS OUTSIDE THE U.S., AND FOR 3 HOUR PERIOD FOR THE U.S.)
	        T = TRACE FOR ANY PRECIP FIELD
	146-147 SD = SNOW DEPTH IN INCHES  
	*/

	
	// instance variables /////////////////////////////////////////////////////
	private int defaultInt = -10000;
	private double defaultDouble = -10000.0;
	
	private String usaf = null;
	private String wban = null;
	private String  yyyymmddhhmm = null;
	private Integer dir  = defaultInt;
	private Integer spd  = defaultInt;
	private Integer gus  = defaultInt;
	private String clg = null;
	private String skc = null;
	private Integer l = defaultInt;
	private Integer m = defaultInt;
	private Integer h = defaultInt;
	private Double vsb = defaultDouble;
	private Integer mw1 = defaultInt;
	private Integer mw2 = defaultInt;
	private Integer mw3 = defaultInt;
	private Integer mw4 = defaultInt;
	private Integer aw1 = defaultInt;
	private Integer aw2 = defaultInt;
	private Integer aw3 = defaultInt;
	private Integer aw4 = defaultInt;
	private Integer w = defaultInt;
	private Integer temp  = defaultInt;
	private Integer dewp  = defaultInt;
	private Double slp = defaultDouble;
	private Double alt = defaultDouble;
	private Double stp = defaultDouble;
	private Double max = defaultDouble;
	private Double min = defaultDouble;
	private Double pcp01 = defaultDouble;
	private Double pcp06 = defaultDouble;
	private Double pcp24 = defaultDouble;
	private Double pcpxx = defaultDouble;
	private Integer sd  = defaultInt;
	
	// instance methods ///////////////////////////////////////////////////////
	/*
	 * Constructor
	 * Accepts a string representing a line from the file, and passes the 
	 * line to another method for processing.
	 */
	public WeatherStation(String theLine){
		processNewWeatherStation(theLine);
	}
	
	/*
	 * return the station id
	 */
	public String getStationID(){
		return usaf;
	}
	
	/*
	 * get the month
	 */
	public int getMonth(){
		// grab the month from the date string and cast it
		//           0123456789
		return new Integer(yyyymmddhhmm.substring(4,6).trim()).intValue();
	}
	
	/*
	 * get the date in Hive format
	 */
	public String getYYYYMMDDHHMM_Hive(){
		// i want to print the timestamp in a manner acceptable to Hive
				
		// http://www.tutorialspoint.com/hive/hive_data_types.htm
//		Timestamp
//		It supports traditional UNIX timestamp with optional nanosecond precision. 
//		It supports java.sql.Timestamp format “YYYY-MM-DD HH:MM:SS.fffffffff” 
//		and format “yyyy-mm-dd hh:mm:ss.ffffffffff”.
//
//		Dates
//		DATE values are described in year/month/day format in the form {{YYYY-MM-DD}}.
		
		// 0123 45 67 89
		// yyyy mm dd hhmm
		
		String yyyy = yyyymmddhhmm.substring(0,4);
		String mm = yyyymmddhhmm.substring(4,6);
		String dd = yyyymmddhhmm.substring(6,8);
		String hh = yyyymmddhhmm.substring(8,10);
		String min = yyyymmddhhmm.substring(10);
		
		return yyyy + "-" + mm + "-" + dd + " " + hh + ":" + min;
	}
	
	/*
	 * override default toString()
	 */
	public String toString(){
		return printPrettyCSV();
	}
	
	/*
	 * method to facilitate printing the weather station data to file
	 * returns a string that is already in the csv format needed for the project
	 */
	public String printPrettyCSV(){
		
		return (
				usaf + "," +
				wban + "," + 
				getYYYYMMDDHHMM_Hive() + "," +
				dir + "," + 
				spd + "," + 
				gus + "," + 
				clg + "," + 
				skc + "," +
				l + "," +
				m + "," +
				h + "," +
				vsb + "," +
				mw1 + "," +
				mw2 + "," +
				mw3 + "," +
				mw4 + "," +
				aw1 + "," +
				aw2 + "," +
				aw3 + "," +
				aw4 + "," +
				w + "," +
				temp + "," +
				dewp + "," +
				slp + "," +
				alt + "," +
				stp + "," +
				max + "," +
				min + "," +
				pcp01 + "," +
				pcp06 + "," +
				pcp24 + "," +
				pcpxx + "," +
				sd);  		
	}
	
	/*
	 * a method to set the appropriate int value for a numeric variable, or
	 * a default if missing data
	 */
	private Integer setIntValue(String theLine, int start, int stop, Integer theVariable){

		// test for missing values
		if(theLine.charAt(start) == '*'){
			// missing data leave variable at default value			
		}else{
			try{
				theVariable = new Integer(theLine.substring(start, stop).trim());
			}catch(NumberFormatException e){
				System.out.println(theLine.substring(start, stop));
				System.out.println(e);
			}			
		}
		return theVariable;		
	}
	
	/*
	 * a method to set the appropriate int value for a numeric variable, or
	 * a default if missing data
	 */
	private Double setDoubleValue(String theLine, int start, int stop, Double theVariable){

		// test for missing values
		if(theLine.charAt(start) == '*'){
			// missing data leave variable at default value			
		}else{
			try{
				theVariable = new Double(theLine.substring(start, stop).trim());
			}catch(NumberFormatException e){
				System.out.println(theLine.substring(start, stop));
				System.out.println(e);
			}			
		}
		return theVariable;
	}
	
	/*
	 * method to test for '*' values in numeric fields
	 */
	
	/*
	 * The string will be parsed to create a weather station instance.
	 */
	private void processNewWeatherStation(String theLine){

		// note, I'll only test numeric values for '*' because such values
		// won't be a problem if left in text fields and will signal
		// missing values
		
		usaf = theLine.substring(0, 6).trim();
		wban = theLine.substring(7, 12).trim();
		yyyymmddhhmm = theLine.substring(13, 25).trim();
		dir = setIntValue(theLine, 26, 29, dir);
		spd = setIntValue(theLine, 30, 33, spd);
		gus = setIntValue(theLine, 34, 37, gus);
		clg = theLine.substring(38, 41).trim();
		skc = theLine.substring(42, 45).trim();
		l = setIntValue(theLine, 46, 47, l);
		m = setIntValue(theLine, 48, 49, m);
		h = setIntValue(theLine, 50, 51, h);
		vsb = setDoubleValue(theLine, 52, 56, vsb);
		mw1 = setIntValue(theLine, 57, 59, mw1);
		mw2 = setIntValue(theLine, 60, 62, mw2);
		mw3 = setIntValue(theLine, 63, 65, mw3);
		mw4 = setIntValue(theLine, 66, 68, mw4);
		aw1 = setIntValue(theLine, 69, 71, aw1);
		aw2 = setIntValue(theLine, 72, 74, aw2);
		aw3 = setIntValue(theLine, 75, 77, aw3);
		aw4 = setIntValue(theLine, 78, 80, aw4);
		w = setIntValue(theLine, 81, 82, w);
		temp = setIntValue(theLine, 83, 87, temp);
		dewp = setIntValue(theLine, 88, 92, dewp);
		slp = setDoubleValue(theLine, 93, 99, slp);
		alt = setDoubleValue(theLine, 100, 105, alt);
		stp = setDoubleValue(theLine, 106, 112, stp);
		max = setDoubleValue(theLine, 113, 116, max);
		min = setDoubleValue(theLine, 117, 120, min);
		pcp01 = setDoubleValue(theLine, 121, 126, pcp01);
		pcp06 = setDoubleValue(theLine, 127, 132, pcp06);
		pcp24 = setDoubleValue(theLine, 133, 138, pcp24);
		pcpxx = setDoubleValue(theLine, 139, 144, pcpxx);
		sd = setIntValue(theLine, 145, 147, sd);
	}

}
