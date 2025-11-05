/*
 * @author nut
 */

package application_Frame_Administrator_ChildFrame;

import application_Constant.Constant;
import application_Controller.AddController;
import application_Controller.QueryController;
import application_Frame.Administrator_Frame;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("unused")
public class OrderManage_Frame extends JFrame implements Constant
{
    public OrderManage_Frame(int tabbed_index)
    {
        initComponents();
        Load_OrderManage_Frame(tabbed_index);
    }
    private void Load_OrderManage_Frame(int tabbed_index)
    {
        this.tabbedPane_OrderManage.setSelectedIndex(tabbed_index);
        this.tabbedPane_OrderManage.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                Administrator_Frame administrator_frame=new Administrator_Frame();
            }
        });
        this.setVisible(true);
    }

    public void checkCustomer()
    {
        if(CurrentCustomer_Name==null)
            JOptionPane.showMessageDialog(this,
                    "还没登记客户！","W-nut Tips", JOptionPane.PLAIN_MESSAGE);
    }

    //接受开订单的客户名
    public void setCustomerName(String cus_name)
    {
        CurrentCustomer_Name=cus_name;
        printOrder();
    }

    //填充数据到订单中
    private void printOrder()
    {
        //生成订单号，订单日期
        //填充客户信息（客户号，客户名，客户地址）
        //填充商品信息（客户选购的商品信息，即之前保存在Constant中的动态数组，使用后应清空）
        

        //查询数据库中的订单表，生成订单号
        queryController=new QueryController();
        order_num=queryController.QueryOrderNum();
        textField_OrderNum.setText(order_num);

        //获取开单时间
        date = new Date(System.currentTimeMillis());
        textField_OrderDate.setText(date.toString());

        //填充客户信息
        queryController.QueryCustomerInfo();
        int isAllowed=queryController.QueryCustomerByName(CurrentCustomer_Name, false);

        textField_CustomerNum.setText(CUSTOMER_INFO_ALL.get(0));
        textField_CustomerName.setText(CUSTOMER_INFO_ALL.get(1));
        textField_CustomerTel.setText(CUSTOMER_INFO_ALL.get(2));
        textField_OrderAddress.setText(CUSTOMER_INFO_ALL.get(3));

        //填充商品信息(商品号，商品名，商品价格，商品选购数量)
        DefaultTableModel tableModel=(DefaultTableModel) table_GoodsInfo.getModel();
        GOODS_INFO_SELECTED_HEADER.add("商品号");
        GOODS_INFO_SELECTED_HEADER.add("商品名");
        GOODS_INFO_SELECTED_HEADER.add("商品价格");
        GOODS_INFO_SELECTED_HEADER.add("购买数量");

        for(String str:GOODS_INFO_SELECTED_HEADER)
            tableModel.addColumn(str);

        for(String str:GOODS_SELECTED_NAME)
            queryController.QueryGoodsInfoByName(str);

        int goods_num=GOODS_SELECTED_NAME.size();
        Vector<String> vec_goodsInfo=new Vector<>();

        for(int i=0;i<goods_num;++i)
        {
            vec_goodsInfo.add(GOODS_INFO_NUM.get(i));//商品号
            vec_goodsInfo.add(GOODS_INFO_NAME.get(i));//商品名
            vec_goodsInfo.add(GOODS_INFO_PRICE.get(i).toString());//商品价格
            vec_goodsInfo.add(GOODS_SELECTED_CHOOSE_NUM.get(i));//商品数量
            Object[]objects=vec_goodsInfo.toArray();//购买数量
            tableModel.addRow(objects);
            vec_goodsInfo.clear();
        }
        GOODS_INFO_SELECTED_HEADER.clear();

        //填充总价格
        int row_num=GOODS_SELECTED_CHOOSE_NUM.size();
        for(int i=0;i<row_num;++i)
            total_Price+=Integer.parseInt(GOODS_SELECTED_CHOOSE_NUM.get(i))*GOODS_INFO_PRICE.get(i);
        textField_TotalMoney.setText(String.valueOf(total_Price));

    }

    //保存订单
    private void button_SaveOrder_ActionPerformed(ActionEvent e) 
    {
        //向数据库中的表写入数据
        //清空Constant中使用的有关ArrayList

        //写数据库中的Order表(订单号，客户号，日期)
        addController=new AddController();
        int cus_num=queryController.QueryCustomerNumByName(CurrentCustomer_Name);
        int isAddOrder=addController.addOrder(order_num,cus_num,date);
        switch (isAddOrder)
        {
            case SUCCESS -> {
                JOptionPane.showMessageDialog(this, "保存订单成功！",
                        "W-nut Tips", JOptionPane.PLAIN_MESSAGE);
            }
            case DATABASE_ERROR -> {
                JOptionPane.showMessageDialog(this, "保存订单失败！",
                        "W-nut Tips", JOptionPane.WARNING_MESSAGE);
            }
        }

        //写订单表中的商品信息(订单号，购买商品的商品号，购买商品的商品名，购买数量)
        int goods_RowNum=GOODS_SELECTED_NAME.size();
        for(int i=0;i<goods_RowNum;++i)
        {
            int isAddGoodsInfo = addController.addGoodsInfoInOrder(order_num, GOODS_INFO_NUM.get(i),
                    GOODS_SELECTED_NAME.get(i), GOODS_SELECTED_CHOOSE_NUM.get(i));
            switch (isAddGoodsInfo)
            {
                case SUCCESS -> {
                    System.out.println("商品信息保存成功！");
                }
                case DATABASE_ERROR -> {
                    System.out.println("商品信息保存失败!");
                }
            }
        }
        GOODS_INFO_HEADER.clear();
        GOODS_INFO_SELECTED_HEADER.clear();
        GOODS_INFO_NUM.clear();
        GOODS_INFO_NAME.clear();
        GOODS_INFO_PRICE.clear();
        GOODS_INFO_STORE_NUM.clear();
        GOODS_SELECTED_HEADER.clear();
        GOODS_SELECTED_NAME.clear();
        GOODS_SELECTED_CHOOSE_NUM.clear();

    }

    //开发票事件
    private void button_CheckInvoice_ActionPerformed(ActionEvent e)
    {
        InvoiceManage_Frame invoiceManage_frame=new InvoiceManage_Frame();
        invoiceManage_frame.setOrderInfo(CurrentCustomer_Name,order_num,total_Price);
        this.setVisible(false);
    }

    //初始化框架
    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - nut
        tabbedPane_OrderManage = new JTabbedPane();
        panel_QueryOrder = new JPanel();
        scrollPane1 = new JScrollPane();
        table_OrderInfo = new JTable();
        button_QueryAll = new JButton();
        button_QueryByOrderNum = new JButton();
        label_TipOutput = new JLabel();
        label_Order_num = new JLabel();
        textField_InputOrderNum = new JTextField();
        panel_AddOrder = new JPanel();
        label_OrderNum = new JLabel();
        label_CustomerNum = new JLabel();
        textField_CustomerNum = new JTextField();
        textField_OrderNum = new JTextField();
        label_OrderName = new JLabel();
        label_CustomerName = new JLabel();
        textField_CustomerName = new JTextField();
        label_OrderDate = new JLabel();
        textField_OrderDate = new JTextField();
        label_GoodsInfo = new JLabel();
        scrollPane2 = new JScrollPane();
        table_GoodsInfo = new JTable();
        label_OrderAddress = new JLabel();
        textField_OrderAddress = new JTextField();
        label9 = new JLabel();
        textField_TotalMoney = new JTextField();
        button_CheckInvoice = new JButton();
        label_CustomerTel = new JLabel();
        textField_CustomerTel = new JTextField();
        button_SaveOrder = new JButton();

        //======== this ========
        setTitle("W-nut OrderManage");
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== tabbedPane_OrderManage ========
        {

            //======== panel_QueryOrder ========
            {
                panel_QueryOrder.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing.
                border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder. CENTER
                , javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font
                .BOLD ,12 ), java. awt. Color. red) ,panel_QueryOrder. getBorder( )) ); panel_QueryOrder. addPropertyChangeListener (
                new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r"
                .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
                panel_QueryOrder.setLayout(null);

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(table_OrderInfo);
                }
                panel_QueryOrder.add(scrollPane1);
                scrollPane1.setBounds(10, 25, 305, 200);

                //---- button_QueryAll ----
                button_QueryAll.setText("\u5168\u90e8\u67e5\u8be2");
                panel_QueryOrder.add(button_QueryAll);
                button_QueryAll.setBounds(new Rectangle(new Point(320, 20), button_QueryAll.getPreferredSize()));

                //---- button_QueryByOrderNum ----
                button_QueryByOrderNum.setText("\u7cbe\u51c6\u67e5\u8be2");
                panel_QueryOrder.add(button_QueryByOrderNum);
                button_QueryByOrderNum.setBounds(new Rectangle(new Point(135, 265), button_QueryByOrderNum.getPreferredSize()));

                //---- label_TipOutput ----
                label_TipOutput.setText("\u67e5\u8be2\u8ba2\u5355\u4ec5\u663e\u793a\u8ba2\u5355\u53f7\u3001\u5ba2\u6237\u540d\u3001\u5f00\u8ba2\u5355\u65e5\u671f");
                panel_QueryOrder.add(label_TipOutput);
                label_TipOutput.setBounds(20, 5, 295, label_TipOutput.getPreferredSize().height);

                //---- label_Order_num ----
                label_Order_num.setText("\u8ba2\u5355\u53f7");
                panel_QueryOrder.add(label_Order_num);
                label_Order_num.setBounds(new Rectangle(new Point(15, 240), label_Order_num.getPreferredSize()));
                panel_QueryOrder.add(textField_InputOrderNum);
                textField_InputOrderNum.setBounds(15, 265, 90, textField_InputOrderNum.getPreferredSize().height);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel_QueryOrder.getComponentCount(); i++) {
                        Rectangle bounds = panel_QueryOrder.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel_QueryOrder.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel_QueryOrder.setMinimumSize(preferredSize);
                    panel_QueryOrder.setPreferredSize(preferredSize);
                }
            }
            tabbedPane_OrderManage.addTab("\u67e5\u8be2\u8ba2\u5355", panel_QueryOrder);

            //======== panel_AddOrder ========
            {
                panel_AddOrder.setLayout(null);

                //---- label_OrderNum ----
                label_OrderNum.setText("\u8ba2\u5355\u53f7");
                panel_AddOrder.add(label_OrderNum);
                label_OrderNum.setBounds(new Rectangle(new Point(5, 10), label_OrderNum.getPreferredSize()));

                //---- label_CustomerNum ----
                label_CustomerNum.setText("\u5ba2\u6237\u53f7");
                panel_AddOrder.add(label_CustomerNum);
                label_CustomerNum.setBounds(new Rectangle(new Point(5, 75), label_CustomerNum.getPreferredSize()));

                //---- textField_CustomerNum ----
                textField_CustomerNum.setEditable(false);
                panel_AddOrder.add(textField_CustomerNum);
                textField_CustomerNum.setBounds(55, 70, 85, textField_CustomerNum.getPreferredSize().height);

                //---- textField_OrderNum ----
                textField_OrderNum.setEditable(false);
                panel_AddOrder.add(textField_OrderNum);
                textField_OrderNum.setBounds(55, 5, 90, textField_OrderNum.getPreferredSize().height);

                //---- label_OrderName ----
                label_OrderName.setText("\u575a\u679c\u516c\u53f8\u8ba2\u8d27\u5355");
                panel_AddOrder.add(label_OrderName);
                label_OrderName.setBounds(160, 35, 95, 30);

                //---- label_CustomerName ----
                label_CustomerName.setText("\u5ba2\u6237\u540d");
                panel_AddOrder.add(label_CustomerName);
                label_CustomerName.setBounds(new Rectangle(new Point(165, 75), label_CustomerName.getPreferredSize()));

                //---- textField_CustomerName ----
                textField_CustomerName.setEditable(false);
                panel_AddOrder.add(textField_CustomerName);
                textField_CustomerName.setBounds(220, 70, 130, textField_CustomerName.getPreferredSize().height);

                //---- label_OrderDate ----
                label_OrderDate.setText("\u8ba2\u8d27\u65e5\u671f");
                panel_AddOrder.add(label_OrderDate);
                label_OrderDate.setBounds(new Rectangle(new Point(5, 125), label_OrderDate.getPreferredSize()));

                //---- textField_OrderDate ----
                textField_OrderDate.setEditable(false);
                panel_AddOrder.add(textField_OrderDate);
                textField_OrderDate.setBounds(60, 120, 85, textField_OrderDate.getPreferredSize().height);

                //---- label_GoodsInfo ----
                label_GoodsInfo.setText("\u5546\u54c1\u4fe1\u606f");
                panel_AddOrder.add(label_GoodsInfo);
                label_GoodsInfo.setBounds(new Rectangle(new Point(5, 170), label_GoodsInfo.getPreferredSize()));

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(table_GoodsInfo);
                }
                panel_AddOrder.add(scrollPane2);
                scrollPane2.setBounds(15, 195, 380, 100);

                //---- label_OrderAddress ----
                label_OrderAddress.setText("\u9001\u8d27\u5730\u70b9");
                panel_AddOrder.add(label_OrderAddress);
                label_OrderAddress.setBounds(new Rectangle(new Point(10, 305), label_OrderAddress.getPreferredSize()));

                //---- textField_OrderAddress ----
                textField_OrderAddress.setEditable(false);
                panel_AddOrder.add(textField_OrderAddress);
                textField_OrderAddress.setBounds(10, 325, 100, textField_OrderAddress.getPreferredSize().height);

                //---- label9 ----
                label9.setText("\u603b\u8ba1\uff08\u5143\uff09");
                panel_AddOrder.add(label9);
                label9.setBounds(new Rectangle(new Point(410, 245), label9.getPreferredSize()));

                //---- textField_TotalMoney ----
                textField_TotalMoney.setEditable(false);
                panel_AddOrder.add(textField_TotalMoney);
                textField_TotalMoney.setBounds(405, 265, 70, textField_TotalMoney.getPreferredSize().height);

                //---- button_CheckInvoice ----
                button_CheckInvoice.setText("\u5f00\u5177\u53d1\u7968");
                button_CheckInvoice.addActionListener(e -> button_CheckInvoice_ActionPerformed(e));
                panel_AddOrder.add(button_CheckInvoice);
                button_CheckInvoice.setBounds(new Rectangle(new Point(395, 315), button_CheckInvoice.getPreferredSize()));

                //---- label_CustomerTel ----
                label_CustomerTel.setText("\u5ba2\u6237\u7535\u8bdd");
                panel_AddOrder.add(label_CustomerTel);
                label_CustomerTel.setBounds(new Rectangle(new Point(150, 305), label_CustomerTel.getPreferredSize()));
                panel_AddOrder.add(textField_CustomerTel);
                textField_CustomerTel.setBounds(150, 325, 105, textField_CustomerTel.getPreferredSize().height);

                //---- button_SaveOrder ----
                button_SaveOrder.setText("\u4fdd\u5b58\u8ba2\u5355");
                button_SaveOrder.addActionListener(e -> button_SaveOrder_ActionPerformed(e));
                panel_AddOrder.add(button_SaveOrder);
                button_SaveOrder.setBounds(new Rectangle(new Point(300, 315), button_SaveOrder.getPreferredSize()));

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel_AddOrder.getComponentCount(); i++) {
                        Rectangle bounds = panel_AddOrder.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel_AddOrder.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel_AddOrder.setMinimumSize(preferredSize);
                    panel_AddOrder.setPreferredSize(preferredSize);
                }
            }
            tabbedPane_OrderManage.addTab("\u65b0\u589e\u8ba2\u5355", panel_AddOrder);
        }
        contentPane.add(tabbedPane_OrderManage);
        tabbedPane_OrderManage.setBounds(0, 0, 485, 390);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - nut
    private JTabbedPane tabbedPane_OrderManage;
    private JPanel panel_QueryOrder;
    private JScrollPane scrollPane1;
    private JTable table_OrderInfo;
    private JButton button_QueryAll;
    private JButton button_QueryByOrderNum;
    private JLabel label_TipOutput;
    private JLabel label_Order_num;
    private JTextField textField_InputOrderNum;
    private JPanel panel_AddOrder;
    private JLabel label_OrderNum;
    private JLabel label_CustomerNum;
    private JTextField textField_CustomerNum;
    private JTextField textField_OrderNum;
    private JLabel label_OrderName;
    private JLabel label_CustomerName;
    private JTextField textField_CustomerName;
    private JLabel label_OrderDate;
    private JTextField textField_OrderDate;
    private JLabel label_GoodsInfo;
    private JScrollPane scrollPane2;
    private JTable table_GoodsInfo;
    private JLabel label_OrderAddress;
    private JTextField textField_OrderAddress;
    private JLabel label9;
    private JTextField textField_TotalMoney;
    private JButton button_CheckInvoice;
    private JLabel label_CustomerTel;
    private JTextField textField_CustomerTel;
    private JButton button_SaveOrder;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private String CurrentCustomer_Name=null;
    private String order_num=null;
    private float total_Price=0;
    private Date date;
    private QueryController queryController;
    private AddController addController;
}
