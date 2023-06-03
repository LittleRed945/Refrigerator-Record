import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.FormatterClosedException;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.IllegalStateException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class DeletePhotoWindow {
    private static final String PHOTOLIST_PATH = "record/photoList.txt";
    JFrame frame = new JFrame("刪除照片");
    private JList<String> iconList;
    private JButton deletePhotoButton;
    private JScrollPane iconScrollPane;
    private ArrayList<String> photoList;
    DeletePhotoWindow(){
    frame.setLayout(new BorderLayout());
    
    iconList = new JList<String>();
    iconList.setCellRenderer(new ItemIconRenderer());
    iconScrollPane = new JScrollPane(iconList);

    deletePhotoButton = new JButton("刪除");
    deletePhotoButton.addActionListener(new Handler());
    try{
    readPhotoList();
    }catch(NoSuchFileException e){
        e.printStackTrace();
    }

    frame.add(iconScrollPane, BorderLayout.CENTER);
    frame.add(deletePhotoButton, BorderLayout.EAST);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(420,420);
    frame.setLayout(null);
    frame.setVisible(true);
    }
    private void readPhotoList() throws NoSuchFileException {
        System.out.println("嘗試讀檔");
        try (Scanner input = new Scanner(Paths.get(PHOTOLIST_PATH))) {
            // read record from file
            photoList = new ArrayList<String>();
            while (input.hasNextLine()) { // while there is more to read
                photoList.add(input.nextLine());
            }
            String[] tmp = photoList.toArray(new String[0]);
            iconList.setListData(tmp);
        } catch (NoSuchFileException noSuchFileException) {
            throw new NoSuchFileException(PHOTOLIST_PATH);
        } catch (IOException | NoSuchElementException |
                 IllegalStateException e) {
            e.printStackTrace();
        }
    }
    private void writePhotoList() throws FileNotFoundException {
        System.out.println("嘗試寫入檔案");
        try (Formatter output = new Formatter(PHOTOLIST_PATH)) {
            try {
                // output new record to file; assumes valid input
                for (int i = 0; i < photoList.size(); i++) {
                    output.format("%s%n", photoList.get(i));
                }

            } catch (NoSuchElementException elementException) {
                System.err.println("Invalid input. Please try again.");
            } catch (SecurityException |
                     FormatterClosedException e) {
                e.printStackTrace();
            }
        }
    }
    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent err) {
            if(photoList!=null){
                photoList.remove(iconList.getSelectedIndex());
                try{
                    writePhotoList();
                    readPhotoList();
                }catch(FileNotFoundException | NoSuchFileException e){
                     e.printStackTrace();
                }
            }
        }
    }
}