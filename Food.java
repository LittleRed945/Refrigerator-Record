package lib;
import javax.swing.*;
import java.util.Date;
import javax.swing.Icon;
public class Food {
    private String name;
    private String type;
    private String icon;
    private Date expiryDate;
    
    public Food(String name, String type, String  icon, Date expiryDate){
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

    public String  getIcon() {
        return icon;
    }

    public void setIcon(String  icon) {
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
