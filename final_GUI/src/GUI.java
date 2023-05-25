import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;

public class GUI extends JFrame {
    private JLabel topLabel;
    private JPanel topPanel;//頂部:顯示方式、排序方式
    private JLabel itemLabel;
    private JPanel itemPanel;//儲存物品的地方
    private JLabel createLabel;
    private JPanel createPanel;//創建物品
    private JLabel bottomLabel;
    private JPanel bottomPanel;//底部:日期、設置、新增
    private JButton showByButton;//顯示方式切換
    private JButton sortByButton;//排序方式
    private JButton createItemButton;//按鈕:新增物品
    private JTextField timeField;//現在日期
    JTextField titleName=new JTextField("名稱");
    JTextField titleType=new JTextField("類型");
    JTextField titleDate=new JTextField("有效日期");
    JTextField titleIcon=new JTextField("圖片");
    JTextField newItemName=new JTextField();
    JTextField newItemType=new JTextField();
    JTextField newItemDate=new JTextField();
    JTextField newItemIcon=new JTextField();

    public GUI() {
        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new GridLayout(7, 1));
        ActionListener display = new displaySwitcher();
        ActionListener sort = new sortSwitcher();
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
        itemPanel.setLayout(new GridLayout(3,6));
        //------create------
        titleName.setEditable(false);
        titleType.setEditable(false);
        titleDate.setEditable(false);
        titleIcon.setEditable(false);

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
        outerPanel.add(itemPanel);
        outerPanel.add(createLabel);
        outerPanel.add(createPanel);
        outerPanel.add(bottomLabel);
        outerPanel.add(bottomPanel);
        //帶進GUI------
        add(outerPanel);

    }
    public void createSystem()
    {

    }
    public void createFood()
    {
        Food newFood = new Food();
        newFood.setName(newItemName.getText());
        //newFood.setExpiryDate(newItemDate.getText());

    }
    public void editFood()
    {}
    public void deleteFood()
    {}


    private class displaySwitcher implements ActionListener //切換顯示方式
    {
        public void actionPerformed(ActionEvent e) {
            showByButton.setText("以" + "表格" + "顯示");
        }
    }

    private class sortSwitcher implements ActionListener //切換排序方式
    {
        public void actionPerformed(ActionEvent e) {
            sortByButton.setText("排序依據：" + "類型");
        }
    }
    private class createFoodButton implements ActionListener //切換顯示方式
    {
        public void actionPerformed(ActionEvent e) {
            showByButton.setText("以" + "表格" + "顯示");
        }
    }
}
