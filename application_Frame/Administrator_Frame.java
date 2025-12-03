/*
 * Created by JFormDesigner on Mon Nov 16 17:55:01 CST 2020
 * @author W-nut
 */

package application_Frame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import application_Frame_Administrator_ChildFrame.*;

@SuppressWarnings("unused")
public class Administrator_Frame extends JFrame
{

    public Administrator_Frame()
    {
        initComponents();
        Load_Administrator();
    }

    private void Load_Administrator()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,300);
        this.setVisible(true);
    }


    private void menuItem_AddCustomer_ActionPerformed(ActionEvent e)
    {
        CustomerManage_Frame customerManage_frame=new CustomerManage_Frame(0);
        this.setVisible(false);
    }

    private void menuItem_QueryCustomer_ActionPerformed(ActionEvent e)
    {
        CustomerManage_Frame customerManage_frame=new CustomerManage_Frame(1);
        this.setVisible(false);
    }

    private void menuItem_DeleteCustomer_ActionPerformed(ActionEvent e)
    {
        CustomerManage_Frame customerManage_frame=new CustomerManage_Frame(2);
        this.setVisible(false);
    }

    private void menuItem_UpdateCustomer_ActionPerformed(ActionEvent e)
    {
        CustomerManage_Frame customerManage_frame=new CustomerManage_Frame(3);
        this.setVisible(false);
    }
    private void menuItem_QueryOrder_ActionPerformed(ActionEvent e)
    {
        OrderManage_Frame orderManage_frame=new OrderManage_Frame(0);
        this.setVisible(false);
    }
    private void menuItem_AddOrder_ActionPerformed(ActionEvent e)
    {
        OrderManage_Frame orderManage_frame=new OrderManage_Frame(1);
        orderManage_frame.checkCustomer();
        this.setVisible(false);
    }
    private void menuItem_AddInvoice_ActionPerformed(ActionEvent e)
    {
        InvoiceManage_Frame invoiceManage_frame=new InvoiceManage_Frame();
        invoiceManage_frame.checkCustomer();
        this.setVisible(false);
    }
    private void menuItem_SelectGoods_ActionPerformed(ActionEvent e) 
    {
       GoodsManage_Frame goodsManage_frame=new GoodsManage_Frame(0);
       goodsManage_frame.checkCustomer();
       this.setVisible(false);
    }
    
    private void menuItem_AddGoods_ActionPerformed(ActionEvent e) 
    {
       GoodsManage_Frame goodsManage_frame=new GoodsManage_Frame(1);
       this.setVisible(false);
    }
    
    private void menuItem_DeleteGoods_ActionPerformed(ActionEvent e) 
    {
       GoodsManage_Frame goodsManage_frame=new GoodsManage_Frame(2);
       this.setVisible(false);
    }
    
    private void menuItem_QueryGoods_ActionPerformed(ActionEvent e) 
    {
       GoodsManage_Frame goodsManage_frame=new GoodsManage_Frame(3);
       this.setVisible(false);
    }
    
    private void menuItem_UpdateGoods_ActionPerformed(ActionEvent e) 
    {
       GoodsManage_Frame goodsManage_frame=new GoodsManage_Frame(4);
       this.setVisible(false);
    }
    
