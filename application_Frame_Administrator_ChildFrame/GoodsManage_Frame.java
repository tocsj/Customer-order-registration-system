/*
 *@author W-nut
 *
 */

package application_Frame_Administrator_ChildFrame;

import application_Constant.Constant;
import application_Controller.QueryController;
import application_Frame.Administrator_Frame;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GoodsManage_Frame extends JFrame implements Constant
{

    public GoodsManage_Frame(int tabbedIndex)
    {
        initComponents();
        Load_GoodsManage_Frame(tabbedIndex);
        loadGoodsInfo();
    }

    //选购商品时确定当前客户名
    public void setShoppingCusName(String current_CusName)
    {
        Current_CustomerName=current_CusName;
    }

    public void checkCustomer()
    {
        if(Current_CustomerName==null)
            JOptionPane.showMessageDialog(this,
                    "还没登记客户！","W-nut Tips", JOptionPane.PLAIN_MESSAGE);
    }

    private void Load_GoodsManage_Frame(int tabbedIndex)
    {
        this.tabbedPane_GoodsManage.setSelectedIndex(tabbedIndex);
        this.tabbedPane_GoodsManage.setVisible(true);
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

    //加载已选商品
    private void loadSelectedGoods()
    {
        GOODS_SELECTED_HEADER.add("已选商品名");
        GOODS_SELECTED_HEADER.add("已选商品个数");

        DefaultTableModel defaultTableModel=(DefaultTableModel)table_SelectedGoods.getModel();
        defaultTableModel.setColumnCount(0);
        defaultTableModel.setRowCount(0);

        for(String str:GOODS_SELECTED_HEADER)
            defaultTableModel.addColumn(str);

        int rowNum=GOODS_SELECTED_NAME.size();
        Vector<String>goods_selected_data=new Vector<>();
        for(int i=0;i<rowNum;++i)
        {
            goods_selected_data.add(GOODS_SELECTED_NAME.get(i));
            goods_selected_data.add(GOODS_SELECTED_CHOOSE_NUM.get(i));
            Object[]objects=goods_selected_data.toArray();
            defaultTableModel.addRow(objects);
            goods_selected_data.clear();
        }
        GOODS_SELECTED_HEADER.clear();
    }

    //加载商品信息
    private void loadGoodsInfo()
    {
        //向控制层发出请求
        queryController=new QueryController();
        queryController.InitGoodsHeader();

        DefaultTableModel tableModel=(DefaultTableModel)table_GoodsInfo.getModel();

        //清空原来内容
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        //初始化表格头
        for(String str:GOODS_INFO_HEADER)
            tableModel.addColumn(str);

        queryController.QueryGoodsInfo();

        int goods_RowNum=GOODS_INFO_NUM.size();
        Vector<String> vec_goodsInfo=new Vector<>();

        for(int i=0;i<goods_RowNum;++i)
        {
            vec_goodsInfo.add(GOODS_INFO_NUM.get(i));
            vec_goodsInfo.add(GOODS_INFO_NAME.get(i));
            vec_goodsInfo.add(GOODS_INFO_PRICE.get(i).toString());
            vec_goodsInfo.add(GOODS_INFO_STORE_NUM.get(i).toString());

            Object[]objects=vec_goodsInfo.toArray();
            tableModel.addRow(objects);
            vec_goodsInfo.clear();
        }
        GOODS_INFO_NUM.clear();
        GOODS_INFO_NAME.clear();
        GOODS_INFO_PRICE.clear();
        GOODS_INFO_STORE_NUM.clear();
        GOODS_INFO_HEADER.clear();
    }

    //开订单(需要传递客户名，选购的商品信息（商品名，选购数量）)
    private void button_CheckOrder_ActionPerformed(ActionEvent e)
    {
        if(!GOODS_SELECTED_NAME.isEmpty())
        {
            OrderManage_Frame orderManage_frame=new OrderManage_Frame(1);
            orderManage_frame.setCustomerName(Current_CustomerName);
            this.setVisible(false);
        }
        else
        {
            JOptionPane.showMessageDialog(this,"还没购买任何商品哦！",
                    "W-nut Errors", JOptionPane.ERROR_MESSAGE);
        }

    }

    //单击事件监听
    private void table_GoodsInfo_MouseClicked(MouseEvent e) 
    {
        //设置每次只能选中一行
        table_GoodsInfo.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        int selectRowNum=table_GoodsInfo.getSelectedRow();
        //获取选中行的商品名
        String selectedGoodsName = (String) table_GoodsInfo.getValueAt(selectRowNum,1);

        //将选中行的商品名单独拎出来
        textField_CurrentSelection.setText(selectedGoodsName);
    }

    //点击确认按钮购买
    private void button_BuyIt_ActionPerformed(ActionEvent e)
    {
        //判断是否输入了购买数量以及合法性
        String buyName=textField_CurrentSelection.getText();
        String buyNum=textField_BuyNum.getText();

        if(buyName.equals(""))
        {
            JOptionPane.showMessageDialog(this,"还没选择购买商品哦！",
                    "W-nut Errors", JOptionPane.ERROR_MESSAGE);
        }
        else if(buyNum.equals(""))
        {
            JOptionPane.showMessageDialog(this,"还没输入购买数量哦！",
                    "W-nut Errors", JOptionPane.ERROR_MESSAGE);
            textField_CurrentSelection.setText("");
        }
        else
        {
            //检查选择商品，购买数量合法性
            queryController=new QueryController();
            int isAllowed=queryController.checkInputBuyNum(buyName,buyNum);
            switch (isAllowed)
            {
                case GOODS_STORE_NUM_SHORT ->  JOptionPane.showMessageDialog(this,
                        "商品库存呢不够了哦！","W-nut Errors", JOptionPane.ERROR_MESSAGE);
                case INPUT_NOT_INTEGER ->  JOptionPane.showMessageDialog(this,
                        "输入不是整数哦！","W-nut Errors", JOptionPane.ERROR_MESSAGE);
                case GOODS_STORE_NUM_ENOUGH ->
                        {
                            JOptionPane.showMessageDialog(this,
                                 "此次购买库存充足！","W-nut Tips", JOptionPane.PLAIN_MESSAGE);
                            //列出已选商品简要信息
                            GOODS_SELECTED_NAME.add(buyName);
                            GOODS_SELECTED_CHOOSE_NUM.add(buyNum);
                            loadSelectedGoods();
                        }

            }
            textField_CurrentSelection.setText("");
            textField_BuyNum.setText("");

        }
        //将购买信息保存起来
    }

    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - nut
        tabbedPane_GoodsManage = new JTabbedPane();
        panel_BuyGoods = new JPanel();
        scrollPane1 = new JScrollPane();
        table_GoodsInfo = new JTable();
        button_CheckOrder = new JButton();
        label_CurrentSelection = new JLabel();
        textField_CurrentSelection = new JTextField();
        label_BuyNum = new JLabel();
        textField_BuyNum = new JTextField();
        button_BuyIt = new JButton();
        label_BuyIt = new JLabel();
        label_GoodsList = new JLabel();
        label_SelectedGoods = new JLabel();
        scrollPane2 = new JScrollPane();
        table_SelectedGoods = new JTable();
        panel_AddGoods = new JPanel();

        //======== this ========
        setTitle("W-nut GoodsManage");
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== tabbedPane_GoodsManage ========
        {

            //======== panel_BuyGoods ========
            {
                panel_BuyGoods.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax.
                swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing. border
                . TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("D\u0069alog"
                ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) ,panel_BuyGoods. getBorder
                ( )) ); panel_BuyGoods. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java
                .beans .PropertyChangeEvent e) {if ("\u0062order" .equals (e .getPropertyName () )) throw new RuntimeException
                ( ); }} );
                panel_BuyGoods.setLayout(null);

                //======== scrollPane1 ========
                {

                    //---- table_GoodsInfo ----
                    table_GoodsInfo.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            table_GoodsInfo_MouseClicked(e);
                        }
                    });
                    scrollPane1.setViewportView(table_GoodsInfo);
                }
                panel_BuyGoods.add(scrollPane1);
                scrollPane1.setBounds(5, 25, 315, 195);

                //---- button_CheckOrder ----
                button_CheckOrder.setText("\u53bb\u5f00\u8ba2\u5355");
                button_CheckOrder.addActionListener(this::button_CheckOrder_ActionPerformed);
                panel_BuyGoods.add(button_CheckOrder);
                button_CheckOrder.setBounds(new Rectangle(new Point(415, 300), button_CheckOrder.getPreferredSize()));

                //---- label_CurrentSelection ----
                label_CurrentSelection.setText("\u5f53\u524d\u9009\u62e9\u5546\u54c1");
                panel_BuyGoods.add(label_CurrentSelection);
                label_CurrentSelection.setBounds(new Rectangle(new Point(10, 240), label_CurrentSelection.getPreferredSize()));
                panel_BuyGoods.add(textField_CurrentSelection);
                textField_CurrentSelection.setBounds(5, 270, 130, 30);

                //---- label_BuyNum ----
                label_BuyNum.setText("\u9009\u8d2d\u6570\u91cf\uff08\u4e2a\uff09");
                panel_BuyGoods.add(label_BuyNum);
                label_BuyNum.setBounds(new Rectangle(new Point(140, 240), label_BuyNum.getPreferredSize()));
                panel_BuyGoods.add(textField_BuyNum);
                textField_BuyNum.setBounds(150, 270, 70, 30);

                //---- button_BuyIt ----
                button_BuyIt.setText("\u786e\u5b9a");
                button_BuyIt.addActionListener(this::button_BuyIt_ActionPerformed);
                panel_BuyGoods.add(button_BuyIt);
                button_BuyIt.setBounds(new Rectangle(new Point(235, 270), button_BuyIt.getPreferredSize()));

                //---- label_BuyIt ----
                label_BuyIt.setText("\u786e\u8ba4\u8d2d\u4e70");
                panel_BuyGoods.add(label_BuyIt);
                label_BuyIt.setBounds(new Rectangle(new Point(250, 240), label_BuyIt.getPreferredSize()));

                //---- label_GoodsList ----
                label_GoodsList.setText("\u5546\u54c1\u5217\u8868");
                panel_BuyGoods.add(label_GoodsList);
                label_GoodsList.setBounds(new Rectangle(new Point(10, 5), label_GoodsList.getPreferredSize()));

                //---- label_SelectedGoods ----
                label_SelectedGoods.setText("\u5df2\u9009\u5217\u8868");
                panel_BuyGoods.add(label_SelectedGoods);
                label_SelectedGoods.setBounds(new Rectangle(new Point(340, 5), label_SelectedGoods.getPreferredSize()));

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(table_SelectedGoods);
                }
                panel_BuyGoods.add(scrollPane2);
                scrollPane2.setBounds(330, 25, 180, 195);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel_BuyGoods.getComponentCount(); i++) {
                        Rectangle bounds = panel_BuyGoods.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel_BuyGoods.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel_BuyGoods.setMinimumSize(preferredSize);
                    panel_BuyGoods.setPreferredSize(preferredSize);
                }
            }
            tabbedPane_GoodsManage.addTab("\u9009\u8d2d\u5546\u54c1", panel_BuyGoods);

            //======== panel_AddGoods ========
            {
                panel_AddGoods.setLayout(null);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel_AddGoods.getComponentCount(); i++) {
                        Rectangle bounds = panel_AddGoods.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel_AddGoods.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel_AddGoods.setMinimumSize(preferredSize);
                    panel_AddGoods.setPreferredSize(preferredSize);
                }
            }
            tabbedPane_GoodsManage.addTab("\u65b0\u589e\u5546\u54c1", panel_AddGoods);
        }
        contentPane.add(tabbedPane_GoodsManage);
        tabbedPane_GoodsManage.setBounds(0, 0, 520, 380);

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
    private JTabbedPane tabbedPane_GoodsManage;
    private JPanel panel_BuyGoods;
    private JScrollPane scrollPane1;
    private JTable table_GoodsInfo;
    private JButton button_CheckOrder;
    private JLabel label_CurrentSelection;
    private JTextField textField_CurrentSelection;
    private JLabel label_BuyNum;
    private JTextField textField_BuyNum;
    private JButton button_BuyIt;
    private JLabel label_BuyIt;
    private JLabel label_GoodsList;
    private JLabel label_SelectedGoods;
    private JScrollPane scrollPane2;
    private JTable table_SelectedGoods;
    private JPanel panel_AddGoods;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private QueryController queryController;
    private String Current_CustomerName=null;
}
