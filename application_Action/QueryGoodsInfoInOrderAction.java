package application_Action;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryGoodsInfoInOrderAction extends LoadDatabaseAction
{
    public QueryGoodsInfoInOrderAction()
    {

    }

    //查询最后一条主码，自动生成新主码值
    public int queryLastNumPK()
    {
        try
        {
            super.loadDatabaseAction();

            ArrayList<Integer>info_PK=new ArrayList<>();
            ResultSet resultSet=statement.executeQuery(QUERY_GOODS_INFO_IN_ORDER_LAST_PK);
            while(resultSet.next())
                info_PK.add(resultSet.getInt("info_PK"));

            if(info_PK.isEmpty())
            {
                return 1;//订单商品信息表中还没记录
            }
            else
            {
                //获取最后一条记录
                int last_num=info_PK.get(info_PK.size()-1);

                //加工新号(最后一号+1)
                int new_num=last_num+1;

                info_PK.clear();
                return new_num;
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
        finally
        {
            super.disConnectDatabase();
        }
    }

    //按订单号查询商品名，选购数量(GOODS_SELECTED_NAME)结果保存到GOODS_SELECTED_NAME，GOODS_SELECTED_CHOOSE_NUM动态数组中
    public void queryGoodsNameByOrderNum(String order_num)
    {
        try
        {
            super.loadDatabaseAction();

            PreparedStatement preparedStatement=connection.prepareStatement(QUERY_GOODS_INFO_IN_ORDER_BY_ORDER_NUM);
            // 尝试将order_num解析为整数，如果是格式化字符串则提取其中的数字部分
            try {
                int orderId = Integer.parseInt(order_num);
                preparedStatement.setInt(1, orderId);
            } catch (NumberFormatException e) {
                // 如果不是纯数字，则尝试提取格式化字符串中的数字部分（如"O000000001" -> 1）
                if (order_num != null && order_num.startsWith("O") && order_num.length() > 1) {
                    int orderId = Integer.parseInt(order_num.substring(1));
                    preparedStatement.setInt(1, orderId);
                } else {
                    // 如果都不是，直接使用原字符串
                    preparedStatement.setString(1, order_num);
                }
            }
            ResultSet resultSet=preparedStatement.executeQuery();

            while(resultSet.next())
            {
                GOODS_SELECTED_NAME.add(resultSet.getString("goods_Name"));
                GOODS_SELECTED_CHOOSE_NUM.add(String.valueOf(resultSet.getInt("goods_ChooseNum")));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            super.disConnectDatabase();
        }
    }

}