//    private void menuItem_QueryOrder_ActionPerformed(ActionEvent e)
//    {
//       GoodsManage_Frame goodsManage_frame=new GoodsManage_Frame(5);
//       this.setVisible(false);
//    }
//
//    private void menuItem_AddOrder_ActionPerformed(ActionEvent e)
//    {
//       GoodsManage_Frame goodsManage_frame=new GoodsManage_Frame(6);
//       goodsManage_frame.checkCustomer();
//       this.setVisible(false);
//    }
    private void menuItem_AddCustomer_MouseClicked(MouseEvent e)
    {
        // TODO add your code here
    }

    private void menuItem_QueryCustomer_MouseClicked(MouseEvent e)
    {
        // TODO add your code here
    }

    private void menuItem__DeleteCustomer_MouseClicked(MouseEvent e)
    {
        // TODO add your code here
    }

    private void menuItem_UpdateCustomer_MouseClicked(MouseEvent e)
    {
        // TODO add your code here
    }

    //方法功能：初始化框架
    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - bob
        menuBar_1 = new JMenuBar();
        menu_CustomerManage = new JMenu();
        menuItem_AddCustomer = new JMenuItem();
        menuItem_QueryCustomer = new JMenuItem();
        menuItem_DeleteCustomer = new JMenuItem();
        menuItem_UpdateCustomer = new JMenuItem();
        menu_GoodsManage = new JMenu();
        menuItem_SelectGoods = new JMenuItem();
        menuItem_AddGoods = new JMenuItem();
        menuItem_DeleteGoods = new JMenuItem();
        menuItem_QueryGoods = new JMenuItem();
        menuItem_UpdateGoods = new JMenuItem();
        menu_OrderManage = new JMenu();
        menuItem_QueryOrder = new JMenuItem();
        menuItem_AddOrder = new JMenuItem();
        menu_InvoiceManage = new JMenu();
        menuItem_AddInvoice = new JMenuItem();
        menu_Own = new JMenu();
        menuItem_OwnInfo = new JMenuItem();

        //======== this ========
        setTitle("Nut Dynasty-Administrator");
        setIconImage(new ImageIcon("E:\\CodeFile\\DataBase-CustomerPurchaseManagementSystem\\Code\\src\\META-INF\\background.jpg").getImage());
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== menuBar_1 ========
        {

            //======== menu_CustomerManage ========
            {
                menu_CustomerManage.setText("\u5ba2\u6237\u7ba1\u7406");

                //---- menuItem_AddCustomer ----
                menuItem_AddCustomer.setText("\u65b0\u589e\u5ba2\u6237\u4fe1\u606f");
                menuItem_AddCustomer.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        menuItem_AddCustomer_MouseClicked(e);
                    }
                });
                menuItem_AddCustomer.addActionListener(e -> menuItem_AddCustomer_ActionPerformed(e));
                menu_CustomerManage.add(menuItem_AddCustomer);

                //---- menuItem_QueryCustomer ----
                menuItem_QueryCustomer.setText("\u67e5\u8be2\u5ba2\u6237\u4fe1\u606f");
                menuItem_QueryCustomer.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        menuItem_QueryCustomer_MouseClicked(e);
                    }
                });
                menuItem_QueryCustomer.addActionListener(e -> menuItem_QueryCustomer_ActionPerformed(e));
                menu_CustomerManage.add(menuItem_QueryCustomer);

                //---- menuItem_DeleteCustomer ----
                menuItem_DeleteCustomer.setText("\u5220\u9664\u5ba2\u6237\u4fe1\u606f");
                menuItem_DeleteCustomer.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        menuItem__DeleteCustomer_MouseClicked(e);
                    }
                });
                menuItem_DeleteCustomer.addActionListener(e -> menuItem_DeleteCustomer_ActionPerformed(e));
                menu_CustomerManage.add(menuItem_DeleteCustomer);

                //---- menuItem_UpdateCustomer ----
                menuItem_UpdateCustomer.setText("\u4fee\u6539\u5ba2\u6237\u4fe1\u606f");
                menuItem_UpdateCustomer.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        menuItem_UpdateCustomer_MouseClicked(e);
                    }
                });
                menuItem_UpdateCustomer.addActionListener(e -> menuItem_UpdateCustomer_ActionPerformed(e));
                menu_CustomerManage.add(menuItem_UpdateCustomer);
            }
            menuBar_1.add(menu_CustomerManage);

            //======== menu_GoodsManage ========
            {
                menu_GoodsManage.setText("\u5546\u54c1\u7ba1\u7406");

                //---- menuItem_SelectGoods ----
                menuItem_SelectGoods.setText("\u9009\u8d2d\u5546\u54c1");
                menuItem_SelectGoods.addActionListener(e -> menuItem_SelectGoods_ActionPerformed(e));
                menu_GoodsManage.add(menuItem_SelectGoods);

                //---- menuItem_AddGoods ----
                menuItem_AddGoods.setText("\u65b0\u589e\u5546\u54c1\u4fe1\u606f");
                menuItem_AddGoods.addActionListener(e -> menuItem_AddGoods_ActionPerformed(e));
                menu_GoodsManage.add(menuItem_AddGoods);

                //---- menuItem_DeleteGoods ----
                menuItem_DeleteGoods.setText("\u5220\u9664\u5546\u54c1\u4fe1\u606f");
                menuItem_DeleteGoods.addActionListener(e -> menuItem_DeleteGoods_ActionPerformed(e));
                menu_GoodsManage.add(menuItem_DeleteGoods);

                //---- menuItem_QueryGoods ----
                menuItem_QueryGoods.setText("\u67e5\u8be2\u5546\u54c1\u4fe1\u606f");
                menuItem_QueryGoods.addActionListener(e -> menuItem_QueryGoods_ActionPerformed(e));
                menu_GoodsManage.add(menuItem_QueryGoods);

                //---- menuItem_UpdateGoods ----
                menuItem_UpdateGoods.setText("\u4fee\u6539\u5546\u54c1\u4fe1\u606f");
                menuItem_UpdateGoods.addActionListener(e -> menuItem_UpdateGoods_ActionPerformed(e));
                menu_GoodsManage.add(menuItem_UpdateGoods);
            }
            menuBar_1.add(menu_GoodsManage);

            //======== menu_OrderManage ========
            {
                menu_OrderManage.setText("\u8ba2\u5355\u7ba1\u7406");

                //---- menuItem_QueryOrder ----
                menuItem_QueryOrder.setText("\u67e5\u8be2\u8ba2\u5355\u4fe1\u606f");
                menuItem_QueryOrder.addActionListener(e -> {
			menuItem_QueryOrder_ActionPerformed(e);
			menuItem_QueryOrder_ActionPerformed(e);
		});
                menu_OrderManage.add(menuItem_QueryOrder);

                //---- menuItem_AddOrder ----
                menuItem_AddOrder.setText("\u65b0\u589e\u8ba2\u5355\u4fe1\u606f");
                menuItem_AddOrder.addActionListener(e -> menuItem_AddOrder_ActionPerformed(e));
                menu_OrderManage.add(menuItem_AddOrder);
            }
            menuBar_1.add(menu_OrderManage);

            //======== menu_InvoiceManage ========
            {
                menu_InvoiceManage.setText("\u53d1\u7968\u7ba1\u7406");

                //---- menuItem_AddInvoice ----
                menuItem_AddInvoice.setText("\u65b0\u589e\u53d1\u7968\u4fe1\u606f");
                menuItem_AddInvoice.addActionListener(e -> menuItem_AddInvoice_ActionPerformed(e));
                menu_InvoiceManage.add(menuItem_AddInvoice);
            }
            menuBar_1.add(menu_InvoiceManage);

            //======== menu_Own ========
            {
                menu_Own.setText("\u4e2a\u4eba\u4e2d\u5fc3");

                //---- menuItem_OwnInfo ----
                menuItem_OwnInfo.setText("\u4e2a\u4eba\u4fe1\u606f");
                menu_Own.add(menuItem_OwnInfo);
            }
            menuBar_1.add(menu_Own);
        }
        setJMenuBar(menuBar_1);

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
    // Generated using JFormDesigner Evaluation license - bob
    private JMenuBar menuBar_1;
    private JMenu menu_CustomerManage;
    private JMenuItem menuItem_AddCustomer;
    private JMenuItem menuItem_QueryCustomer;
    private JMenuItem menuItem_DeleteCustomer;
    private JMenuItem menuItem_UpdateCustomer;
    private JMenu menu_GoodsManage;
    private JMenuItem menuItem_SelectGoods;
    private JMenuItem menuItem_AddGoods;
    private JMenuItem menuItem_DeleteGoods;
    private JMenuItem menuItem_QueryGoods;
    private JMenuItem menuItem_UpdateGoods;
    private JMenu menu_OrderManage;
    private JMenuItem menuItem_QueryOrder;
    private JMenuItem menuItem_AddOrder;
    private JMenu menu_InvoiceManage;
    private JMenuItem menuItem_AddInvoice;
    private JMenu menu_Own;
    private JMenuItem menuItem_OwnInfo;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
