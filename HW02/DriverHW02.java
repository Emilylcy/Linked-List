
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DriverHW02 {

    public static void main(String[] args) throws FileNotFoundException {
        // Parse command line arguments
        if (args.length < 3) {
            System.out.println("incorrect number of arguments");
            return;
        }

        String[] filenames = new String[args.length];
        String[] names = new String[args.length];
        char[] genders = new char[args.length];
        int[] years = new int[args.length];
        int numNames = 0;
        int numFiles = 0;
        

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-m") || args[i].equals("-f")) {
                if (i + 1 < args.length) {
                    genders[numNames] = args[i].charAt(1);
                    names[numNames] = args[i + 1];
                    numNames++;
                    i++;
                } else {
                    System.out.println("missing name after -m or -f.");
                    return;
                }
            } else {
                File file = new File(args[i]);
                filenames[numFiles] = file.getAbsolutePath();
                numFiles++;
            }
        }

        // Read files and populate NameLL objects
        NameLL maleNames = new NameLL();
        NameLL femaleNames = new NameLL();
        NameLL maleIndex = new NameLL();
        NameLL femaleIndex = new NameLL();
        NameLL maleRank = new NameLL();
        NameLL femaleRank = new NameLL();

        // Read files and calculate total counts
        for (int i = 0; i < numFiles; i++) {
            Scanner scanner = new Scanner(new File(filenames[i]));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String maleName = parts[1];
                int maleNumber = Integer.parseInt(parts[2]);
                String femaleName = parts[3];
                int femaleNumber = Integer.parseInt(parts[4]);
                String filename = filenames[i];
                int startIndex = filename.indexOf("names") + "names".length();
                int endIndex = filename.indexOf(".csv");
                String yearStr = filename.substring(startIndex, endIndex);
                yearStr = yearStr.replaceAll("[^0-9]", "");
                int year = Integer.parseInt(yearStr);
                years[i] = year;
                if (!maleName.isEmpty()) {
                    maleNames.insertSortedAlpha(new Name(maleName, 'M', year, Integer.parseInt(parts[0]), maleNumber, 0.0));
                }
                if (!femaleName.isEmpty()) {
                    femaleNames.insertSortedAlpha(new Name(femaleName, 'F', year, Integer.parseInt(parts[0]), femaleNumber, 0.0)); 
                }
            }
            
            scanner.close();
        }

        for(int i = 0; i < numFiles; i++){
            Scanner scanner = new Scanner(new File(filenames[i]));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int rank = Integer.parseInt(parts[0]);
                String maleName = parts[1];
                int maleNumber = Integer.parseInt(parts[2]);
                String femaleName = parts[3];
                int femaleNumber = Integer.parseInt(parts[4]);
                String filename = filenames[i];
                int startIndex = filename.indexOf("names") + "names".length();
                int endIndex = filename.indexOf(".csv");
                String yearStr = filename.substring(startIndex, endIndex);      
                yearStr = yearStr.replaceAll("[^0-9]", "");
                int year = Integer.parseInt(yearStr);
                years[i] = year;
                int currentIdx = maleRank.index(maleName);
                if (!maleName.isEmpty()) {
                     if (maleRank.index(maleName) == -1){
                        maleIndex.insertSortedAlpha(new Name(maleName, 'M', year, rank, maleNumber, 0.0));
                        maleRank.insertSortedRank(new Name(maleName, 'M', year, rank, maleNumber, 0.0));
                    } else{    
                        int currentnum = maleRank.getNumber(currentIdx);
                        maleRank.remove(new Name(maleName, 'M', year, rank, maleNumber, 0.0));
                        maleRank.insertSortedRank(new Name(maleName, 'M', year, rank, maleNumber+currentnum, 0.0));
                    }
                }
                int currentIdx2 = femaleRank.index(femaleName);
                if (!femaleName.isEmpty()) {
                    if (femaleRank.index(femaleName) == -1){
                        femaleIndex.insertSortedAlpha(new Name(femaleName, 'F', year, rank, femaleNumber, 0.0)); 
                        femaleRank.insertSortedRank(new Name(femaleName, 'F', year, rank, femaleNumber, 0.0));
                    } else {
                        int currentnum2 = femaleRank.getNumber(currentIdx2);
                        femaleRank.remove(new Name(femaleName, 'F', year, rank, femaleNumber, 0.0));
                        femaleRank.insertSortedRank(new Name(femaleName, 'F', year, rank, femaleNumber+currentnum2, 0.0));
                    }
                }
            }
            scanner.close();
        }
    
        
        // Calculate total counts and percentages
        for (int i = 0; i < numFiles; i++) {
            int totalMaleNum = getMaleTotal(maleNames, years[i]);
            int totalFemaleNum = getFemaleTotal(femaleNames, years[i]);
            int totalMale = maleTotal(maleNames);
            int totalFemale = femaleTotal(femaleNames);
            updatePercentages(maleNames, years[i], totalMaleNum);
            updatePercentages(femaleNames,years[i],totalFemaleNum);
            updateTotalPercentage(maleRank, totalMale);
            updateTotalPercentage(femaleRank, totalFemale);

        }

        for (int j = 0; j < numNames; j++) {
            String[] total = new String[numFiles];
    
            if (genders[j] == 'f') {
                printIndex(femaleIndex, names[j]);
            } else {
                printIndex(maleIndex, names[j]);
            }
    
            for (int i = 0; i < numFiles; i++) {
                String stats = "";
                if (genders[j] == 'f') {
                    stats = printStats(femaleNames, names[j], years[i], 'F');
                } else if (genders[j] == 'm') {
                    stats = printStats(maleNames, names[j], years[i], 'M');
                }
                if (stats != "") {
                    System.out.println(stats);
                }
            }
    
            // Print the total for the current name outside the inner loop
            String totalStats = "";
            if (genders[j] == 'f') {
                totalStats = printTotalStats(femaleRank, names[j], 'F');
            } else if (genders[j] == 'm') {
                totalStats = printTotalStats(maleRank, names[j], 'M');
            } 
            if (totalStats != "") {
                System.out.println(totalStats);
            }
        }
    }

    private static void updatePercentages(NameLL list, int year, int total) {
        NameLL.Node current = list.head.next;
        while (current != list.tail) {
            if (current.name != null && current.name.getYear() == year) {
                double percentage = (double) current.name.getNumber() / total;
                Name updatedName = new Name(current.name.getName(), current.name.getGender(), current.name.getYear(),
                        current.name.getRank(), current.name.getNumber(), percentage);
                current.name = updatedName;
            }
            current = current.next;
        }
    }

    private static void updateTotalPercentage(NameLL list, int total){
        NameLL.Node current = list.head.next;
        while (current != list.tail) {
            if (current.name != null) {
                double percentage = (double) current.name.getNumber() / total;
                Name updatedName = new Name(current.name.getName(), current.name.getGender(), current.name.getYear(),
                        current.name.getRank(), current.name.getNumber(), percentage);
                current.name = updatedName;
            }
            current = current.next;
        }
    }
    private static int getMaleTotal(NameLL list, int year) {
        int totalMaleNum = 0;
        NameLL.Node current = list.head.next;
        while (current != null) {
            if (current.name != null && current.name.getGender() == 'M' && current.name.getYear() == year) {
                totalMaleNum += current.name.getNumber();
            }
            current = current.next;
        }
        return totalMaleNum;
    }

    private static int getFemaleTotal(NameLL list, int year) {
        int totalFemaleNum = 0;
        NameLL.Node current = list.head.next;
        while (current != null) {
            if (current.name != null &&current.name.getGender() == 'F' && current.name.getYear() == year) {
                totalFemaleNum += current.name.getNumber();
            }
            current = current.next;
        }
        return totalFemaleNum;
    }

    private static int maleTotal(NameLL list){
        int totalMale = 0;
        NameLL.Node current = list.head.next;
        while (current != null){
            if (current.name != null){
                totalMale += current.name.getNumber();
            }
            current = current.next;
        }
        return totalMale;
    }

    private static int femaleTotal(NameLL list){
        int totalfemale = 0;
        NameLL.Node current = list.head.next;
        while (current != null){
            if (current.name != null){
                totalfemale += current.name.getNumber();
            }
            current = current.next;
        }
        return totalfemale;
    }

    private static void printIndex(NameLL list, String name){
        int index = list.index(name);
        if (index != -1){
        System.out.println(list.index(name));
        System.out.println();
        } else{
        System.out.println("That name isn't in our data.");
        }
    }

    private static String printStats(NameLL list, String name, int year, char gender) {
        int index = list.index(name);
        String result = "";
        if (index != -1) {
            NameLL.Node current = list.head.next;
            while (current != list.tail) {
                if (current.name.getName() != null && current.name.getName().equals(name) && current.name.getYear() == year && current.name.getGender() == gender) {
                    double[] stats = list.yearStats(name, year, gender);
                    result = current.name.getYear() + "\n" + name + ": " + (int) stats[0] + ", " + (int) stats[1] + ", " + String.format("%.6f", stats[2]) + "\n";
                    return result;
                }
                current = current.next;
            }
        } 
        return "";
    }

    private static String printTotalStats(NameLL list, String name, char gender) {
        int index = list.index(name);
        String result = "";
        if (index != -1) {
            double[] totalStats = list.totalStats(name, gender);
            if (totalStats != null) {
                result = "Total" + "\n" + name + ": " + (int) totalStats[0] + ", " + (int) totalStats[1] + ", " + String.format("%.6f", totalStats[2]);
                return result;
            }
        }
        return "";
    }
}