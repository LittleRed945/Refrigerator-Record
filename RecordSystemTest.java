package test.java;
import lib.RecordSystem;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.lang.IndexOutOfBoundsException;
import java.io.IOException;
class RecordSystemTest{
    private static final String sourceFilePath = "test/icon/test.jpg";
    public static void main(String[] args) {
        RecordSystem recordSystem = new RecordSystem();
        ImageIcon icon = new ImageIcon("icon/test.jpg");
        try{
           System.out.println(recordSystem.getFood(0).toString());
        }catch(IndexOutOfBoundsException indexOutOfBoundsException){
            System.out.println(indexOutOfBoundsException.getMessage());
        }
        System.out.println();

        //createFood
        System.out.println("Create Food:");
        recordSystem.createFood("01", icon, new Date());
        try{
            System.out.println(recordSystem.getFood(0).toString());
        }catch(IndexOutOfBoundsException indexOutOfBoundsException){
            System.out.println(indexOutOfBoundsException.getMessage());
        }
        System.out.println();

        //editFood
        System.out.println("Edit Food:");
        recordSystem.editFood("SECOND", icon, new Date(), 0);
        try{
            System.out.println(recordSystem.getFood(0).toString());
        }catch(IndexOutOfBoundsException indexOutOfBoundsException){
            System.out.println(indexOutOfBoundsException.getMessage());
        }
        System.out.println();

        //deleteFood
        System.out.println("Delete Food:");
        recordSystem.deleteFood( 0);
        try{
            System.out.println(recordSystem.getFood(0).toString());
        }catch(IndexOutOfBoundsException indexOutOfBoundsException){
            System.out.println(indexOutOfBoundsException.getMessage());
        }
        System.out.println();

        //uploadPhoto
        try{
            recordSystem.uploadPhoto(sourceFilePath);
        }catch(IOException iOException){
            System.out.println(iOException.getMessage());
        }
        
            
    }
    
}