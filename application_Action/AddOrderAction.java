package application_Action;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddOrderAction extends LoadDatabaseAction
{
    public AddOrderAction()
    {

    }

    //添加订单记录
    public int addOrder(String order_num, int cus_num, Date order_date )
    {
        try
        {
            super.loadDatabaseAction();
            PreparedStatement preparedStatement=connection.prepareStatement(ADD_ORDER);

            preparedStatement.setString(1,order_num);
            preparedStatement.setInt(2,cus_num);
            preparedStatement.setDate(3,order_date);
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
