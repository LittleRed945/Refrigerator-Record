
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import  java.util.concurrent.TimeUnit ;
import javax.swing.Icon;
import java.lang.IndexOutOfBoundsException;
import java.io.IOException;
import java.awt.AWTException;

public class RecordSystem extends Notify{
    private static final String destinationDirectory = "icon/";
    public ArrayList<Food> foods ;
    private PhotoUploader photoUploader;
    public RecordSystem(){
        super();
        foods = new ArrayList<Food>();
        photoUploader = new PhotoUploader();
    }
    public  ArrayList<Food> getFoods()
        {
            return this.foods;
        }
    
    public void createFood(String name, String type, Icon icon, Date expiryDate){
        Food food = new Food(name,type , icon, expiryDate);
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
                long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if(diff <= 1 && diff >= 0){
                    String msg = "Your "+foods.get(i).getName() + "is about to expiried!";
                    windowsNotify(msg);
                }
            }
        }catch(AWTException aWTException){  
            System.out.println(aWTException.getMessage());
        }
    }

    //排序
    public void sortFood(String sortMethod)
    {
        Food tempFood;
        for(int i = 1;foods.get(i) != null;i++)
        {
            for(int j = 0; j<i;j++)
            {
                switch (sortMethod) {
                    case "類型":
                        if (foods.get(i).getType()!=foods.get(j).getType())
                        {
                            tempFood=foods.get(i);
                            foods.get(i).setFood(foods.get(j));
                            foods.get(j).setFood(tempFood);
                        }
                        break;
                    case "有效日期":
                        if (foods.get(i).getExpiryDate()!=foods.get(j).getExpiryDate())
                        {
                            tempFood=foods.get(i);
                            foods.get(i).setFood(foods.get(j));
                            foods.get(j).setFood(tempFood);
                        }
                        break;
                }
            }
        }
    }
    //上傳到固定的位置?
}