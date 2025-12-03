package application_Action;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddGoodsAction extends LoadDatabaseAction
{
    public AddGoodsAction()
    {

    }

    //查询商品表中的商品数量，用于生成新的商品号
    public int queryGoodsNum()
    {
        try
        {
            super.loadDatabaseAction();

            ResultSet resultSet=statement.executeQuery(QUERY_GOODS_NUM);
            while(resultSet.next())
                GOODS_INFO_NUM.add(resultSet.getString("goods_Num"));

            super.disConnectDatabase();

            if(GOODS_INFO_NUM.isEmpty())
            {
                return 1;//数据库中商品表没有记录
            }
            else
            {
                //获取最后一条商品记录的商品号
                String last_num=GOODS_INFO_NUM.get(GOODS_INFO_NUM.size()-1);

                //加工新商品号(最后一条商品号+1)
                int number=Integer.parseInt(last_num)+1;
                GOODS_INFO_NUM.clear();
                return number;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
    }

    //检查商品信息合法性
    public int checkInfo(String name, String price, String storeNum)
    {
        try
        {
            //检查商品名是否为空或过长
            if(name.length() > 20 || name.isEmpty()) {
                return GOODS_NAME_INVALID;//商品名无效
            }

            //检查价格是否为有效数字
            try {
                Float.parseFloat(price);
            } catch (NumberFormatException e) {
                return GOODS_PRICE_INVALID;//价格无效
            }

            //检查库存是否为有效整数
            try {
                Integer.parseInt(storeNum);
            } catch (NumberFormatException e) {
                return GOODS_STORE_NUM_INVALID;//库存无效
            }

            super.loadDatabaseAction();
            ResultSet resultSet=statement.executeQuery(QUERY_GOODS_NAME);
            while(resultSet.next())
            {
                GOODS_INFO_NAME.add(resultSet.getString("goods_Name"));
            }
            for(String str:GOODS_INFO_NAME)
            {
                if(str.equals(name))
                    return GOODS_NAME_EXISTS; //商品名已存在
            }
            GOODS_INFO_NAME.clear();
            super.disConnectDatabase();

            return ADD_GOODS_ALLOWED; //允许添加
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
    }

    //添加商品到数据库
    public int addGoods(int num, String name, float price, int storeNum)
    {
        try
        {
            super.loadDatabaseAction();
            PreparedStatement preparedStatement=connection.prepareStatement(ADD_GOODS);

            preparedStatement.setInt(1, num);
            preparedStatement.setString(2, name);
            preparedStatement.setFloat(3, price);
            preparedStatement.setInt(4, storeNum);
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