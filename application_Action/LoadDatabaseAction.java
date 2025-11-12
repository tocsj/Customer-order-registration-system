/*
 * @author W-nut
 * FileInfo:连接SQL Server数据库
 * URL：jdbc:sqlserver://<machine_name><:port>;DatabaseName=<dbname>//没有microsoft！！！
 */

package application_Action;

import application_Constant.Constant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LoadDatabaseAction implements Constant
{
    public Connection connection;
    public Statement statement;
    public LoadDatabaseAction()
    {

    }
    public void loadDatabaseAction()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 8.x 驱动
            String url = "jdbc:mysql://localhost:3306/customer_purchase"
                    + "?useUnicode=true&characterEncoding=utf8"
                    + "&useSSL=false&serverTimezone=Asia/Shanghai";
            String user = "root";
            String pwd  = "1234";
            connection = DriverManager.getConnection(url, user, pwd);
            System.out.println("连接数据库成功！");
            statement=connection.createStatement();
            System.out.println("获取SQL语句对象成功!");
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
            System.err.println("加载类失败！");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.err.println("连接数据库失败！");
        }
    }
    public void disConnectDatabase()
    {
        try
        {
            connection.close();
            statement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
