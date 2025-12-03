package application_Action;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryOrderAction extends LoadDatabaseAction
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
}