package application_Action;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddGoodsInfoInOrder extends LoadDatabaseAction
{
    public AddGoodsInfoInOrder()
    {

    }

    //添加订单中的商品信息表记录
    public int addGoodsInfoInOrder(int new_PK,String order_num,String goods_num,String goods_name,String chooseNum)
    {
        int goods_Num=Integer.parseInt(goods_num);
        int choose_num=Integer.parseInt(chooseNum);

        try
        {
            super.loadDatabaseAction();
            PreparedStatement preparedStatement=connection.prepareStatement(ADD_GOODS_INFO_IN_ORDER);
            preparedStatement.setInt(1,new_PK);
            preparedStatement.setString(2,order_num);
            preparedStatement.setInt(3,goods_Num);
            preparedStatement.setString(4,goods_name);
            preparedStatement.setInt(5,choose_num);
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
