/*
 *@author W-nut
 *
 */

package application_Action;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryGoodsAction extends LoadDatabaseAction
{
    public  QueryGoodsAction()
    {

    }
    public void InitGoodsHeader()
    {
        GOODS_INFO_HEADER.add("商品号");
        GOODS_INFO_HEADER.add("商品名");
        GOODS_INFO_HEADER.add("商品单价");
        GOODS_INFO_HEADER.add("库存数量");
    }

    //查询全部商品的全部信息
    public void QueryGoodsInfo()
    {
        try
        {
            super.loadDatabaseAction();

            ResultSet resultSet=statement.executeQuery(QUERY_GOODS);
            while(resultSet.next())
            {
                GOODS_INFO_NUM.add(resultSet.getString("goods_Num"));
                GOODS_INFO_NAME.add(resultSet.getString("goods_Name"));
                GOODS_INFO_PRICE.add(resultSet.getFloat("goods_Price"));
                GOODS_INFO_STORE_NUM.add(resultSet.getInt("goods_StoreNum"));
            }
            super.disConnectDatabase();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //检查购买数量合法性
    public int checkInputBuyNum(String buyName,String buyNum)
    {
        try
        {
            super.loadDatabaseAction();

            PreparedStatement preparedStatement=connection.prepareStatement(QUERY_GOODS_STORE_NUM);
            preparedStatement.setString(1,buyName);
            ResultSet resultSet=preparedStatement.executeQuery();
            resultSet.next();
            int storeNum=resultSet.getInt("goods_StoreNum");

            super.disConnectDatabase();

            if(!buyNum.matches("^-?[1-9]\\d*$"))
                return INPUT_NOT_INTEGER;   //不是整数
            else if(storeNum <= Integer.parseInt(buyNum))
                return GOODS_STORE_NUM_SHORT;  //库存不够
            else
                return GOODS_STORE_NUM_ENOUGH; //库存充足

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
    }

    //通过商品名查询商品信息
    public void queryGoodsInfoByName(String goods_name)
    {
        try
        {
            super.loadDatabaseAction();

            PreparedStatement preparedStatement=connection.prepareStatement(QUERY_GOODS_BY_NAME);
            preparedStatement.setString(1,goods_name);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next())
            {
                GOODS_INFO_NUM.add(resultSet.getString("goods_Num"));
                GOODS_INFO_NAME.add(resultSet.getString("goods_Name"));
                GOODS_INFO_PRICE.add(resultSet.getFloat("goods_Price"));
                GOODS_INFO_STORE_NUM.add(resultSet.getInt("goods_StoreNum"));
            }

            super.disConnectDatabase();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    //通过商品名查询库存数量
    public int queryGoodsStoreNumByName(String goods_name)
    {
        try
        {
            super.loadDatabaseAction();

            PreparedStatement preparedStatement=connection.prepareStatement(QUERY_GOODS_STORE_NUM_BY_NAME);
            preparedStatement.setString(1,goods_name);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
                return resultSet.getInt("goods_StoreNum");
            else {
                System.err.println("未找到商品[" + goods_name + "]的库存信息");
                return ERROR;
            }
        }
        catch(SQLException e)
        {
            System.err.println("查询商品[" + goods_name + "]库存异常: " + e.getMessage());
            e.printStackTrace();
            return DATABASE_ERROR;
        }
        finally
        {
            super.disConnectDatabase();
        }
    }
}
