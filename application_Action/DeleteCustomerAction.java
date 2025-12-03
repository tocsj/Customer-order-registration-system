package application_Action;

import application_Constant.Constant;

import java.sql.PreparedStatement;
import java.util.Random;

public class DeleteCustomerAction extends LoadDatabaseAction{
    public DeleteCustomerAction()
    {

    }

    //删除客户信息
    public int deleteCustomer(String name)
    {
        try
        {
            super.loadDatabaseAction();

            PreparedStatement preparedStatement=connection.prepareStatement(DELETE_CUSTOMER);
            preparedStatement.setString(1,name);

            int result=preparedStatement.executeUpdate();
            super.disConnectDatabase();
            if(result==0){
                return CUSTOMER_NAME_NOT_OVERLOAD;
            }

            return Constant.DELETE_SUCCESS;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Constant.DATABASE_ERROR;

        }
    }
}
