/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2_weather_data_analysis;

/**
 *
 * @author Tajuddin Idrisa
 */
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Project2_Weather_Data_Analysis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        // create scanner object
        Scanner in = new Scanner(System.in);

        // create date format
       DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy"); 
       
       // list to store weather data
       ArrayList<Weather_data> weatherDataList = new ArrayList<Weather_data>();

       
       String fileName = "WeatherDataFile.csv";

       // Reading all lines from the file
       List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);

       // create weatherDataList
       lines.forEach(el -> {
           String[] lineData = el.split(",");
           LocalDate localDate = LocalDate.parse(lineData[0], dateFormat);
           double lowTemp = Double.parseDouble(lineData[1]);
           double highTemp = Double.parseDouble(lineData[2]);
           double precip = Double.parseDouble(lineData[3]);
           
           // create WeatherData object
           Weather_data weatherData = new Weather_data(localDate, lowTemp, highTemp, precip);
           weatherDataList.add(weatherData);// add weather data to list
       });

       //getting date from the user
       System.out.print("Enter the date to analyze Weather data in (MM/DD/YYYY) format : ");
       String date = in.next();
       LocalDate localDate = LocalDate.parse(date, dateFormat);// format user date
       int day = localDate.getDayOfMonth();
       
       int month = localDate.getMonthValue();
       String monthString;
       //convert the month entered as int to its corresponding string name
        switch (month) {
            case 1:  monthString = "January";       break;
            case 2:  monthString = "February";      break;
            case 3:  monthString = "March";         break;
            case 4:  monthString = "April";         break;
            case 5:  monthString = "May";           break;
            case 6:  monthString = "June";          break;
            case 7:  monthString = "July";          break;
            case 8:  monthString = "August";        break;
            case 9:  monthString = "September";     break;
            case 10: monthString = "October";       break;
            case 11: monthString = "November";      break;
            case 12: monthString = "December";      break;
            default: monthString = "Invalid month"; break;
        }

       // filter weatherdata list for user date
       List<Weather_data> filteredData = weatherDataList.stream()
               .filter(data -> (data.getDate().getMonthValue() == month && data.getDate().getDayOfMonth() == day))
               .collect(Collectors.toList());

       //compute all the required data from filtered data
       Double avgLowTemp = filteredData.stream().mapToDouble(val -> val.getLowTemp()).average().orElse(0);

       Double avgHighTemp = filteredData.stream().mapToDouble(val -> val.getHighTemp()).average().orElse(0);

       Double lowTemp = filteredData.stream().mapToDouble(val -> val.getLowTemp()).min().orElse(0);

       Double highTemp = filteredData.stream().mapToDouble(val -> val.getHighTemp()).max().orElse(0);

       Double avgPrecip = filteredData.stream().mapToDouble(val -> val.getPrecip()).average().orElse(0);

       // print all the computed data for user date
       System.out.println("The number of Weather data records found for " + monthString  + " " + day + " is "
               + filteredData.size());
       System.out.printf(
               "Average Low temperature recorded for " + monthString +" " + day + " is %.2f %n",  avgLowTemp);
       System.out.printf(
               "Average High temperature recorded for " + monthString +" " + day + " is %.2f %n",  avgHighTemp);
       System.out.println("Low temperature recorded for " + monthString +" " + day +  " is " + lowTemp);
       System.out.println("High temperature recorded for " + monthString +" " + day +  " is " + highTemp);
       System.out.printf(
               "Average Precipitate recorded for " + monthString +" " + day +  " is %.3f", avgPrecip);
        
    }
    
}
