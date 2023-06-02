import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

 
/**
 * Custom renderer to display a country's flag alongside its name
 *
 * @author wwww.codejava.net
 */
public class ItemIconRenderer extends JLabel implements ListCellRenderer<String> {
 
    public ItemIconRenderer() {
        setOpaque(true);
    }
    
    private ImageIcon createIcon(String icon_name){
        ImageIcon imageIcon = new ImageIcon("icon/" + icon_name);
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(20  , 20, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        return  imageIcon;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String icon_name, int index,
            boolean isSelected, boolean cellHasFocus) {
 
        
        ImageIcon imageIcon = createIcon(icon_name);
 
        setIcon(imageIcon);
        setText(icon_name);
 
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
 
        return this;
    }
} 