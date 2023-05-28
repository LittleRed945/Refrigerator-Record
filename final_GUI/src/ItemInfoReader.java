
import java.io.IOException;
import java.lang.IllegalStateException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ItemInfoReader {
    private Scanner input;
    private String fileName; // target file name
    private static final String destinationDirectory = "icon/";
    private PhotoUploader photoUploader = new PhotoUploader();
    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public ItemInfoReader(String fileName) {
        this.fileName = fileName;
    }
    public String[][] readAllFood() {
        openFile();
        ArrayList<Food> list = readRecords();
        String[][] listArray = convertArrayList2Array(list);
        closeFile();

        return listArray;
    }
    public void openFile() {
        try {
            input = new Scanner(Paths.get(fileName));
        } catch (IOException ioException) {
            System.err.println("Error opening file. Terminating.");
            System.exit(1);
        }
    }
    public ArrayList<Food> readRecords() {
        ArrayList<Food> list = new ArrayList<Food>();
        try {
            while (input.hasNext()) // while there is more to read
            {
                String foodName = input.next();
                String foodType = input.next();

                //這邊做轉換
                String icon_path = input.next();
                String date_str = input.next();
                try {
                    // 解析字符串為日期對象
                    Date date = dateFormat.parse(date_str);
                    //存疑
                    Icon icon = new ImageIcon(destinationDirectory + photoUploader);

                    Food food = new Food(foodName, foodType,  icon,date);
                    list.add(food);


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (NoSuchElementException elementException) {
            System.err.println("File improperly formed. Terminating.");
        } catch (IllegalStateException stateException) {
            System.err.println("Error reading from file. Terminating.");
        }

        return list;
    } // end method readRecords
    public String[][] convertArrayList2Array(ArrayList<Food> list) {
        int size = list.size();
        String[][] listArray = new String[size][];

        for (int i = 0; i < size; i++) {
            String[] record = new String[4];
            Food account = list.get(i);

            record[0] = account.getName();
            record[1] = account.getType();
            record[2] = dateFormat.format(account.getExpiryDate());
            record[3] = null;

            listArray[i] = record;
        }

        return listArray;
    }
    public void closeFile() {
        if (input != null)
            input.close();
    }
    public String uploadPhoto(String sourceFilePath)  throws IOException{
        try {
            String filename = photoUploader.uploadPhoto(sourceFilePath, destinationDirectory);
            System.out.println("Photo uploaded successfully!");
            return filename;
        } catch (IOException e) {
            System.out.println("Failed to upload photo: " + e.getMessage());
            throw new IOException(e.getMessage());
        }

    }
}
