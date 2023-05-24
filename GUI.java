import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.TimerTask;
import java.util.Timer;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Formatter;
import java.util.FormatterClosedException;
import lib.RecordSystem;
import lib.Food;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
public class GUI extends JFrame {
    private static final String destinationDirectory = "icon/";
    private static final String RECORDS_PATH = "record/records.txt";
    private static final int ITEM_ROW = 3; // 顯示的 row 數量
    private static final int ITEM_COLUMN = 4;// 顯示的 column 數量
    private JLabel topLabel;
    private JPanel topPanel;//頂部:顯示方式、排序方式
    private JLabel itemLabel;
    private JLabel[] itemfields;
    private Icon[] icons;
    private JPanel itemPanel;//儲存物品的地方
    private JLabel createLabel;
    private JPanel createPanel;//創建物品
    private JTextField newItemName;
    private JTextField newItemType;
    private JTextField newItemDate;
    private JTextField newItemIcon;
    private JLabel bottomLabel;
    private JPanel bottomPanel;//底部:日期、設置、新增
    private JButton showByButton;//顯示方式切換
    private JButton sortByButton;//排序方式
    private JButton createItemButton;//按鈕:新增物品
    private JTextField timeField;//現在日期
    private RecordSystem recordSystem;//系統
     private int item_counts;
    public GUI() {
        recordSystem = new RecordSystem();
        item_counts = 0;
        
        //每隔1小時判斷是否有東西要過期
        Timer t = new Timer();
        long delay = 0L; 
        long period = 3600000L;
        t.schedule(new TimerTask() {
            @Override public void run() {
                recordSystem.expiriedNotify();
            }
        }, 0L, period);

        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new GridLayout(8, 1));
        ActionListener display = new displaySwitcher();
        ActionListener sort = new sortSwitcher();
        ActionListener create = new createAction();
        //------top------
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        showByButton = new JButton("以" + "圖像" + "顯示");
        showByButton.addActionListener(display);
        sortByButton = new JButton("排序依據：" + "有效日期");
        sortByButton.addActionListener(sort);

