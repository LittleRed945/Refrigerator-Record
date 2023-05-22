import javax.swing.*;
import java.util.Date;

public class Food {
    private String name;
    private Icon icon;
    private Date expiryDate;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
