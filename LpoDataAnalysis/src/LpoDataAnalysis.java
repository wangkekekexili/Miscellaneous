import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * Analyze the data from {@link http://lpo.dt.navy.mil/}.
 * 
 * @author kewang
 *
 */
public class LpoDataAnalysis {

	public static void main(String[] args) {
		
		int year = 0;
		int month = 0;
		int day = 0;
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to LPA data analyzer!");
		
		// get year, month, and day
		System.out.print("Enter year: ");
		if (scanner.hasNextInt()) {
			year = scanner.nextInt();
		} else {
			System.exit(1);
		}
		System.out.print("Enter month: ");
		if (scanner.hasNextInt()) {
			month = scanner.nextInt();
		} else {
			System.exit(1);
		}
		System.out.print("Enter day: ");
		if (scanner.hasNextInt()) {
			day = scanner.nextInt();
		} else {
			System.exit(1);
		}
		scanner.close();
		
		// validate date
		LocalDate date = null;
		try {
			date = LocalDate.of(year, month, day);
		} catch (DateTimeException e) {
			System.out.println("Invalide date!");
			System.exit(1);
		}
		
		// get data from the website
		InputStream stream = null;
		try {
			stream = new URL("http://lpo.dt.navy.mil/data/DM/Environmental_Data_Deep_Moor_"+
		        date.getYear() + ".txt").openStream();
		} catch (IOException e) {
			System.out.println("Cannot get data. Please check the original website");
			System.exit(1);
		}
		List<String> lines = null;
		try {
			lines = IOUtils.readLines(stream);
		} catch (IOException e) {
			System.out.println("Fail to get data");
			System.exit(1);
		}
		
		List<Double> windSpeedList = new ArrayList<>();
		List<Double> airTemperatureList = new ArrayList<>();
		List<Double> barometricPressure = new ArrayList<>();
		
		for (String line : lines) {
			String dateFormat = date.format(DateTimeFormatter
					.ofPattern("uuuu_MM_dd"));
			if (line.startsWith(dateFormat)) {
				String[] item = line.split(Pattern.quote("\t"));
				if (item.length < 8) {
					System.out.println("Please check file format.");
					System.exit(1);
				}
				try {
					windSpeedList.add(Double.parseDouble(item[7]));
				} catch (NumberFormatException e) {}
				try {
					airTemperatureList.add(Double.parseDouble(item[1]));
				} catch (NumberFormatException e) {}
				try {
					barometricPressure.add(Double.parseDouble(item[2]));
				} catch (NumberFormatException e) {}
			}
		}
//		System.out.println(windSpeedList.size());
//		System.out.println(airTemperatureList.size());
//		System.out.println(barometricPressure.size());
		
		// calculate mean and median for each attribute
		System.out.println();
		System.out.println(date.toString());
		System.out.println("Wind Speed");
		System.out.println("mean: " + windSpeedList.stream().mapToDouble(
				e->e.doubleValue()).average().getAsDouble());
		System.out.print("median: ");
		windSpeedList.sort(new DefaultDoubleComparator());
		int tempSize = windSpeedList.size();
		if (tempSize % 2 == 0) {
			System.out.println((windSpeedList.get(tempSize/2)+windSpeedList.get(tempSize)/2-1)/2);
		} else {
			System.out.println(windSpeedList.get(tempSize/2));
		}
		
		System.out.println();
		
		System.out.println("Air Temperature");
		System.out.println("mean: " + airTemperatureList.stream().mapToDouble(
				e->e.doubleValue()).average().getAsDouble());
		System.out.print("median: ");
		airTemperatureList.sort(new DefaultDoubleComparator());
		tempSize = airTemperatureList.size();
		if (tempSize % 2 == 0) {
			System.out.println((airTemperatureList.get(tempSize/2)+airTemperatureList.get(tempSize)/2-1)/2);
		} else {
			System.out.println(airTemperatureList.get(tempSize/2));
		}
		
		System.out.println();
		System.out.println("Barometric Pressure");
		
		System.out.println("mean: " + barometricPressure.stream().mapToDouble(
				e->e.doubleValue()).average().getAsDouble());
		System.out.print("median: ");
		barometricPressure.sort(new DefaultDoubleComparator());
		tempSize = windSpeedList.size();
		if (tempSize % 2 == 0) {
			System.out.println((barometricPressure.get(tempSize/2)+barometricPressure.get(tempSize)/2-1)/2);
		} else {
			System.out.println(barometricPressure.get(tempSize/2));
		}
		
		System.out.println();
		
	}

}

class DefaultDoubleComparator implements Comparator<Double> {

	@Override
	public int compare(Double o1, Double o2) {
		if (o1.doubleValue() > o2.doubleValue()) {
			return 1;
		} else if (o1.doubleValue() < o2.doubleValue()) {
			return -1;
		} else {
			return 0;
		}
	}
	
}