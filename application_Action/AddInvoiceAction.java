package application_Action;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AddInvoiceAction extends LoadDatabaseAction
{
    public AddInvoiceAction()
    {

    }

    //添加发票记录
    public int addInvoice(int order_id, int cus_num, float total_price, String pay_way, java.sql.Timestamp date )
    {
        try
        {
            super.loadDatabaseAction();

            // 注意：发票号(inv_Num)是自增主键，不需要手动插入
            // 修改SQL语句，去掉inv_Num字段，让数据库自动生成
            PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO CP_invoice (inv_OrderNum, inv_CustomerNum, inv_TotalMoney, inv_PayType, inv_Date) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);

            // 根据数据库表结构调整参数设置顺序
            preparedStatement.setInt(1, order_id);           // inv_OrderNum (整数类型)
            preparedStatement.setInt(2, cus_num);            // inv_CustomerNum
            preparedStatement.setFloat(3, total_price);      // inv_TotalMoney
            preparedStatement.setString(4, pay_way);         // inv_PayType
            preparedStatement.setTimestamp(5, date);         // inv_Date (使用Timestamp保持时分秒)

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