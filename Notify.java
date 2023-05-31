package lib;
import java.awt.*;
import java.awt.TrayIcon.MessageType;

abstract public class Notify{
     public void windowsNotify(String msg) throws AWTException {
         System.out.println("expiredNotify");
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        trayIcon.displayMessage("Refrigerator Record", msg, MessageType.INFO);
        tray.remove(trayIcon);
    }
} 