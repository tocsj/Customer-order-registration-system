/*
 * Created by JFormDesigner on Mon Nov 16 19:04:25 CST 2020
 */

package application_Frame_Administrator_ChildFrame;

import application_Constant.Constant;
import application_Controller.AddController;
import application_Controller.QueryController;
import application_Controller.UpdateController;
import application_Frame.Administrator_Frame;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import javax.swing.*;

public class InvoiceManage_Frame extends JFrame implements Constant
{
    public InvoiceManage_Frame()
    {
        initComponents();
        Load_Invoice_Frame();
    }
    private void Load_Invoice_Frame()
    {
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
        if(Customer_Name==null)
            JOptionPane.showMessageDialog(this,
                    "还没登记客户！","W-nut Tips", JOptionPane.PLAIN_MESSAGE);
    }

    //接收订单上的客户名，订单号，总金额
    public void setOrderInfo(String customer_name,String order_num,float total_money)
    {
        Customer_Name=customer_name;
        Order_Num=order_num;
        Total_Money=total_money;
        System.out.println("total_money="+Total_Money);

        printInvoice();
    }

    //去支付事件（支付成功后库存数量减少！）
    private void button_PayFor_ActionPerformed(ActionEvent e)
    {
        //确定支付方式
        Object ob=comboBox_PayWay.getSelectedItem();
        pay_way=String.valueOf(ob);

        //按订单号查询订单中的已选商品信息（商品名，商品选购数量）
        queryController=new QueryController();
        queryController.QueryGoodsInfoInOrder(Order_Num);//结果保存到GOODS_SELECTED_NAME，GOODS_SELECTED_CHOOSE_NUM动态数组中

        //更新商品库存(每一种选购的商品都要减少)
        updateController=new UpdateController();

        if(GOODS_SELECTED_NAME.size()==0)
            JOptionPane.showMessageDialog(this,"订单中没有商品信息！","W-nut Errors",
                    JOptionPane.WARNING_MESSAGE);
        else
        {
            for(int i=0;i<GOODS_SELECTED_NAME.size();++i)
                updateController.UpdateGoodsStoreNum(GOODS_SELECTED_NAME.get(i),GOODS_SELECTED_CHOOSE_NUM.get(i));
            JOptionPane.showMessageDialog(this,"支付成功");
        }

    }

    private void printInvoice()
    {
        //生成发票号
        queryController=new QueryController();
        Invoice_Num=queryController.QueryInvoiceNum();
        textField_InvoiceNum.setText(Invoice_Num);

        //获取开发票时间
        date = new Date(System.currentTimeMillis());
        textField_Date.setText(date.toString());

        //填充其他信息
        int cus_num=queryController.QueryCustomerNumByName(Customer_Name);
        textField_CustomerNum.setText(String.valueOf(cus_num));
        textField_CustomerName.setText(Customer_Name);

        textField_OrderNum.setText(Order_Num);
        textField_Money.setText(String.valueOf(Total_Money));
    }

