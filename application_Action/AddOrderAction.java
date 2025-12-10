package application_Action;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddOrderAction extends LoadDatabaseAction
{
    public AddOrderAction()
    {

    }

    //添加订单记录
    public int addOrder(int cus_num, java.sql.Timestamp order_date, int[] generatedOrderId)
    {
        try
        {
            super.loadDatabaseAction();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, cus_num);
            preparedStatement.setTimestamp(2, order_date);
            preparedStatement.setString(3, "待支付"); // 默认订单状态为"待支付"
            preparedStatement.executeUpdate();
            
            // 获取生成的自增ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedOrderId[0] = generatedKeys.getInt(1);
            }
            generatedKeys.close();

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