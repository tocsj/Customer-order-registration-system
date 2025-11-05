/*
 *@author W-nut
 *
 */

package application_Action;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddCustomerAction extends LoadDatabaseAction
{
    public AddCustomerAction()
    {

    }

    //合法性检查返回0000 0000 后四位分别检查地址长度，电话长度，名字重名，名字长度
    public int checkInfo(String name,String tel,String address)
    {
        try
        {
            //检查是否重名
            if(name.length()>20)
            {
                System.out.println(name+name.length());
                return CUSTOMER_NAME_OVER_LENGTH;//名字长度错误
            }

            super.loadDatabaseAction();
            ResultSet resultSet=statement.executeQuery(QUERY_CUSTOMER_NAME);
            while(resultSet.next())
            {
                CUSTOMER_INFO_NAME.add(resultSet.getString("cus_Name"));
            }
            for(String str:CUSTOMER_INFO_NAME)
            {
                if(str.equals(name))
                    return CUSTOMER_NAME_OVERLOAD; //名字不合法,重名或超过长度
            }
            CUSTOMER_INFO_NAME.clear();
            super.disConnectDatabase();

            //检查电话和地址长度
            if(tel.length()!=11)
                return CUSTOMER_TEL_OVER_LENGTH;  //电话位数错误
            if(address.length()>50)
                return CUSTOMER_ADDRESS_OVER_LENGTH;  //地址超长
            return ADD_CUSTOMER_ALLOWED;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
    }

    public int addCustomer(int num,String name,String tel,String address)
    {
        try
        {
            super.loadDatabaseAction();
            PreparedStatement preparedStatement=connection.prepareStatement(ADD_CUSTOMER);

            preparedStatement.setInt(1,num);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,tel);
            preparedStatement.setString(4,address);
            preparedStatement.executeUpdate();

            super.disConnectDatabase();

            return ADD_CUSTOMER_ALLOWED;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
    }
}
