package lib;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.Icon;
import java.lang.IndexOutOfBoundsException;
import java.io.IOException;
public class RecordSystem extends Notify{
    private static final String destinationDirectory = "icon/";
    private ArrayList<Food> foods ;
    private PhotoUploader photoUploader;
    public RecordSystem(){
        super();
        foods = new ArrayList<Food>();
        photoUploader = new PhotoUploader();
    }
    
    public void createFood(String name, Icon icon, Date expiryDate){
        Food food = new Food(name, icon, expiryDate);
        foods.add(food);
    }
    public void editFood(String name, Icon icon, Date expiryDate, int index){
        Food food = foods.get(index);
        food.setName(name);
        food.setIcon(icon);
        food.setExpiryDate(expiryDate);
        foods.set(index,food);
    }
    public void deleteFood(int index){
        foods.remove(index);
    }
    public Food getFood(int index) throws IndexOutOfBoundsException{
        if(foods.size() <= index){
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        }
        return foods.get(index);
    }
    public void uploadPhoto(String sourceFilePath){
         try {
            photoUploader.uploadPhoto(sourceFilePath, destinationDirectory);
            System.out.println("Photo uploaded successfully!");
        } catch (IOException e) {
            System.out.println("Failed to upload photo: " + e.getMessage());
        }
        
    }
    //上傳到固定的位置?
}