        topPanel.add(showByButton,BorderLayout.WEST);
        topPanel.add(sortByButton,BorderLayout.EAST);
        //------item------
        itemLabel=new JLabel("冰箱內:");
        itemPanel = new JPanel();
        itemPanel.setLayout(new GridLayout(ITEM_ROW, ITEM_COLUMN));
        itemfields = new JLabel[ITEM_ROW * ITEM_COLUMN];
        icons = new ImageIcon[ITEM_ROW];
        for(int i=0;i<ITEM_ROW * ITEM_COLUMN;i++){
            if((i+1)%ITEM_COLUMN==0){
                icons[i/ITEM_COLUMN] = new ImageIcon("");
                itemfields[i] = new JLabel(icons[i/ITEM_COLUMN]);
            }else{
                 itemfields[i] = new JLabel();
            }
            itemPanel.add(itemfields[i]);
        }
        JScrollPane scroller = new JScrollPane(itemPanel);
        //------create------
        JTextField titleName=new JTextField("名稱");
        JTextField titleType=new JTextField("類型");
        JTextField titleDate=new JTextField("有效日期");
        JTextField titleIcon=new JTextField("圖片");
        titleName.setEditable(false);
        titleType.setEditable(false);
        titleDate.setEditable(false);
        titleIcon.setEditable(false);
        newItemName=new JTextField();
        newItemType=new JTextField();
        newItemDate=new JTextField("2023/05/24");
        newItemIcon=new JTextField();
        createLabel = new JLabel("新增物品到冰箱:");
        createPanel = new JPanel();
        createPanel.setLayout(new GridLayout(2,4));
        createPanel.add(titleName);
        createPanel.add(titleType);
        createPanel.add(titleDate);
        createPanel.add(titleIcon);
        createPanel.add(newItemName);
        createPanel.add(newItemType);
        createPanel.add(newItemDate);
        createPanel.add(newItemIcon);
        createItemButton = new JButton("新增");
        createItemButton.setPreferredSize(new Dimension(40, 40));
        createItemButton.addActionListener(create);
        //------bottom------
        bottomLabel = new JLabel("底部(test)");
        bottomPanel = new JPanel();
        timeField = new JTextField();
        timeField.setEditable(false);
        timeField.setText("現在日期:");
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(timeField, BorderLayout.WEST);
        //------帶進outer------
        outerPanel.add(topPanel);
        outerPanel.add(itemLabel);
        //outerPanel.add(itemPanel);
        outerPanel.add(scroller);
        outerPanel.add(createLabel);
        outerPanel.add(createPanel);
        outerPanel.add(createItemButton);
        outerPanel.add(bottomLabel);
        outerPanel.add(bottomPanel);
        //帶進GUI------
        add(outerPanel);
        while(true){
            try{
                readFile();
                break;
            }catch(NoSuchFileException e){
                try{
                    File records = new File(RECORDS_PATH);
                    records.createNewFile();
                }catch (IOException iOException) {
                    System.out.println("An error occurred.");
                    iOException.printStackTrace();
                }
            }
        }
       
    }
    public void createFood( String name, String type, String date_str, String icon_path){
        try{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = simpleDateFormat.parse(date_str);
                String filename = recordSystem.uploadPhoto(icon_path);
                ImageIcon icon = new ImageIcon(destinationDirectory + filename);
                Image image = icon.getImage(); // transform it 
                Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
                icon = new ImageIcon(newimg);
                recordSystem.createFood(name, icon, date);
                Food item = recordSystem.getFood(item_counts);
                itemfields[item_counts*ITEM_COLUMN].setText(item.getName());
                //itemfields[item_counts*3 + 1] = gettype
                itemfields[item_counts*ITEM_COLUMN + 2] .setText(simpleDateFormat.format(item.getExpiryDate())) ;
                icons[item_counts] = item.getIcon();
                itemfields[item_counts*ITEM_COLUMN + 3].setIcon(icons[item_counts]);
                if(item_counts+1<ITEM_ROW){
                    item_counts++;
                }
                
            }catch(ParseException parseException){
                System.out.println(parseException.getMessage());
            }catch(IOException iOException){
                 System.out.println(iOException.getMessage());
            }
    }
    private void readFile() throws NoSuchFileException{
        try(Scanner input = new Scanner(Paths.get(RECORDS_PATH))) {
         // read record from file
         while (input.hasNext()) { // while there is more to read
            createFood(input.next(), input.next(), input.next(), input.next());   
         }       
      } 
      catch(NoSuchFileException noSuchFileException){
        throw new NoSuchFileException(RECORDS_PATH);
      }
      catch (IOException | NoSuchElementException | 
         IllegalStateException e) {
         e.printStackTrace();
      } 
    }
    private void writeFile(String name, String type, String date_str, String icon_path){
         try (Formatter output = new Formatter(RECORDS_PATH)) {
           
                try {
                // output new record to file; assumes valid input
                output.format("%s %s %s %s%n", name, type, date_str, icon_path);
                } 
                catch (NoSuchElementException elementException) {
                System.err.println("Invalid input. Please try again.");
                } 

                
            
        }
        catch (SecurityException | FileNotFoundException | 
            FormatterClosedException e) {
            e.printStackTrace();
        } 
    }
    private class displaySwitcher implements ActionListener //切換顯示方式
    {
        // TODO
        public void actionPerformed(ActionEvent e) {
            showByButton.setText("以" + "表格" + "顯示");
        }
    }

    private class sortSwitcher implements ActionListener //切換排序方式
    {
        // TODO
        public void actionPerformed(ActionEvent e) {
            sortByButton.setText("排序依據：" + "類型");
        }
    }

    private class createAction implements ActionListener //新增
    {
        // TODO
        public void actionPerformed(ActionEvent e) {
            String name = newItemName.getText();
            String type = newItemType.getText();
            String date_str = newItemDate.getText();
            String icon_path = newItemIcon.getText();
            createFood(name, type, date_str, icon_path);
            writeFile(name, type, date_str, icon_path);
        }
    }
}
