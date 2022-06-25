/**
 * Print out total number of babies born, as well as for each gender, in a given CSV file of baby name data.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class BabyBirths {
    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += 1;
            if (rec.get(1).equals("M")) {
                totalBoys += 1;
            }
            else {
                totalGirls += 1;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);
    }

    public void testTotalBirths () {
        System.out.println("1900");
        FileResource fr = new FileResource("data/yob1900.csv");
        totalBirths(fr);
        System.out.println("1905");
        FileResource fr1 = new FileResource("data/yob1905.csv");
        totalBirths(fr1);
    }
    
    public int getRank (int year, String name, String gender) {
        String fileName =  "data/yob" + year + ".csv"; 
        FileResource fr = new FileResource(fileName);
        int rank = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                rank += 1;
                if(rec.get(0).equals(name)){
                    return rank;
                }
            }
        }
        return -1;
    }
    
    public void testgetRank () {
        System.out.println("rank ====Emily==========");
        int rank = getRank(1960, "Emily" ,"F");
        System.out.println("rank = " + rank);
        System.out.println("rank ====Frank==========");
        rank = getRank(1971, "Frank" ,"M");
        System.out.println("rank = " + rank);
    }
    
    public String getName (int year, int rank, String gender) {
        String fileName =  "data/yob" + year + ".csv"; 
        //String fileName =  "data/example-small.csv";     
        //
        // open the correct year
        FileResource fr = new FileResource(fileName);
        int runningRank = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                runningRank += 1;
                if(runningRank == rank){
                    //if is correct gender
                    return rec.get(0);
                }
            }
        }
        return "NO NAME";
    }
    
        public void testgetName () {
        System.out.println("getName =====350=========");
        String name = getName(1980, 350 ,"F");
        System.out.println("name = " + name);
        
        System.out.println("getName =====450 =========");
        name = getName(1982, 450 ,"M");
        System.out.println("name = " + name);
    }
    
    public String whatIsNameInYear(String name, int year, int newYear, String gender){
        int rank = getRank(year, name,gender);
        String newName = getName(newYear,rank,gender);
        return newName;
    }
    
     public void testWhatIsNameInYear () {
        System.out.println("whatIsNameInYear=======Susan=======");
        String newName = whatIsNameInYear("Susan", 1972, 2014 ,"F");
        System.out.println("name = " + newName);
        
        System.out.println("whatIsNameInYear=======Owen =======");
        newName = whatIsNameInYear("Owen", 1974, 2014,"M");
        System.out.println("name = " + newName);
    }
    
    public int yearOfHighestRank(String name, String gender){
        int highestRank = -1;
        int highestYear = -1;
        int firstYear = 1880;
        int lastYear = 2014;
        
        while(firstYear <= lastYear){ 
            int rank = getRank(firstYear, name, gender);
            if(highestRank == -1 || (rank != -1 && highestRank > rank)){
                //update value
                highestRank = rank;
                highestYear = firstYear;
            }
            firstYear += 1;
            }
        return highestYear;
    }
    
    
    public void testYearOfHighestRank(){
        String name = "Genevieve";
        String gender = "F";
        int year = yearOfHighestRank(name, gender);
        System.out.println("For " + name + " year with highest rank was " + year);
        name = "Mich";
        gender = "M";
        year = yearOfHighestRank(name, gender);
        System.out.println("For " + name + " year with highest rank was " + year);
    }
    
    public double getAverageRank(String name, String gender){
        double average = -1;
        double count = 0;
        double total = 0;
        int firstYear = 1880;
        int lastYear = 2014;
        
        while(firstYear <= lastYear){ 
            int rank = getRank(firstYear, name, gender);
            if(rank != -1){
                total = total + rank;
                count += 1;
            }
            firstYear += 1;
        }
        
        if(count == 0){
            return -1.0;
        }
        average = total/count;
        return average;
    }
    
    public void testAverageRank(){
        String name = "Susan";
        String gender = "F";
        double average = getAverageRank(name, gender);
        
        System.out.println("For " + name + " average rank is " + average);
        
        name = "Robert";
        gender = "M";
        average = getAverageRank(name, gender);
        
        System.out.println("For " + name + " average rank is " + average);
        
        name = "Yui";
        gender = "F";
        average = getAverageRank(name, gender);
        
        System.out.println("For " + name + " average rank is " + average);
        
        name = "David";
        gender = "M";
        average = getAverageRank(name, gender);
        
        System.out.println("For " + name + " average rank is " + average);
    }
    
    public int getTotalBirthsRankedHigher(String name, int year, String gender){
        int rank = getRank(year, name, gender);
        int totalBirth = 0;
        
        if(rank == -1){
            return -1;
        }
        
        String fileName = "data/yob" + year + ".csv"; 
        FileResource fr = new FileResource(fileName);
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)){
                if(rec.get(0).equals(name)) {
                    return totalBirth;
                }
                totalBirth = totalBirth + Integer.parseInt(rec.get(2));
            }
        }
            //count +=1;
        return totalBirth;
    }
    
    public void testTotalBirthsRankedHigher(){
        String name = "Emily";
        int year = 1990;
        String gender ="F";
        int total = getTotalBirthsRankedHigher(name, year, gender);
        System.out.println("For " + name + " in " + year +" total birth of more common name kids is " + total);
        
        name = "Drew";
        year = 1990;
        gender ="M";
        total = getTotalBirthsRankedHigher(name, year, gender);
        System.out.println("For " + name + " in " + year +" total birth of more common name kids is " + total);
        name = "David";
        year = 2003;
        gender ="M";
        total = getTotalBirthsRankedHigher(name, year, gender);
        System.out.println("For " + name + " in " + year +" total birth of more common name kids is " + total);
        name = "abkjdokjfoko";
        year = 2003;
        gender ="M";
        total = getTotalBirthsRankedHigher(name, year, gender);
        System.out.println("For " + name + " in " + year +" total birth of more common name kids is " + total);
    }
}

