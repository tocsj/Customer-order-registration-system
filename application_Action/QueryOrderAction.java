package application_Action;

import application_Constant.Constant;

import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class QueryOrderAction extends LoadDatabaseAction implements Constant
{
    public QueryOrderAction()
    {

    }

    //查询最后一条订单号
    public String queryOrderNum()
    {
        try
        {
            super.loadDatabaseAction();

            ResultSet resultSet=statement.executeQuery(QUERY_ORDER_NUM);
            while(resultSet.next())
                ORDER_INFO_NUM.add(resultSet.getString("order_Num"));

            super.disConnectDatabase();

            if(ORDER_INFO_NUM.isEmpty())
            {
                return "O"+"000000001";//数据库中订单表没有记录
            }
            else
            {
                //获取最后一条订单记录的订单号
                String last_num=ORDER_INFO_NUM.get(ORDER_INFO_NUM.size()-1);
                
                // 检查last_num是否为空或长度不足
                if (last_num == null || last_num.length() <= 1) {
                    return "O"+"000000001"; // 返回默认订单号
                }

                try {
                    //加工新订单号(最后一条订单号+1)
                    int number=Integer.parseInt(last_num.substring(1))+1;
                    String str_number=String.format("%09d",number);
                    return ("O"+str_number).toString();
                } catch (NumberFormatException e) {
                    // 如果解析失败，返回默认订单号
                    return "O"+"000000001";
                }
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            ORDER_INFO_NUM.clear();
        }
    }
    
    // 查询所有订单
    public void queryAllOrders(DefaultTableModel tableModel) {
        try {
            super.loadDatabaseAction();
            
            String query = "SELECT o.order_Num, c.cus_Name, o.order_Date FROM CP_order o JOIN CP_customer c ON o.order_CustomerNum = c.cus_Num ORDER BY o.order_Date DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Vector<String> row = new Vector<>();
                row.add(resultSet.getString("order_Num"));
                row.add(resultSet.getString("cus_Name"));
                row.add(resultSet.getString("order_Date"));
                tableModel.addRow(row);
            }
            
            super.disConnectDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 根据订单号查询订单
    public void queryOrderByNum(String orderNum, DefaultTableModel tableModel) {
        try {
            super.loadDatabaseAction();
            
            String query = "SELECT o.order_Num, c.cus_Name, o.order_Date FROM CP_order o JOIN CP_customer c ON o.order_CustomerNum = c.cus_Num WHERE o.order_Num = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, orderNum);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                Vector<String> row = new Vector<>();
                row.add(resultSet.getString("order_Num"));
                row.add(resultSet.getString("cus_Name"));
                row.add(resultSet.getString("order_Date"));
                tableModel.addRow(row);
            } else {
                // 没有找到对应的订单
                Vector<String> row = new Vector<>();
                row.add("未找到订单");

                tableModel.addRow(row);
            }
            
            super.disConnectDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}