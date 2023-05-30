package lib;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.SecurityException;
import java.util.Date;
import java.util.Formatter;
import java.util.FormatterClosedException;
public class ItemInfoWriter {
    private static Formatter output; // outputs text to a file
    private String fileName; // target file name
    public ItemInfoWriter(String fileName) {
        this.fileName = fileName;
    }

    public void createFood(String name, String type, String date_str, String icon_path) {//接收String，需先轉換
        openFile();
        addRecord(name, type, date_str, icon_path);
        closeFile();
    }

    public void openFile() {
        try {
            FileWriter fw = new FileWriter(fileName, true);
            output = new Formatter(fw);
        } catch (SecurityException securityException) {
            System.err.println("Write permission denied. Terminating.");
            System.exit(1); // terminate the program
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error opening file. Terminating.");
            System.exit(1); // terminate the program
        } catch (IOException e) {
            System.err.println("I/O error. Terminating.");
            System.exit(1); // terminate the program
        }
    }
    public void addRecord(String name, String type, String date_str, String icon_path) {
        try {
            // output new record to file; assumes valid input
            // TODO
            output.format("%s %s %s %s%n", name, type, date_str, icon_path);
            output.flush(); // 刷新輸出

        } catch (FormatterClosedException formatterClosedException) {
            System.err.println("Error writing to file. Terminating.");
        }
    }
    public static void closeFile() {
        if (output != null)
            output.close();
    }
}
