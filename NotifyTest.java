import java.awt.*;
import java.awt.TrayIcon.MessageType;
import lib.Notify;
class NotifyTest{
    public static void main(String[] args) throws AWTException {
        if (SystemTray.isSupported()) {
            Notify notify = new TestNotify();
            notify.windowsNotify("你有一項食物快過期了");
           
        } else {
            System.err.println("System tray not supported!");
        }
    }
    static class TestNotify extends Notify{

    }
}