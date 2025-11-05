package application_Action;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddInvoiceAction extends LoadDatabaseAction
{
    public AddInvoiceAction()
    {

    }

    //添加发票记录
    public int addInvoice(String invoice_num, String order_num, int cus_num, float total_price, String pay_way, Date date )
    {
        try
        {
            super.loadDatabaseAction();

            PreparedStatement preparedStatement=connection.prepareStatement(ADD_INVOICE);
            preparedStatement.setString(1,invoice_num);
            preparedStatement.setString(2,order_num);
            preparedStatement.setInt(3,cus_num);
            preparedStatement.setFloat(4,total_price);
            preparedStatement.setString(5,pay_way);
            preparedStatement.setDate(6,date);

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
