import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;
import java.util.Timer;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.awt.Dimension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
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
    private JLabel[] itemFields;
    private Icon[] icons;
    private JScrollPane scrollPane;//滾動條
    private JComponent itemViewer;
    private JPanel viewPanel;
    private JPanel itemPanel;//儲存物品的地方
    private JTable itemTable;//儲存物品的表格
    private String[] tableColumnNames = {"名稱", "類型", "有效日期"};
    private JLabel createLabel;
    private JPanel createPanel;//創建物品
    private JTextField newItemName;
    private JTextField newItemType;
    private JTextField newItemDate;
    private JTextField newItemIcon;
    private ArrayList<String> photoNames;
    private JLabel bottomLabel;
    private JPanel bottomPanel;//底部:日期、設置、新增
    private JButton showByButton;//顯示方式切換

    private JButton sortByButton;//排序方式
    private JButton createItemButton;//按鈕:新增物品
    private JButton uploadPhotoButton;//按鈕:上傳照片
    private JTextField timeField;//現在日期
    private RecordSystem recordSystem;//系統
    private ItemInfoReader reader;//仿作Writer
    private ItemInfoWriter writer;//仿作Reader
    private String displayMethod = "圖片";//顯示方式
    private String sortMethod = "類型";//排序方式
    private int item_counts;
    private Food[] allFood={null};
    Object[][] testData = {
            {"John", 25, "USA"},
            {"Emily", 30, "Canada"},
            {"David", 35, "UK"},
            {"Sophia", 28, "Australia"}
    };
    private JPanel outerPanel;

    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Constructor
    public GUI() {
        super("冰箱記帳");
        outerPanel = new JPanel();
        recordSystem = new RecordSystem();
        item_counts = 0;
        photoNames = new ArrayList<String>();
        Date today = new Date();
        //每隔1小時判斷是否有東西要過期
        Timer t = new Timer();
        long delay = 0L;
        long period = 3600000L;


        outerPanel.setLayout(new GridLayout(8, 1));
        Handler handler = new Handler();
        //------top------
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        showByButton = new JButton("以" + "圖片" + "顯示");
        showByButton.addActionListener(handler);
        sortByButton = new JButton("排序依據：" + "有效日期");
        sortByButton.addActionListener(handler);
        //設定按鈕大小
        showByButton.setBorder(new EmptyBorder(20, 20, 20, 20));
        sortByButton.setBorder(new EmptyBorder(20, 20, 20, 20));

        topPanel.add(showByButton, BorderLayout.WEST);
        topPanel.add(sortByButton, BorderLayout.EAST);

        //------item------
        itemTable=new JTable(testData,tableColumnNames);

        itemLabel = new JLabel("冰箱內:");
        itemPanel = new JPanel();

        itemPanel.setLayout(new GridLayout(ITEM_ROW , ITEM_COLUMN));
        itemFields = new JLabel[ITEM_ROW * ITEM_COLUMN];
        icons = new ImageIcon[ITEM_ROW];
        //導入item圖片
        for(int i =0 ;itemFields[i]!=null;i++)
        {
            itemPanel.add(itemFields[i]);
        }
        itemViewer=itemPanel;
        viewPanel = new JPanel();
        viewPanel.add(itemViewer);
        scrollPane = new JScrollPane(viewPanel);

//        for (int i = 0; i < ITEM_ROW * ITEM_COLUMN; i++) {
//            if ((i + 1) % ITEM_COLUMN == 0) {
//                icons[i / ITEM_COLUMN] = new ImageIcon("");
//                itemFields[i] = new JLabel(icons[i / ITEM_COLUMN]);
//            } else {
//                itemFields[i] = new JLabel();
//            }
//            itemPanel.add(itemFields[i]);
//        }

        //scrollPane = new JScrollPane(itemPanel);

        //------create------
        JTextField titleName = new JTextField("名稱");
        JTextField titleType = new JTextField("類型");
        JTextField titleDate = new JTextField("有效日期");
        JTextField titleIcon = new JTextField("圖片");
        titleName.setEditable(false);
        titleType.setEditable(false);
        titleDate.setEditable(false);
        titleIcon.setEditable(false);
        newItemName = new JTextField();
        newItemType = new JTextField();
        newItemDate = new JTextField("yyyy-MM-dd");
        newItemIcon = new JTextField();

        createLabel = new JLabel("新增物品到冰箱:");
        createPanel = new JPanel();
        createPanel.setLayout(new GridLayout(2, 4));
        createPanel.add(titleName);
        createPanel.add(titleType);
        createPanel.add(titleDate);
        createPanel.add(titleIcon);
        createPanel.add(newItemName);
        createPanel.add(newItemType);
        createPanel.add(newItemDate);
        createPanel.add(newItemIcon);

        //------bottom------
        bottomLabel = new JLabel("底部(test)");
        bottomPanel = new JPanel();
        Date nowTime = new Date();
        timeField = new JTextField(nowTime.toString());
        timeField.setEditable(false);
        timeField.setText("現在日期：" + dateFormat.format(today));
        createItemButton = new JButton("新增紀錄");
        createItemButton.setPreferredSize(new Dimension(20, 20));
        createItemButton.addActionListener(handler);
        uploadPhotoButton = new JButton("上傳照片");
        uploadPhotoButton.setPreferredSize(new Dimension(20, 20));
        uploadPhotoButton.addActionListener(handler);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(timeField, BorderLayout.WEST);
        bottomPanel.add(createItemButton);
        bottomPanel.add(uploadPhotoButton);

        //------帶進outer------
        updateAll();

        t.schedule(new TimerTask() {
            @Override
            public void run() {
                recordSystem.expiriedNotify();
            }
        }, 0L, period);
    }
    //重整
    private void updateAll() {
        while (true) {
            try {
                readFile();
                break;
            } catch (NoSuchFileException e) {
                try {
                    File records = new File(RECORDS_PATH);
                    records.mkdirs();
                    records.createNewFile();

                } catch (IOException iOException) {
                    System.out.println("An error occurred.");
                    iOException.printStackTrace();
                }
            }
        }
        remove(outerPanel);
        scrollPane.removeAll();
        itemPanel.removeAll();
        outerPanel=new JPanel();
        outerPanel.setLayout(new GridLayout(8, 1));
        outerPanel.add(topPanel);
        outerPanel.add(itemLabel);
        switch (displayMethod) {
            case "圖片":
                scrollPane=new JScrollPane(itemPanel);
            break;
            case "表格":
                scrollPane=new JScrollPane(itemTable);
            break;
        }
        outerPanel.add(scrollPane);
        outerPanel.add(createLabel);
        outerPanel.add(createPanel);
        outerPanel.add(createItemButton);
        outerPanel.add(bottomLabel);
        outerPanel.add(bottomPanel);
        add(outerPanel);
        //帶進GUI------
//
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void createFood(String name, String type, String date_str, String icon_path) {
        try {
            System.out.println("PATH:" + icon_path);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = simpleDateFormat.parse(date_str);
            String filename = recordSystem.uploadPhoto(icon_path);
            ImageIcon icon = new ImageIcon(destinationDirectory + filename);
            Image image = icon.getImage(); // transform it
            Image newimg = image.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newimg);
            recordSystem.createFood(name, type, icon, date);
            Food item = recordSystem.getFood(item_counts);
            allFood[item_counts]=item;
//            itemFields[item_counts * ITEM_COLUMN].setText(item.getName());
            //itemfields[item_counts*3 + 1] = gettype
//            itemFields[item_counts * ITEM_COLUMN + 2].setText(simpleDateFormat.format(item.getExpiryDate()));
            icons[item_counts] = item.getIcon();
            itemFields[item_counts].setIcon(icons[item_counts]);
//            if (item_counts + 1 < ITEM_ROW) {
                item_counts++;
//            }


        } catch (ParseException parseException) {
            System.out.println(parseException.getMessage());
        } catch (IOException iOException) {
            System.out.println(iOException.getMessage());
        }
    }

    private void readFile() throws NoSuchFileException {
        System.out.println("嘗試讀檔");
        try (Scanner input = new Scanner(Paths.get(RECORDS_PATH))) {
            // read record from file
            while (input.hasNext()) { // while there is more to read
                createFood(input.next(), input.next(), input.next(), input.next());
            }
        } catch (NoSuchFileException noSuchFileException) {
            throw new NoSuchFileException(RECORDS_PATH);
        } catch (IOException | NoSuchElementException |
                 IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void writeFile(String name, String type,  String icon_path,String date_str) throws FileNotFoundException {
        System.out.println("嘗試寫入檔案");
        try (Formatter output = new Formatter(RECORDS_PATH)) {
            try {
                // output new record to file; assumes valid input
                output.format("%s %s %s %s%n", name, type,  icon_path,date_str);
            } catch (NoSuchElementException elementException) {
                System.err.println("Invalid input. Please try again.");
            } catch (SecurityException |
                     FormatterClosedException e) {
                e.printStackTrace();
            }
        }
    }

    private void displaySwitcher() //切換顯示方式
    {
        // TODO
        scrollPane.remove(itemViewer);
        switch (displayMethod) {
            case "圖片":
                displayMethod = "表格";
                showByButton.setText("以" + displayMethod + "顯示");
                System.out.println("切到表格顯示");
                break;
            case "表格":
                displayMethod = "圖片";
                showByButton.setText("以" + "圖片" + "顯示");
                System.out.println("切到圖片顯示");
                break;
        }
        updateAll();
    }
    private void sortSwitcher()//切換排序方式
    {
        // TODO

        sortByButton.setText("排序依據：" + sortMethod);
        switch (sortMethod) {
            case "類型":
                sortMethod = "有效日期";
                sortByButton.setText("排序依據：" + sortMethod);
                System.out.println("切到以日期排序");
                break;
            case "有效日期":
                sortMethod = "類型";
                sortByButton.setText("排序依據：" + sortMethod);
                System.out.println("切到以類型排序");
                break;
        }
        for(int i = 1;allFood[i] != null;i++)
        {
            for(int j = 0; j<i;j++)
            {
                switch (sortMethod) {
                    case "類型":
                        if (allFood[i].getType()!=allFood[j].getType())
                        {

                        }
                        break;
                    case "有效日期":
                        if (allFood[i].getExpiryDate()!=allFood[j].getExpiryDate())
                        {

                        }
                        break;
                }
            }
        }
    }

    private void createAction() throws FileNotFoundException //新增紀錄
    {
        // TODO
            String name = newItemName.getText();
            String type = newItemType.getText();
            String date_str = newItemDate.getText();
            String icon_path = newItemIcon.getText();
            createFood(name, type, date_str, icon_path);
            writeFile(name, type, date_str, icon_path);
        try {
            readFile();
        } catch (NoSuchFileException e) {
            System.out.println("An error occurred.");
        }

    }

    private void uploadAction()//新增
    {
        // TODO

        JFileChooser fileChooser = new JFileChooser();//宣告filechooser
        fileChooser.addChoosableFileFilter(new ImageFilter());//只能選照片
        fileChooser.setAcceptAllFileFilterUsed(false);//只能選照片
        int returnValue = fileChooser.showOpenDialog(null);//叫出filechooser
        if (returnValue == JFileChooser.APPROVE_OPTION) //判斷是否選擇檔案
        {
            try {
                File selectedFile = fileChooser.getSelectedFile();//指派給File
                System.out.println(selectedFile.getName()); //印出檔名
                recordSystem.uploadPhoto(selectedFile.getPath());//上傳照片
            } catch (IOException | SecurityException |
                     FormatterClosedException err) {
                err.printStackTrace();
            }
        }
    }

    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == showByButton) {
                displaySwitcher();
            } else if (e.getSource() == sortByButton) {
                sortSwitcher();
            } else if (e.getSource() == createItemButton) {
                try {
                    createAction();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (e.getSource() == uploadPhotoButton) {
                uploadAction();
            }
        }
    }
}