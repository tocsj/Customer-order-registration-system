
/*
 * @author nut
 */

package application_Frame_Administrator_ChildFrame;

import java.awt.event.*;

import application_Action.AddCustomerAction;
import application_Constant.Constant;
import application_Controller.AddController;
import application_Controller.DeleteController;
import application_Controller.ModifyController;
import application_Controller.QueryController;
import application_Frame.Administrator_Frame;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("unused")
public class CustomerManage_Frame extends JFrame implements Constant
{
    public CustomerManage_Frame(int tabbed_index)
    {
        initComponents();
        Load_CustomerManage_Frame(tabbed_index);
    }
    private void Load_CustomerManage_Frame(int tabbed_index)
    {
        this.tabbedPane_CustomerManage.setSelectedIndex(tabbed_index);
        this.tabbedPane_CustomerManage.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                Administrator_Frame administrator_frame=new Administrator_Frame();
            }
        });
        //this.setSize(400,300);
        this.setVisible(true);
    }

    //查询客户信息
    private void button_Query_ActionPerformed(ActionEvent e)
    {
        //向控制层发出请求
        queryController=new QueryController();
        queryController.InitCustomerHeader();

        DefaultTableModel tableModel=(DefaultTableModel)table_CustomerInfo.getModel();

        //清空原来内容
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        //初始化表格头
        for(String str:CUSTOMER_INFO_HEADER)
            tableModel.addColumn(str);

        queryController.QueryCustomerInfo();

        int customer_RowNum=CUSTOMER_INFO_NUM.size();
        Vector<String> vec_customerInfo=new Vector<>();

        for(int i=0;i<customer_RowNum;++i)
        {
            vec_customerInfo.add(CUSTOMER_INFO_NUM.get(i));
            vec_customerInfo.add(CUSTOMER_INFO_NAME.get(i));
            vec_customerInfo.add(CUSTOMER_INFO_TEL.get(i));
            vec_customerInfo.add(CUSTOMER_INFO_ADDRESS.get(i));

            Object[]objects=vec_customerInfo.toArray();
            tableModel.addRow(objects);
            vec_customerInfo.clear();
        }
        CUSTOMER_INFO_NUM.clear();
        CUSTOMER_INFO_NAME.clear();
        CUSTOMER_INFO_TEL.clear();
        CUSTOMER_INFO_ADDRESS.clear();
        CUSTOMER_INFO_HEADER.clear();
    }
    //修改用户
    private void button_Modify_ActionPerformed(ActionEvent e){
        DefaultTableModel tableModel=(DefaultTableModel)table_CustomerInfo.getModel();
        queryController.QueryCustomerInfo();
        //信息
        String name = CUSTOMER_INFO_NAME.get(table_CustomerInfo.getSelectedRow());
        String tel = CUSTOMER_INFO_TEL.get(table_CustomerInfo.getSelectedRow());
        String address = CUSTOMER_INFO_ADDRESS.get(table_CustomerInfo.getSelectedRow());

        this.tabbedPane_CustomerManage.setSelectedIndex(2);
        this.textField_CustomerTel2.setText(tel);
        this.textField_CustomerName2.setText(name);
        this.textArea_CustomerAddress2.setText(address);



    }
    private void button_Modify2_ActionPerformed(ActionEvent e) {
        //向控制层发出请求
        modifyController=new ModifyController();

        //修改后
        String cus_name=textField_CustomerName2.getText();
        String cus_tel=textField_CustomerTel2.getText();
        String cus_address=textArea_CustomerAddress2.getText();

        if(cus_name.equals(""))
        {
            JOptionPane.showMessageDialog(this,"客户名不能为空哦！",
                    "W-nut Tips",JOptionPane.WARNING_MESSAGE);
        }
        else if(cus_tel.equals("")||cus_address.equals(""))
        {
            JOptionPane.showMessageDialog(this,"请补全信息哦！",
                    "W-nut Tips",JOptionPane.WARNING_MESSAGE);
        }
        int isAllowed=modifyController.modifyCustomer(
                CUSTOMER_INFO_NUM.get(table_CustomerInfo.getSelectedRow()),
                cus_name,
                cus_tel,
                cus_address);
        switch (isAllowed)
        {
            case CUSTOMER_NAME_OVER_LENGTH -> {
                JOptionPane.showMessageDialog(this, "名字最多20个字符哦！",
                        "W-nut Tips", JOptionPane.WARNING_MESSAGE);
            }
            case CUSTOMER_NAME_OVERLOAD -> {
                JOptionPane.showMessageDialog(this, "名字重复，已有该客户！",
                        "W-nut Tips", JOptionPane.WARNING_MESSAGE);
            }
            case CUSTOMER_TEL_OVER_LENGTH -> {
                JOptionPane.showMessageDialog(this, "电话只能是11位哦！",
                        "W-nut Tips", JOptionPane.WARNING_MESSAGE);
            }
            case CUSTOMER_ADDRESS_OVER_LENGTH -> {
                JOptionPane.showMessageDialog(this, "地址最多50个字符哦！",
                        "W-nut Tips", JOptionPane.WARNING_MESSAGE);
            }
            case ADD_CUSTOMER_ALLOWED -> {
                JOptionPane.showMessageDialog(this, "修改客户成功！",
                        "W-nut Tips", JOptionPane.WARNING_MESSAGE);
                AddOrQuery_Success_CusName=cus_name;
            }
            default -> throw new IllegalStateException("Unexpected value: " + isAllowed);
        }
    }
    private void button_DeleteByName_ActionPerformed(ActionEvent e) {
        String input_name=textField_InputCustomerName2.getText();
        if(input_name.equals(""))
        {
            JOptionPane.showMessageDialog(this,"还没输入客户名哦！",
                    "W-nut Tips",JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            deleteController=new DeleteController();
            int isAllowed=deleteController.deleteCustomer(input_name);
            switch (isAllowed)
            {
                case DELETE_SUCCESS -> {
                    JOptionPane.showMessageDialog(this,
                            "客户存在并且删除！", "W-nut Tips", JOptionPane.PLAIN_MESSAGE);
                    AddOrQuery_Success_CusName=input_name;
                }
                case CUSTOMER_NAME_NOT_OVERLOAD -> JOptionPane.showMessageDialog(this,
                        "没有该客户！", "W-nut Tips", JOptionPane.WARNING_MESSAGE);
                default -> throw new IllegalStateException("Unexpected value: " + isAllowed);
            }
        }
    }
    //添加新客户
    private void button_Add_ActionPerformed(ActionEvent e)
    {
        String cus_name=textField_CustomerName.getText();
        String cus_tel=textField_CustomerTel.getText();
        String cus_address=textArea_CustomerAddress.getText();
        if(cus_name.equals(""))
        {
            JOptionPane.showMessageDialog(this,"客户名不能为空哦！",
                    "W-nut Tips",JOptionPane.WARNING_MESSAGE);
        }
        else if(cus_tel.equals("")||cus_address.equals(""))
        {
            JOptionPane.showMessageDialog(this,"请补全信息哦！",
                    "W-nut Tips",JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            addController=new AddController();
            int isAllowed=addController.addCustomer(cus_name,cus_tel,cus_address);
            switch (isAllowed)
            {
                case CUSTOMER_NAME_OVER_LENGTH -> {
                    JOptionPane.showMessageDialog(this, "名字最多20个字符哦！",
                            "W-nut Tips", JOptionPane.WARNING_MESSAGE);
                }
                case CUSTOMER_NAME_OVERLOAD -> {
                    JOptionPane.showMessageDialog(this, "名字重复，已有该客户！",
                            "W-nut Tips", JOptionPane.WARNING_MESSAGE);
                }
                case CUSTOMER_TEL_OVER_LENGTH -> {
                    JOptionPane.showMessageDialog(this, "电话只能是11位哦！",
                            "W-nut Tips", JOptionPane.WARNING_MESSAGE);
                }
                case CUSTOMER_ADDRESS_OVER_LENGTH -> {
                    JOptionPane.showMessageDialog(this, "地址最多50个字符哦！",
                            "W-nut Tips", JOptionPane.WARNING_MESSAGE);
                }
                case ADD_CUSTOMER_ALLOWED -> {
                    JOptionPane.showMessageDialog(this, "添加客户成功！",
                            "W-nut Tips", JOptionPane.WARNING_MESSAGE);
                    AddOrQuery_Success_CusName=cus_name;
                }
                default -> throw new IllegalStateException("Unexpected value: " + isAllowed);
            }
        }
    }

    private void button_addEsc_ActionPerformed(ActionEvent e)
    {
        Administrator_Frame administrator_frame=new Administrator_Frame();
        this.setVisible(false);
    }

    private void button_Shopping_ActionPerformed(ActionEvent e)
    {
        //添加新客户成功后直接购物时传递客户名
        if(AddOrQuery_Success_CusName!=null)
        {
            GoodsManage_Frame goodsManage_frame=new GoodsManage_Frame(0);
            goodsManage_frame.setShoppingCusName(AddOrQuery_Success_CusName);
            this.setVisible(false);
        }
        else
            JOptionPane.showMessageDialog(this, "添加客户尚未成功！",
                    "W-nut Tips", JOptionPane.WARNING_MESSAGE);

    }

    //输入客户名查询
    private void button_QueryByName_ActionPerformed(ActionEvent e)
    {
        String input_name=textField_InputCustomerName.getText();
        if(input_name.equals(""))
        {
            JOptionPane.showMessageDialog(this,"还没输入客户名哦！",
                    "W-nut Tips",JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            // 清空之前查询的结果
            DefaultTableModel tableModel = (DefaultTableModel) table_CustomerInfo.getModel();
            tableModel.setRowCount(0);
            
            queryController=new QueryController();
            queryController.InitCustomerHeader();
            
            // 初始化表格头
            tableModel.setColumnCount(0);
            for(String str:CUSTOMER_INFO_HEADER)
                tableModel.addColumn(str);
            
            // 查询客户信息
            int isExist=queryController.QueryCustomerByName(input_name, false);
            if(isExist == SUCCESS) {
                // 展示客户信息
                Vector<String> customerInfo = new Vector<>();
                customerInfo.add(CUSTOMER_INFO_ALL.get(0)); // 客户号
                customerInfo.add(CUSTOMER_INFO_ALL.get(1)); // 客户名
                customerInfo.add(CUSTOMER_INFO_ALL.get(2)); // 客户电话
                customerInfo.add(CUSTOMER_INFO_ALL.get(3)); // 客户地址
                
                tableModel.addRow(customerInfo);
                AddOrQuery_Success_CusName=input_name;
                CUSTOMER_INFO_ALL.clear();
                CUSTOMER_INFO_HEADER.clear();
            } else {
                JOptionPane.showMessageDialog(this,
                        "没有该客户！", "W-nut Tips", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    //查询客户名成功后直接去购物
    private void button_GoShopping_ActionPerformed(ActionEvent e) 
    {
        //查询客户名成功后直接购物时传递客户名
        if(AddOrQuery_Success_CusName!=null)
        {
            GoodsManage_Frame goodsManage_frame=new GoodsManage_Frame(0);
            goodsManage_frame.setShoppingCusName(AddOrQuery_Success_CusName);
            this.setVisible(false);
        }
        else
        {
            // 如果没有通过查询按钮获取客户名，尝试从输入框获取
            String input_name=textField_InputCustomerName.getText();
            if (!input_name.equals("")) {
                // 验证客户是否存在
                queryController = new QueryController();
                int isExist = queryController.QueryCustomerByName(input_name);
                if (isExist == CUSTOMER_NAME_OVERLOAD) {
                    GoodsManage_Frame goodsManage_frame = new GoodsManage_Frame(0);
                    goodsManage_frame.setShoppingCusName(input_name);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "没有查询到该客户！",
                            "W-nut Tips", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "没有查询到该客户！",
                        "W-nut Tips", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Ethan Hunt
        tabbedPane_CustomerManage = new JTabbedPane();
        panel_AddCustomer = new JPanel();
        label_CustomerName = new JLabel();
        label_CustomerTel = new JLabel();
        label_CustomerAddress = new JLabel();
        textField_CustomerName = new JTextField();
        textField_CustomerTel = new JTextField();
        scrollPane1 = new JScrollPane();
        textArea_CustomerAddress = new JTextArea();
        button_Add = new JButton();
        button_addEsc = new JButton();
        button_Shop = new JButton();
        panel_QueryCustomer = new JPanel();
        scrollPane2 = new JScrollPane();
        table_CustomerInfo = new JTable();
        button_Query = new JButton();
        label_QueryOneCustomer = new JLabel();
        textField_InputCustomerName = new JTextField();
        button_QueryByName = new JButton();
        button_GoShopping = new JButton();
        button_Modify = new JButton();
        panel_UpdateCustomer = new JPanel();
        textField_CustomerName2 = new JTextField();
        textArea_CustomerAddress2 = new JTextArea();
        label_CustomerName2 = new JLabel();
        label_CustomerTel2 = new JLabel();
        label_CustomerAddress2 = new JLabel();
        textField_CustomerTel2 = new JTextField();
        button_Modify2 = new JButton();
        button_addEsc2 = new JButton();
        panel_DeleteCustomer = new JPanel();
        label_QueryOneCustomer2 = new JLabel();
        textField_InputCustomerName2 = new JTextField();
        button_DeleteByName = new JButton();

        //======== this ========
        setTitle("W-nut CustomerManage");
        setMinimumSize(new Dimension(400, 300));
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== tabbedPane_CustomerManage ========
        {

            //======== panel_AddCustomer ========
            {
                panel_AddCustomer.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax
                . swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmDes\u0069gner \u0045valua\u0074ion" , javax. swing
                .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .
                Font ( "D\u0069alog", java .awt . Font. BOLD ,12 ) ,java . awt. Color .red
                ) ,panel_AddCustomer. getBorder () ) ); panel_AddCustomer. addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override
                public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062order" .equals ( e. getPropertyName (
                ) ) )throw new RuntimeException( ) ;} } );
                panel_AddCustomer.setLayout(null);

                //---- label_CustomerName ----
                label_CustomerName.setText("\u5ba2\u6237\u540d");
                panel_AddCustomer.add(label_CustomerName);
                label_CustomerName.setBounds(20, 55, 60, label_CustomerName.getPreferredSize().height);

                //---- label_CustomerTel ----
                label_CustomerTel.setText("\u5ba2\u6237\u7535\u8bdd");
                panel_AddCustomer.add(label_CustomerTel);
                label_CustomerTel.setBounds(20, 85, 55, label_CustomerTel.getPreferredSize().height);

                //---- label_CustomerAddress ----
                label_CustomerAddress.setText("\u5ba2\u6237\u5730\u5740");
                panel_AddCustomer.add(label_CustomerAddress);
                label_CustomerAddress.setBounds(new Rectangle(new Point(20, 125), label_CustomerAddress.getPreferredSize()));
                panel_AddCustomer.add(textField_CustomerName);
                textField_CustomerName.setBounds(90, 45, 80, textField_CustomerName.getPreferredSize().height);
                panel_AddCustomer.add(textField_CustomerTel);
                textField_CustomerTel.setBounds(90, 80, 130, 30);

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(textArea_CustomerAddress);
                }
                panel_AddCustomer.add(scrollPane1);
                scrollPane1.setBounds(95, 125, 125, 80);

                //---- button_Add ----
                button_Add.setText("Add");
                button_Add.addActionListener(e -> button_Add_ActionPerformed(e));
                panel_AddCustomer.add(button_Add);
                button_Add.setBounds(250, 50, 85, 35);

                //---- button_addEsc ----
                button_addEsc.setText("Esc");
                button_addEsc.addActionListener(e -> button_addEsc_ActionPerformed(e));
                panel_AddCustomer.add(button_addEsc);
                button_addEsc.setBounds(250, 105, 85, 35);

                //---- button_Shop ----
                button_Shop.setText("Shop");
                button_Shop.addActionListener(e -> button_Shopping_ActionPerformed(e));
                panel_AddCustomer.add(button_Shop);
                button_Shop.setBounds(250, 160, 85, 35);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel_AddCustomer.getComponentCount(); i++) {
                        Rectangle bounds = panel_AddCustomer.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel_AddCustomer.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel_AddCustomer.setMinimumSize(preferredSize);
                    panel_AddCustomer.setPreferredSize(preferredSize);
                }
            }
            tabbedPane_CustomerManage.addTab("\u65b0\u589e\u5ba2\u6237", panel_AddCustomer);

            //======== panel_QueryCustomer ========
            {
                panel_QueryCustomer.setLayout(null);

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(table_CustomerInfo);
                }
                panel_QueryCustomer.add(scrollPane2);
                scrollPane2.setBounds(10, 5, 290, 150);

                //---- button_Query ----
                button_Query.setText("\u67e5\u8be2");
                button_Query.addActionListener(e -> button_Query_ActionPerformed(e));
                panel_QueryCustomer.add(button_Query);
                button_Query.setBounds(new Rectangle(new Point(320, 30), button_Query.getPreferredSize()));

                //---- label_QueryOneCustomer ----
                label_QueryOneCustomer.setText("\u8f93\u5165\u5ba2\u6237\u540d\u67e5\u8be2");
                panel_QueryCustomer.add(label_QueryOneCustomer);
                label_QueryOneCustomer.setBounds(new Rectangle(new Point(15, 215), label_QueryOneCustomer.getPreferredSize()));
                panel_QueryCustomer.add(textField_InputCustomerName);
                textField_InputCustomerName.setBounds(15, 245, 115, textField_InputCustomerName.getPreferredSize().height);

                //---- button_QueryByName ----
                button_QueryByName.setText("\u67e5\u8be2\u8be5\u7528\u6237");
                button_QueryByName.addActionListener(e -> button_QueryByName_ActionPerformed(e));
                panel_QueryCustomer.add(button_QueryByName);
                button_QueryByName.setBounds(new Rectangle(new Point(155, 245), button_QueryByName.getPreferredSize()));

                //---- button_GoShopping ----
                button_GoShopping.setText("\u53bb\u8d2d\u7269");
                button_GoShopping.addActionListener(e -> button_GoShopping_ActionPerformed(e));
                panel_QueryCustomer.add(button_GoShopping);
                button_GoShopping.setBounds(new Rectangle(new Point(355, 245), button_GoShopping.getPreferredSize()));

                //---- button_Modify ----
                button_Modify.setText("\u4fee\u6539");
                button_Modify.addActionListener(e -> button_Query_ActionPerformed(e));
                panel_QueryCustomer.add(button_Modify);
                button_Modify.setBounds(325, 85, 78, 30);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel_QueryCustomer.getComponentCount(); i++) {
                        Rectangle bounds = panel_QueryCustomer.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel_QueryCustomer.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel_QueryCustomer.setMinimumSize(preferredSize);
                    panel_QueryCustomer.setPreferredSize(preferredSize);
                }
            }
            tabbedPane_CustomerManage.addTab("\u67e5\u8be2\u5ba2\u6237", panel_QueryCustomer);

            //======== panel_UpdateCustomer ========
            {
                panel_UpdateCustomer.setLayout(null);
                panel_UpdateCustomer.add(textField_CustomerName2);
                textField_CustomerName2.setBounds(85, 50, 80, 30);
                panel_UpdateCustomer.add(textArea_CustomerAddress2);
                textArea_CustomerAddress2.setBounds(85, 155, 123, 78);

                //---- label_CustomerName2 ----
                label_CustomerName2.setText("\u5ba2\u6237\u540d");
                panel_UpdateCustomer.add(label_CustomerName2);
                label_CustomerName2.setBounds(15, 55, 60, 17);

                //---- label_CustomerTel2 ----
                label_CustomerTel2.setText("\u5ba2\u6237\u7535\u8bdd");
                panel_UpdateCustomer.add(label_CustomerTel2);
                label_CustomerTel2.setBounds(15, 110, 55, 17);

                //---- label_CustomerAddress2 ----
                label_CustomerAddress2.setText("\u5ba2\u6237\u5730\u5740");
                panel_UpdateCustomer.add(label_CustomerAddress2);
                label_CustomerAddress2.setBounds(15, 160, 48, 17);
                panel_UpdateCustomer.add(textField_CustomerTel2);
                textField_CustomerTel2.setBounds(85, 100, 130, 30);

                //---- button_Modify2 ----
                button_Modify2.setText("Modify");
                button_Modify2.addActionListener(e -> button_Add_ActionPerformed(e));
                panel_UpdateCustomer.add(button_Modify2);
                button_Modify2.setBounds(260, 50, 85, 35);

                //---- button_addEsc2 ----
                button_addEsc2.setText("Esc");
                button_addEsc2.addActionListener(e -> button_addEsc_ActionPerformed(e));
                panel_UpdateCustomer.add(button_addEsc2);
                button_addEsc2.setBounds(265, 145, 85, 35);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel_UpdateCustomer.getComponentCount(); i++) {
                        Rectangle bounds = panel_UpdateCustomer.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel_UpdateCustomer.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel_UpdateCustomer.setMinimumSize(preferredSize);
                    panel_UpdateCustomer.setPreferredSize(preferredSize);
                }
            }
            tabbedPane_CustomerManage.addTab("\u4fee\u6539\u5ba2\u6237", panel_UpdateCustomer);

            //======== panel_DeleteCustomer ========
            {
                panel_DeleteCustomer.setLayout(null);

                //---- label_QueryOneCustomer2 ----
                label_QueryOneCustomer2.setText("\u8f93\u5165\u5ba2\u6237\u540d\u67e5\u8be2");
                panel_DeleteCustomer.add(label_QueryOneCustomer2);
                label_QueryOneCustomer2.setBounds(0, 0, 145, 45);
                panel_DeleteCustomer.add(textField_InputCustomerName2);
                textField_InputCustomerName2.setBounds(0, 45, 145, 45);

                //---- button_DeleteByName ----
                button_DeleteByName.setText("\u641c\u7d22\u5e76\u5220\u9664");
                button_DeleteByName.addActionListener(e -> button_QueryByName_ActionPerformed(e));
                panel_DeleteCustomer.add(button_DeleteByName);
                button_DeleteByName.setBounds(175, 45, 105, 40);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel_DeleteCustomer.getComponentCount(); i++) {
                        Rectangle bounds = panel_DeleteCustomer.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel_DeleteCustomer.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel_DeleteCustomer.setMinimumSize(preferredSize);
                    panel_DeleteCustomer.setPreferredSize(preferredSize);
                }
            }
            tabbedPane_CustomerManage.addTab("\u5220\u9664\u5ba2\u6237", panel_DeleteCustomer);
        }
        contentPane.add(tabbedPane_CustomerManage);
        tabbedPane_CustomerManage.setBounds(0, 0, 505, 370);

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
    // Generated using JFormDesigner Evaluation license - Ethan Hunt
    private JTabbedPane tabbedPane_CustomerManage;
    private JPanel panel_AddCustomer;
    private JLabel label_CustomerName;
    private JLabel label_CustomerTel;
    private JLabel label_CustomerAddress;
    private JTextField textField_CustomerName;
    private JTextField textField_CustomerTel;
    private JScrollPane scrollPane1;
    private JTextArea textArea_CustomerAddress;
    private JButton button_Add;
    private JButton button_addEsc;
    private JButton button_Shop;
    private JPanel panel_QueryCustomer;
    private JScrollPane scrollPane2;
    private JTable table_CustomerInfo;
    private JButton button_Query;
    private JLabel label_QueryOneCustomer;
    private JTextField textField_InputCustomerName;
    private JButton button_QueryByName;
    private JButton button_GoShopping;
    private JButton button_Modify;
    private JPanel panel_UpdateCustomer;
    private JTextField textField_CustomerName2;
    private JTextArea textArea_CustomerAddress2;
    private JLabel label_CustomerName2;
    private JLabel label_CustomerTel2;
    private JLabel label_CustomerAddress2;
    private JTextField textField_CustomerTel2;
    private JButton button_Modify2;
    private JButton button_addEsc2;
    private JPanel panel_DeleteCustomer;
    private JLabel label_QueryOneCustomer2;
    private JTextField textField_InputCustomerName2;
    private JButton button_DeleteByName;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private QueryController queryController;
    private ModifyController modifyController;
    private DeleteController deleteController;
    private AddController addController;
    private String AddOrQuery_Success_CusName=null;
}
