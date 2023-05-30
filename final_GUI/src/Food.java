
import javax.swing.*;
import java.util.Date;
import javax.swing.Icon;
public class Food {
    private String name;
    private String type;
    private Icon icon;
    private Date expiryDate;


    public void setFood(Food food)
    {
        this.name=food.getName();
        this.type = food.getType();
        this.icon = food.getIcon();
        this.expiryDate = food.getExpiryDate();
    }
    public Food(String name,  Icon icon, Date expiryDate){
        this.name = name;
        this.icon = icon;
        this.expiryDate = expiryDate;
    }
    //含type
    public Food(String name, String type, Icon icon, Date expiryDate){
        this.name = name;
        this.type = type;
        this.icon = icon;
        this.expiryDate = expiryDate;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    @Override
    public String toString(){
        return "Name: " + name ;
    }
}
