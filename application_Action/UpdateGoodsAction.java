package application_Action;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateGoodsAction extends LoadDatabaseAction
{
    public UpdateGoodsAction()
    {

    }

    //开发票支付成功商品库存数量减少
    public void DecreaseGoodsStoreNum(String goods_name,int store_num,String choose_num)
    {
        int new_storeNum=store_num-Integer.parseInt(choose_num);
        try
        {
            super.loadDatabaseAction();
            PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_GOODS_STORE_NUM);

            preparedStatement.setInt(1,new_storeNum);
            preparedStatement.setString(2,goods_name);

            preparedStatement.executeUpdate();

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            super.disConnectDatabase();
        }

    }


}
