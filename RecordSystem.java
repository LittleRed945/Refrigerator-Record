package lib;
import java.util.ArrayList;
import java.util.Date;
import  java.util.concurrent.TimeUnit ;
import javax.swing.Icon;
import java.lang.IndexOutOfBoundsException;
import java.io.IOException;
import java.awt.AWTException;
import java.util.Comparator;
public class RecordSystem extends Notify{
    private static final String destinationDirectory = "icon/";
    private ArrayList<Food> foods ;
    private PhotoUploader photoUploader;
    public RecordSystem(){
        super();
        foods = new ArrayList<Food>();
        photoUploader = new PhotoUploader();
    }
    
    public void createFood(String name, String type, Icon icon, Date expiryDate){
        Food food = new Food(name, type, icon, expiryDate);
        foods.add(food);
        System.out.println("create food success!!");
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
    public void clearFoods(){
        foods.clear();
    }
    public Food getFood(int index) throws IndexOutOfBoundsException{
        if(foods.size() <= index){
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        }
        return foods.get(index);
    }
    public ArrayList<Food> getFoods() throws IndexOutOfBoundsException{
        return foods;
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
    public void expiriedNotify(){
        try{
            Date current_time= new Date();
            for(int i=0;i<foods.size();++i){
                long diffInMillies = foods.get(i).getExpiryDate().getTime() - current_time.getTime();
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if(diff <= 1 && diff >= 0){
                    String msg = "Your "+foods.get(i).getName() + " is about to expiried!";
                    windowsNotify(msg);
                }
            }
        }catch(AWTException aWTException){  
            System.out.println(aWTException.getMessage());
        }
    }
    
   public void sortFood(String sortMethod)
    {
        Food tempFood;
        switch (sortMethod) {
            case "類型":
                foods.sort(Comparator.comparing(Food::getType));
                break;
            case "有效日期":
                foods.sort(Comparator.comparing(Food::getExpiryDate));
                break;
        }
        
    }
}