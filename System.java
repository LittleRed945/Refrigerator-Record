package lib.system;
import java.util.ArrayList;
public class System extends Notify{
    private ArrayList<Food> foods ;
    System(){
        super();
        ArrayList<Food> = new ArrayList<Food>();
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
    public deleteFood(int index){
        foods.remove(index);
    }
}