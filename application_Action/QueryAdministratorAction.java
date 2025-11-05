/*
 * @author W-nut
 * FileInfo:登录操作
 */

package application_Action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class QueryAdministratorAction extends LoadDatabaseAction
{
    //身份验证消息
    private boolean id_verifiedMessage=false;

    public QueryAdministratorAction()
    {

    }

    public void checkAdministrator(String s_name,String s_password)
    {
        ArrayList<String> str_name = new ArrayList<>();
        ArrayList<String> str_password = new ArrayList<>();

        try
        {
            super.loadDatabaseAction();
            ResultSet resultSet=statement.executeQuery(QUERY_ADMINISTRATOR_NAME_PASSWORD);

            while(resultSet.next())
            {
                str_name.add(resultSet.getString("ad_Name"));
                str_password.add(resultSet.getString("ad_Password"));
            }
            int index=0;
            for(String name:str_name)
            {
                if(name.equals(s_name))
                {
                    if(str_password.get(index++).equals(s_password))
                    {
                        setIDVerifiedMessage();
                        System.out.println("欢迎您!  "+name+"！");
                        break;
                    }
                }
            }
            super.disConnectDatabase();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    private void setIDVerifiedMessage()
    {
        id_verifiedMessage=true;
    }
    public boolean getIDVerifiedMessage()
    {
        return id_verifiedMessage;
    }
}
