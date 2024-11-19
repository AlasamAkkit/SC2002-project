package HMS.Manager;

import java.io.*;
import java.util.*;

/**
 * The FileManager class handles reading from and writing to files, abstracting the file I/O operations.
 */
public class FileManager {

    /**
     * Reads data from a CSV file and returns it as a list of strings.
     * @param filePath the path to the CSV file
     * @return a list of string arrays, each representing a row in the CSV file
     */
    public List<String[]> readFile(String filePath) {
        List<String[]> data = new ArrayList<>();
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return data;
    }

    /**
     * Writes a list of objects to a CSV file.
     * @param filePath the path to the CSV file
     * @param data the data to write to the file
     */
    public <T> void writeFile(String filePath, List<T> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (T item : data) {
                bw.write(item.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
}
