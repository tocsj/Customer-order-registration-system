package application_Action;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryInvoiceAction extends LoadDatabaseAction
{

    public QueryInvoiceAction()
    {

    }

    //查询最后一条发票号
    public String queryInvoiceNum()
    {
        try
        {
            super.loadDatabaseAction();

            ResultSet resultSet=statement.executeQuery(QUERY_INVOICE_NUM);
            while(resultSet.next())
                INVOICE_INFO_NUM.add(resultSet.getString("inv_Num"));

            super.disConnectDatabase();

            if(INVOICE_INFO_NUM.isEmpty())
            {
                return "I"+"000000001";//数据库中订单表没有记录
            }
            else
            {
                //获取最后一条订单记录的订单号
                String last_num=INVOICE_INFO_NUM.get(INVOICE_INFO_NUM.size()-1);

                //加工新订单号(最后一条订单号+1)
                int number=Integer.parseInt(last_num.substring(1))+1;
                String str_number=String.format("%09d",number);
                return ("I"+str_number);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            INVOICE_INFO_NUM.clear();
        }
    }
}
