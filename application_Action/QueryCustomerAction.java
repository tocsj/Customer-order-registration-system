/*
 * @author W-nut
 */


package application_Action;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

@SuppressWarnings("unused")
public class QueryCustomerAction extends LoadDatabaseAction
{
    public QueryCustomerAction()
    {

    }
    public void InitCustomerHeader()
    {
        CUSTOMER_INFO_HEADER.add("客户号");
        CUSTOMER_INFO_HEADER.add("客户名");
        CUSTOMER_INFO_HEADER.add("客户电话");
        CUSTOMER_INFO_HEADER.add("客户地址");
    }

    //查询客户号
    public int queryCustomerNum()
    {
        try
        {
            super.loadDatabaseAction();

            ArrayList<Integer> info_PK=new ArrayList<>();

            Calendar cal = Calendar.getInstance();
            int current_year = cal.get(Calendar.YEAR);

            ResultSet resultSet=statement.executeQuery(QUERY_CUSTOMER_NUM);
            while(resultSet.next())
                info_PK.add(resultSet.getInt("cus_Num"));

            if(info_PK.isEmpty())
            {
                return 2020000001;//客户表中还没记录
            }
            else
            {
                //仅仅是同一年！！！
                //获取最后一条客户记录的订单号
                String last_num=String.valueOf(info_PK.get(info_PK.size()-1));

                //加工新客户号(最后一条订单号+1)
                int number=Integer.parseInt(last_num.substring(4))+1;
                String new_number=String.valueOf(number);
                String new_num= current_year +String.format("%06d",number);

                //返回新客户号
                info_PK.clear();
                return Integer.parseInt(new_num);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
        finally
        {
            super.disConnectDatabase();
        }
    }
    //查询客户信息
    public void QueryCustomerInfo()
    {
        try
        {
            super.loadDatabaseAction();

            ResultSet resultSet=statement.executeQuery(QUERY_CUSTOMER);
            while(resultSet.next())
            {
                CUSTOMER_INFO_NUM.add(resultSet.getString("cus_Num"));
                CUSTOMER_INFO_NAME.add(resultSet.getString("cus_Name"));
                CUSTOMER_INFO_TEL.add(resultSet.getString("cus_Tel"));
                CUSTOMER_INFO_ADDRESS.add(resultSet.getString("cus_Address"));
            }
            super.disConnectDatabase();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //按客户名查询
    public int queryByName(String cus_name)
    {
        try
        {
            super.loadDatabaseAction();

            PreparedStatement preparedStatement=connection.prepareStatement(QUERY_CUSTOMER_BY_NAME);
            preparedStatement.setString(1,cus_name);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(!resultSet.next())
                return CUSTOMER_NAME_NOT_OVERLOAD;//没有该客户
            else
                return CUSTOMER_NAME_OVERLOAD;//该客户已存在

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
        finally
        {
            super.disConnectDatabase();
        }
    }

    //按客户名查询,返回客户所有信息
    public int queryByName(String cus_name,boolean differ)
    {
        try
        {
            super.loadDatabaseAction();

            PreparedStatement preparedStatement=connection.prepareStatement(QUERY_CUSTOMER_ALL_BY_NAME);
            preparedStatement.setString(1,cus_name);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                CUSTOMER_INFO_ALL.add(resultSet.getString("cus_Num"));
                CUSTOMER_INFO_ALL.add(resultSet.getString("cus_Name"));
                CUSTOMER_INFO_ALL.add(resultSet.getString("cus_Tel"));
                CUSTOMER_INFO_ALL.add(resultSet.getString("cus_Address"));

                super.disConnectDatabase();
                return SUCCESS;
            }

            super.disConnectDatabase();
            return ERROR;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
    }

    //按客户名查询客户号
    public int queryCustomerNumByName(String cus_name)
    {
        try
        {
            super.loadDatabaseAction();

            PreparedStatement preparedStatement=connection.prepareStatement(QUERY_CUSTOMER_NUM_BY_NAME);
            preparedStatement.setString(1,cus_name);
            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next())
                return resultSet.getInt("cus_Num");
            return ERROR;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
        finally
        {
            super.disConnectDatabase();
        }
    }


}
