/*
 *@author W-nut
 *
 */

package application_Frame_Administrator_ChildFrame;

import application_Constant.Constant;
import application_Controller.AddController;
import application_Controller.DeleteController;
import application_Controller.QueryController;
import application_Controller.UpdateController;
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
    
    // 加载商品信息到查询表格
    private void loadGoodsInfoToQueryTable()
    {
        //向控制层发出请求
        queryController=new QueryController();
        queryController.InitGoodsHeader();

        DefaultTableModel tableModel=(DefaultTableModel)table_QueryGoods.getModel();

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
    
    // 查询订单事件处理
    private void button_QueryOrderActionPerformed(ActionEvent e) {
        OrderManage_Frame orderManage_frame = new OrderManage_Frame(0);
        this.setVisible(false);
    }
    
    // 新增订单事件处理
    private void button_AddOrderActionPerformed(ActionEvent e) {
        if (Current_CustomerName == null) {
            JOptionPane.showMessageDialog(this, "还没登记客户！", 
                    "W-nut Tips", JOptionPane.PLAIN_MESSAGE);
        } else {
            OrderManage_Frame orderManage_frame = new OrderManage_Frame(1);
            orderManage_frame.setCustomerName(Current_CustomerName);
            this.setVisible(false);
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

    //添加商品事件处理
    private void good_addActionPerformed(ActionEvent e) {
        String goodsName = textField1.getText();
        String goodsPrice = textField2.getText();
        String goodsStoreNum = textField3.getText();

        if (goodsName.equals("")) {
            JOptionPane.showMessageDialog(this, "商品名不能为空哦！",
                    "W-nut Errors", JOptionPane.ERROR_MESSAGE);
        } else if (goodsPrice.equals("")) {
            JOptionPane.showMessageDialog(this, "商品价格不能为空哦！",
                    "W-nut Errors", JOptionPane.ERROR_MESSAGE);
        } else if (goodsStoreNum.equals("")) {
            JOptionPane.showMessageDialog(this, "商品库存不能为空哦！",
                    "W-nut Errors", JOptionPane.ERROR_MESSAGE);
        } else {
            addController = new AddController();
            int isAllowed = addController.addGoods(goodsName, goodsPrice, goodsStoreNum);
            
            switch (isAllowed) {
                case GOODS_NAME_INVALID -> JOptionPane.showMessageDialog(this, "商品名长度不能超过20个字符！",
                        "W-nut Errors", JOptionPane.ERROR_MESSAGE);
                case GOODS_PRICE_INVALID -> JOptionPane.showMessageDialog(this, "商品价格必须是有效的数字！",
                        "W-nut Errors", JOptionPane.ERROR_MESSAGE);
                case GOODS_STORE_NUM_INVALID -> JOptionPane.showMessageDialog(this, "商品库存必须是有效的整数！",
                        "W-nut Errors", JOptionPane.ERROR_MESSAGE);
                case GOODS_NAME_EXISTS -> JOptionPane.showMessageDialog(this, "该商品名已存在，请更换商品名！",
                        "W-nut Errors", JOptionPane.ERROR_MESSAGE);
                case SUCCESS -> {
                    JOptionPane.showMessageDialog(this, "添加商品成功！",
                            "W-nut Tips", JOptionPane.PLAIN_MESSAGE);
                    // 清空输入框
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    // 刷新商品列表
                    loadGoodsInfo();
                }
                default -> JOptionPane.showMessageDialog(this, "添加商品失败！",
                        "W-nut Errors", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //取消添加商品事件处理
    private void button2ActionPerformed(ActionEvent e) {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
    }
    
    // 删除商品事件处理
    private void button_DeleteGoodsActionPerformed(ActionEvent e) {
        String goodsName = textField_DeleteGoodsName.getText();
        
        if (goodsName.equals("")) {
            JOptionPane.showMessageDialog(this, "商品名不能为空！",
                    "W-nut Errors", JOptionPane.ERROR_MESSAGE);
        } else {
            deleteController = new DeleteController();
            int isExists = deleteController.checkGoodsExists(goodsName);
            
            if (isExists == GOODS_NAME_NOT_EXISTS) {
                JOptionPane.showMessageDialog(this, "该商品不存在！",
                        "W-nut Errors", JOptionPane.ERROR_MESSAGE);
            } else {
                int isDeleted = deleteController.deleteGoods(goodsName);
                
                if (isDeleted == DELETE_SUCCESS) {
                    JOptionPane.showMessageDialog(this, "删除成功！",
                            "W-nut Tips", JOptionPane.PLAIN_MESSAGE);
                    textField_DeleteGoodsName.setText("");
                    // 刷新商品列表
                    loadGoodsInfo();
                } else {
                    JOptionPane.showMessageDialog(this, "删除失败！",
                            "W-nut Errors", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    // 取消删除商品事件处理
    private void button_DeleteCancelActionPerformed(ActionEvent e) {
        textField_DeleteGoodsName.setText("");
    }
    
    // 查询所有商品事件处理
    private void button_QueryAllGoodsActionPerformed(ActionEvent e) {
        loadGoodsInfoToQueryTable();
    }
    
    // 根据商品名查询商品事件处理
    private void button_QueryGoodsByNameActionPerformed(ActionEvent e) {
        String goodsName = textField_QueryGoodsName.getText();
        
        if (goodsName.equals("")) {
            JOptionPane.showMessageDialog(this, "商品名不能为空！",
                    "W-nut Errors", JOptionPane.ERROR_MESSAGE);
        } else {
            queryController = new QueryController();
            queryController.InitGoodsHeader();
            
            DefaultTableModel tableModel = (DefaultTableModel) table_QueryGoods.getModel();
            
            // 清空原来内容
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            
            // 初始化表格头
            for (String str : GOODS_INFO_HEADER)
                tableModel.addColumn(str);
            
            queryController.QueryGoodsInfoByName(goodsName);
            
            if (GOODS_INFO_NUM.isEmpty()) {
                JOptionPane.showMessageDialog(this, "未找到该商品！",
                        "W-nut Tips", JOptionPane.WARNING_MESSAGE);
            } else {
                Vector<String> vec_goodsInfo = new Vector<>();
                
                vec_goodsInfo.add(GOODS_INFO_NUM.get(0));
                vec_goodsInfo.add(GOODS_INFO_NAME.get(0));
                vec_goodsInfo.add(GOODS_INFO_PRICE.get(0).toString());
                vec_goodsInfo.add(GOODS_INFO_STORE_NUM.get(0).toString());
                
                Object[] objects = vec_goodsInfo.toArray();
                tableModel.addRow(objects);
                vec_goodsInfo.clear();
            }
            
            GOODS_INFO_NUM.clear();
            GOODS_INFO_NAME.clear();
            GOODS_INFO_PRICE.clear();
            GOODS_INFO_STORE_NUM.clear();
            GOODS_INFO_HEADER.clear();
        }
    }
    
    // 修改商品事件处理
    private void button_UpdateGoodsActionPerformed(ActionEvent e) {
        String currentGoodsName = textField_UpdateGoodsCurrentName.getText();
        String newGoodsName = textField_UpdateGoodsNewName.getText();
        String newGoodsPrice = textField_UpdateGoodsNewPrice.getText();
        String newGoodsStoreNum = textField_UpdateGoodsNewStoreNum.getText();
        
        if (currentGoodsName.equals("")) {
            JOptionPane.showMessageDialog(this, "当前商品名不能为空！",
                    "W-nut Errors", JOptionPane.ERROR_MESSAGE);
        } else if (newGoodsName.equals("")) {
            JOptionPane.showMessageDialog(this, "新商品名不能为空！",
                    "W-nut Errors", JOptionPane.ERROR_MESSAGE);
        } else if (newGoodsPrice.equals("")) {
            JOptionPane.showMessageDialog(this, "商品价格不能为空！",
                    "W-nut Errors", JOptionPane.ERROR_MESSAGE);
        } else if (newGoodsStoreNum.equals("")) {
            JOptionPane.showMessageDialog(this, "商品库存不能为空！",
                    "W-nut Errors", JOptionPane.ERROR_MESSAGE);
        } else {
            updateController = new UpdateController();
            int isExists = updateController.checkGoodsExists(currentGoodsName);
            
            if (isExists == GOODS_NAME_NOT_EXISTS) {
                JOptionPane.showMessageDialog(this, "该商品不存在！",
                        "W-nut Errors", JOptionPane.ERROR_MESSAGE);
            } else {
                int isUpdated = updateController.updateGoodsInfo(currentGoodsName, newGoodsName, newGoodsPrice, newGoodsStoreNum);
                
                switch (isUpdated) {
                    case GOODS_PRICE_INVALID -> JOptionPane.showMessageDialog(this, "商品价格必须是有效的数字！",
                            "W-nut Errors", JOptionPane.ERROR_MESSAGE);
                    case GOODS_STORE_NUM_INVALID -> JOptionPane.showMessageDialog(this, "商品库存必须是有效的整数！",
                            "W-nut Errors", JOptionPane.ERROR_MESSAGE);
                    case UPDATE_SUCCESS -> {
                        JOptionPane.showMessageDialog(this, "修改商品成功！",
                                "W-nut Tips", JOptionPane.PLAIN_MESSAGE);
                        // 清空输入框
                        textField_UpdateGoodsCurrentName.setText("");
                        textField_UpdateGoodsNewName.setText("");
                        textField_UpdateGoodsNewPrice.setText("");
                        textField_UpdateGoodsNewStoreNum.setText("");
                        // 刷新商品列表
                        loadGoodsInfo();
                    }
                    default -> JOptionPane.showMessageDialog(this, "修改商品失败！",
                            "W-nut Errors", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    // 取消修改商品事件处理
    private void button_UpdateCancelActionPerformed(ActionEvent e) {
        textField_UpdateGoodsCurrentName.setText("");
        textField_UpdateGoodsNewName.setText("");
        textField_UpdateGoodsNewPrice.setText("");
        textField_UpdateGoodsNewStoreNum.setText("");
    }

    //初始化框架
    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Ethan Hunt
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
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        good_add = new JButton();
        button2 = new JButton();
        panel_DeleteGoods = new JPanel();
        label_DeleteGoodsName = new JLabel();
        textField_DeleteGoodsName = new JTextField();
        button_DeleteGoods = new JButton();
        button_DeleteCancel = new JButton();
        panel_QueryGoods = new JPanel();
        scrollPane_QueryGoods = new JScrollPane();
        table_QueryGoods = new JTable();
        button_QueryAllGoods = new JButton();
        label_QueryGoodsName = new JLabel();
        textField_QueryGoodsName = new JTextField();
        button_QueryGoodsByName = new JButton();
        panel_UpdateGoods = new JPanel();
        label_UpdateGoodsCurrentName = new JLabel();
        textField_UpdateGoodsCurrentName = new JTextField();
        label_UpdateGoodsNewName = new JLabel();
        textField_UpdateGoodsNewName = new JTextField();
        label_UpdateGoodsNewPrice = new JLabel();
        textField_UpdateGoodsNewPrice = new JTextField();
        label_UpdateGoodsNewStoreNum = new JLabel();
        textField_UpdateGoodsNewStoreNum = new JTextField();
        button_UpdateGoods = new JButton();
        button_UpdateCancel = new JButton();
        panel_OrderManage = new JPanel();
        button_QueryOrder = new JButton();
        button_AddOrder = new JButton();

        //======== this ========
        setTitle("W-nut GoodsManage");
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== tabbedPane_GoodsManage ========
        {

            //======== panel_BuyGoods ========
            {
                panel_BuyGoods.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder( 0
                , 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM
                , new java .awt .Font ("D\u0069alog" ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) ,
                panel_BuyGoods. getBorder( )) ); panel_BuyGoods. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e
                ) {if ("\u0062order" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
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
                button_CheckOrder.addActionListener(e -> button_CheckOrder_ActionPerformed(e));
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
                button_BuyIt.addActionListener(e -> button_BuyIt_ActionPerformed(e));
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

                //---- label1 ----
                label1.setText("\u5546\u54c1\u540d\u79f0");
                panel_AddGoods.add(label1);
                label1.setBounds(new Rectangle(new Point(20, 40), label1.getPreferredSize()));

                //---- label2 ----
                label2.setText("\u5546\u54c1\u4ef7\u683c");
                panel_AddGoods.add(label2);
                label2.setBounds(new Rectangle(new Point(20, 105), label2.getPreferredSize()));

                //---- label3 ----
                label3.setText("\u5546\u54c1\u5e93\u5b58");
                panel_AddGoods.add(label3);
                label3.setBounds(new Rectangle(new Point(20, 175), label3.getPreferredSize()));
                panel_AddGoods.add(textField1);
                textField1.setBounds(100, 40, 115, 40);
                panel_AddGoods.add(textField2);
                textField2.setBounds(100, 110, 115, 40);
                panel_AddGoods.add(textField3);
                textField3.setBounds(100, 175, 115, 40);

                //---- good_add ----
                good_add.setText("ADD");
                good_add.addActionListener(e -> good_addActionPerformed(e));
                panel_AddGoods.add(good_add);
                good_add.setBounds(new Rectangle(new Point(305, 60), good_add.getPreferredSize()));

                //---- button2 ----
                button2.setText("ESC");
                button2.addActionListener(e -> button2ActionPerformed(e));
                panel_AddGoods.add(button2);
                button2.setBounds(new Rectangle(new Point(305, 165), button2.getPreferredSize()));

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

            //======== panel_DeleteGoods ========
            {
                panel_DeleteGoods.setLayout(null);

                //---- label_DeleteGoodsName ----
                label_DeleteGoodsName.setText("\u5546\u54c1\u540d\u79f0");
                panel_DeleteGoods.add(label_DeleteGoodsName);
                label_DeleteGoodsName.setBounds(new Rectangle(new Point(30, 65), label_DeleteGoodsName.getPreferredSize()));
                panel_DeleteGoods.add(textField_DeleteGoodsName);
                textField_DeleteGoodsName.setBounds(115, 60, 130, textField_DeleteGoodsName.getPreferredSize().height);

                //---- button_DeleteGoods ----
                button_DeleteGoods.setText("\u5220\u9664");
                button_DeleteGoods.addActionListener(e -> button_DeleteGoodsActionPerformed(e));
                panel_DeleteGoods.add(button_DeleteGoods);
                button_DeleteGoods.setBounds(new Rectangle(new Point(305, 55), button_DeleteGoods.getPreferredSize()));

                //---- button_DeleteCancel ----
                button_DeleteCancel.setText("\u53d6\u6d88");
                button_DeleteCancel.addActionListener(e -> button_DeleteCancelActionPerformed(e));
                panel_DeleteGoods.add(button_DeleteCancel);
                button_DeleteCancel.setBounds(new Rectangle(new Point(305, 130), button_DeleteCancel.getPreferredSize()));

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel_DeleteGoods.getComponentCount(); i++) {
                        Rectangle bounds = panel_DeleteGoods.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel_DeleteGoods.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel_DeleteGoods.setMinimumSize(preferredSize);
                    panel_DeleteGoods.setPreferredSize(preferredSize);
                }
            }
            tabbedPane_GoodsManage.addTab("\u5220\u9664\u5546\u54c1", panel_DeleteGoods);

            //======== panel_QueryGoods ========
            {
                panel_QueryGoods.setLayout(null);

                //======== scrollPane_QueryGoods ========
                {
                    scrollPane_QueryGoods.setViewportView(table_QueryGoods);
                }
                panel_QueryGoods.add(scrollPane_QueryGoods);
                scrollPane_QueryGoods.setBounds(10, 5, 480, 265);

                //---- button_QueryAllGoods ----
                button_QueryAllGoods.setText("\u67e5\u8be2\u5168\u90e8");
                button_QueryAllGoods.addActionListener(e -> button_QueryAllGoodsActionPerformed(e));
                panel_QueryGoods.add(button_QueryAllGoods);
                button_QueryAllGoods.setBounds(new Rectangle(new Point(395, 295), button_QueryAllGoods.getPreferredSize()));

                //---- label_QueryGoodsName ----
                label_QueryGoodsName.setText("\u5546\u54c1\u540d\u79f0");
                panel_QueryGoods.add(label_QueryGoodsName);
                label_QueryGoodsName.setBounds(new Rectangle(new Point(15, 280), label_QueryGoodsName.getPreferredSize()));
                panel_QueryGoods.add(textField_QueryGoodsName);
                textField_QueryGoodsName.setBounds(75, 275, 130, textField_QueryGoodsName.getPreferredSize().height);

                //---- button_QueryGoodsByName ----
                button_QueryGoodsByName.setText("\u67e5\u8be2");
                button_QueryGoodsByName.addActionListener(e -> button_QueryGoodsByNameActionPerformed(e));
                panel_QueryGoods.add(button_QueryGoodsByName);
                button_QueryGoodsByName.setBounds(new Rectangle(new Point(220, 275), button_QueryGoodsByName.getPreferredSize()));

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel_QueryGoods.getComponentCount(); i++) {
                        Rectangle bounds = panel_QueryGoods.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel_QueryGoods.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel_QueryGoods.setMinimumSize(preferredSize);
                    panel_QueryGoods.setPreferredSize(preferredSize);
                }
            }
            tabbedPane_GoodsManage.addTab("\u67e5\u8be2\u5546\u54c1", panel_QueryGoods);

            //======== panel_UpdateGoods ========
            {
                panel_UpdateGoods.setLayout(null);

                //---- label_UpdateGoodsCurrentName ----
                label_UpdateGoodsCurrentName.setText("\u5f53\u524d\u5546\u54c1\u540d\u79f0");
                panel_UpdateGoods.add(label_UpdateGoodsCurrentName);
                label_UpdateGoodsCurrentName.setBounds(new Rectangle(new Point(15, 35), label_UpdateGoodsCurrentName.getPreferredSize()));
                panel_UpdateGoods.add(textField_UpdateGoodsCurrentName);
                textField_UpdateGoodsCurrentName.setBounds(120, 30, 125, textField_UpdateGoodsCurrentName.getPreferredSize().height);

                //---- label_UpdateGoodsNewName ----
                label_UpdateGoodsNewName.setText("\u65b0\u5546\u54c1\u540d\u79f0");
                panel_UpdateGoods.add(label_UpdateGoodsNewName);
                label_UpdateGoodsNewName.setBounds(new Rectangle(new Point(15, 90), label_UpdateGoodsNewName.getPreferredSize()));
                panel_UpdateGoods.add(textField_UpdateGoodsNewName);
                textField_UpdateGoodsNewName.setBounds(120, 85, 125, textField_UpdateGoodsNewName.getPreferredSize().height);

                //---- label_UpdateGoodsNewPrice ----
                label_UpdateGoodsNewPrice.setText("\u65b0\u5546\u54c1\u4ef7\u683c");
                panel_UpdateGoods.add(label_UpdateGoodsNewPrice);
                label_UpdateGoodsNewPrice.setBounds(new Rectangle(new Point(15, 140), label_UpdateGoodsNewPrice.getPreferredSize()));
                panel_UpdateGoods.add(textField_UpdateGoodsNewPrice);
                textField_UpdateGoodsNewPrice.setBounds(120, 135, 125, textField_UpdateGoodsNewPrice.getPreferredSize().height);

                //---- label_UpdateGoodsNewStoreNum ----
                label_UpdateGoodsNewStoreNum.setText("\u65b0\u5546\u54c1\u5e93\u5b58");
                panel_UpdateGoods.add(label_UpdateGoodsNewStoreNum);
                label_UpdateGoodsNewStoreNum.setBounds(new Rectangle(new Point(15, 195), label_UpdateGoodsNewStoreNum.getPreferredSize()));
                panel_UpdateGoods.add(textField_UpdateGoodsNewStoreNum);
                textField_UpdateGoodsNewStoreNum.setBounds(120, 190, 125, textField_UpdateGoodsNewStoreNum.getPreferredSize().height);

                //---- button_UpdateGoods ----
                button_UpdateGoods.setText("\u4fee\u6539");
                button_UpdateGoods.addActionListener(e -> button_UpdateGoodsActionPerformed(e));
                panel_UpdateGoods.add(button_UpdateGoods);
                button_UpdateGoods.setBounds(new Rectangle(new Point(315, 70), button_UpdateGoods.getPreferredSize()));

                //---- button_UpdateCancel ----
                button_UpdateCancel.setText("\u53d6\u6d88");
                button_UpdateCancel.addActionListener(e -> button_UpdateCancelActionPerformed(e));
                panel_UpdateGoods.add(button_UpdateCancel);
                button_UpdateCancel.setBounds(new Rectangle(new Point(315, 145), button_UpdateCancel.getPreferredSize()));

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel_UpdateGoods.getComponentCount(); i++) {
                        Rectangle bounds = panel_UpdateGoods.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel_UpdateGoods.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel_UpdateGoods.setMinimumSize(preferredSize);
                    panel_UpdateGoods.setPreferredSize(preferredSize);
                }
            }
            tabbedPane_GoodsManage.addTab("\u4fee\u6539\u5546\u54c1", panel_UpdateGoods);

            //======== panel_OrderManage ========
            {
                panel_OrderManage.setLayout(null);

                //---- button_QueryOrder ----
                button_QueryOrder.setText("\u67e5\u8be2\u8ba2\u5355");
                button_QueryOrder.addActionListener(e -> button_QueryOrderActionPerformed(e));
                panel_OrderManage.add(button_QueryOrder);
                button_QueryOrder.setBounds(new Rectangle(new Point(135, 110), button_QueryOrder.getPreferredSize()));

                //---- button_AddOrder ----
                button_AddOrder.setText("\u65b0\u589e\u8ba2\u5355");
                button_AddOrder.addActionListener(e -> button_AddOrderActionPerformed(e));
                panel_OrderManage.add(button_AddOrder);
                button_AddOrder.setBounds(new Rectangle(new Point(290, 110), button_AddOrder.getPreferredSize()));

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel_OrderManage.getComponentCount(); i++) {
                        Rectangle bounds = panel_OrderManage.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel_OrderManage.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel_OrderManage.setMinimumSize(preferredSize);
                    panel_OrderManage.setPreferredSize(preferredSize);
                }
            }
            tabbedPane_GoodsManage.addTab("\u8ba2\u5355\u7ba1\u7406", panel_OrderManage);
        }
        contentPane.add(tabbedPane_GoodsManage);
        tabbedPane_GoodsManage.setBounds(-5, 10, 520, 380);

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
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton good_add;
    private JButton button2;
    private JPanel panel_DeleteGoods;
    private JLabel label_DeleteGoodsName;
    private JTextField textField_DeleteGoodsName;
    private JButton button_DeleteGoods;
    private JButton button_DeleteCancel;
    private JPanel panel_QueryGoods;
    private JScrollPane scrollPane_QueryGoods;
    private JTable table_QueryGoods;
    private JButton button_QueryAllGoods;
    private JLabel label_QueryGoodsName;
    private JTextField textField_QueryGoodsName;
    private JButton button_QueryGoodsByName;
    private JPanel panel_UpdateGoods;
    private JLabel label_UpdateGoodsCurrentName;
    private JTextField textField_UpdateGoodsCurrentName;
    private JLabel label_UpdateGoodsNewName;
    private JTextField textField_UpdateGoodsNewName;
    private JLabel label_UpdateGoodsNewPrice;
    private JTextField textField_UpdateGoodsNewPrice;
    private JLabel label_UpdateGoodsNewStoreNum;
    private JTextField textField_UpdateGoodsNewStoreNum;
    private JButton button_UpdateGoods;
    private JButton button_UpdateCancel;
    private JPanel panel_OrderManage;
    private JButton button_QueryOrder;
    private JButton button_AddOrder;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private QueryController queryController;
    private AddController addController;
    private DeleteController deleteController;
    private UpdateController updateController;
    private String Current_CustomerName=null;
}
