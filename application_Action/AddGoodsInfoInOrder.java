package application_Action;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddGoodsInfoInOrder extends LoadDatabaseAction
{
    public AddGoodsInfoInOrder()
    {

    }

    //添加订单中的商品信息表记录
    public int addGoodsInfoInOrder(int order_num, String goods_name, String chooseNum)
    {
        int choose_num=Integer.parseInt(chooseNum);
        float goods_price = 0.0f;

        try {
            // 先查询商品价格
            super.loadDatabaseAction();
            PreparedStatement priceStatement = connection.prepareStatement(QUERY_GOODS_BY_NAME);
            priceStatement.setString(1, goods_name);
            ResultSet priceResult = priceStatement.executeQuery();
            
            if (priceResult.next()) {
                goods_price = priceResult.getFloat("goods_Price");
            }
            
            super.disConnectDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            return DATABASE_ERROR;
        }

        try
        {
            super.loadDatabaseAction();
            PreparedStatement preparedStatement=connection.prepareStatement(ADD_GOODS_INFO_IN_ORDER);
            preparedStatement.setInt(1, order_num);
            preparedStatement.setString(2, goods_name);
            preparedStatement.setFloat(3, goods_price);
            preparedStatement.setInt(4, choose_num);
            preparedStatement.executeUpdate();

            super.disConnectDatabase();

            return SUCCESS;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
    }
}