    private void button_PrintInvoice_ActionPerformed(ActionEvent e)
    {
        //保存发票信息

        int cus_num=queryController.QueryCustomerNumByName(Customer_Name);
        //向数据库写数据Invoice表
        addController=new AddController();
        int isInvoice=addController.addInvoice(Invoice_Num,Order_Num,cus_num,Total_Money,pay_way,date);
        switch (isInvoice)
        {
            case SUCCESS -> {
                JOptionPane.showMessageDialog(this, "保存（打印）发票成功！",
                        "W-nut Tips", JOptionPane.PLAIN_MESSAGE);
            }
            case DATABASE_ERROR -> {
                JOptionPane.showMessageDialog(this, "保存（打印）发票失败！",
                        "W-nut Tips", JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - nut
        label_InvoiceName = new JLabel();
        label_InvoiceNum = new JLabel();
        textField_InvoiceNum = new JTextField();
        label_CustomerNum = new JLabel();
        textField_CustomerNum = new JTextField();
        label_OrderNum = new JLabel();
        textField_OrderNum = new JTextField();
        label_Money = new JLabel();
        textField_Money = new JTextField();
        button_PayFor = new JButton();
        label_PayWay = new JLabel();
        button_PrintInvoice = new JButton();
        label_CustomerName = new JLabel();
        textField_CustomerName = new JTextField();
        comboBox_PayWay = new JComboBox(PAY_WAYS);
        label_Date = new JLabel();
        textField_Date = new JTextField();

        //======== this ========
        setTitle("W-nut InvoiceManage");
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label_InvoiceName ----
        label_InvoiceName.setText("\u575a\u679c\u516c\u53f8\u53d1\u7968\u5904\u7406\u4e2d\u5fc3");
        contentPane.add(label_InvoiceName);
        label_InvoiceName.setBounds(120, 15, 140, label_InvoiceName.getPreferredSize().height);

        //---- label_InvoiceNum ----
        label_InvoiceNum.setText("\u53d1\u7968\u53f7");
        contentPane.add(label_InvoiceNum);
        label_InvoiceNum.setBounds(new Rectangle(new Point(5, 50), label_InvoiceNum.getPreferredSize()));
        contentPane.add(textField_InvoiceNum);
        textField_InvoiceNum.setBounds(50, 40, 100, textField_InvoiceNum.getPreferredSize().height);

        //---- label_CustomerNum ----
        label_CustomerNum.setText("\u5ba2\u6237\u53f7");
        contentPane.add(label_CustomerNum);
        label_CustomerNum.setBounds(new Rectangle(new Point(5, 85), label_CustomerNum.getPreferredSize()));
        contentPane.add(textField_CustomerNum);
        textField_CustomerNum.setBounds(50, 80, 100, textField_CustomerNum.getPreferredSize().height);

        //---- label_OrderNum ----
        label_OrderNum.setText("\u8ba2\u5355\u53f7");
        contentPane.add(label_OrderNum);
        label_OrderNum.setBounds(new Rectangle(new Point(5, 120), label_OrderNum.getPreferredSize()));
        contentPane.add(textField_OrderNum);
        textField_OrderNum.setBounds(50, 115, 100, textField_OrderNum.getPreferredSize().height);

        //---- label_Money ----
        label_Money.setText("\u5e94\u4ed8\u91d1\u989d");
        contentPane.add(label_Money);
        label_Money.setBounds(new Rectangle(new Point(5, 165), label_Money.getPreferredSize()));
        contentPane.add(textField_Money);
        textField_Money.setBounds(65, 160, 70, textField_Money.getPreferredSize().height);

        //---- button_PayFor ----
        button_PayFor.setText("\u53bb\u652f\u4ed8");
        button_PayFor.addActionListener(this::button_PayFor_ActionPerformed);
        contentPane.add(button_PayFor);
        button_PayFor.setBounds(new Rectangle(new Point(150, 245), button_PayFor.getPreferredSize()));

        //---- label_PayWay ----
        label_PayWay.setText("\u652f\u4ed8\u65b9\u5f0f");
        contentPane.add(label_PayWay);
        label_PayWay.setBounds(new Rectangle(new Point(145, 165), label_PayWay.getPreferredSize()));

        //---- button_PrintInvoice ----
        button_PrintInvoice.setText("\u4fdd\u5b58\uff08\u6253\u5370\uff09\u53d1\u7968");
        button_PrintInvoice.addActionListener(this::button_PrintInvoice_ActionPerformed);
        contentPane.add(button_PrintInvoice);
        button_PrintInvoice.setBounds(new Rectangle(new Point(265, 245), button_PrintInvoice.getPreferredSize()));

        //---- label_CustomerName ----
        label_CustomerName.setText("\u5ba2\u6237\u540d");
        contentPane.add(label_CustomerName);
        label_CustomerName.setBounds(new Rectangle(new Point(160, 85), label_CustomerName.getPreferredSize()));
        contentPane.add(textField_CustomerName);
        textField_CustomerName.setBounds(205, 80, 120, textField_CustomerName.getPreferredSize().height);
        contentPane.add(comboBox_PayWay);
        comboBox_PayWay.setBounds(205, 160, 130, comboBox_PayWay.getPreferredSize().height);

        //---- label_Date ----
        label_Date.setText("\u65e5\u671f");
        contentPane.add(label_Date);
        label_Date.setBounds(new Rectangle(new Point(5, 210), label_Date.getPreferredSize()));
        contentPane.add(textField_Date);
        textField_Date.setBounds(50, 205, 90, textField_Date.getPreferredSize().height);

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
        setSize(465, 395);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - nut
    private JLabel label_InvoiceName;
    private JLabel label_InvoiceNum;
    private JTextField textField_InvoiceNum;
    private JLabel label_CustomerNum;
    private JTextField textField_CustomerNum;
    private JLabel label_OrderNum;
    private JTextField textField_OrderNum;
    private JLabel label_Money;
    private JTextField textField_Money;
    private JButton button_PayFor;
    private JLabel label_PayWay;
    private JButton button_PrintInvoice;
    private JLabel label_CustomerName;
    private JTextField textField_CustomerName;
    private JComboBox comboBox_PayWay;
    private JLabel label_Date;
    private JTextField textField_Date;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private String Customer_Name=null;
    private String Order_Num=null;
    private float Total_Money=0;
    private String Invoice_Num=null;
    private String pay_way=null;
    private Date date;
    private QueryController queryController;
    private AddController addController;
    private UpdateController updateController;
}
