package application_Action;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateGoodsAction extends LoadDatabaseAction {
    public UpdateGoodsAction() {

    }

    // 开发票支付成功商品库存数量减少
    public void DecreaseGoodsStoreNum(String goods_name, int store_num, String choose_num) {
        int new_storeNum = store_num - Integer.parseInt(choose_num);
        try {
            super.loadDatabaseAction();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GOODS_STORE_NUM);

            preparedStatement.setInt(1, new_storeNum);
            preparedStatement.setString(2, goods_name);

            int result = preparedStatement.executeUpdate();
            
            // 添加更新结果检查
            if (result > 0) {
                System.out.println("商品[" + goods_name + "]库存更新成功: " + store_num + " -> " + new_storeNum);
            } else {
                System.out.println("商品[" + goods_name + "]库存更新失败，影响行数为0");
            }

        } catch (SQLException e) {
            System.err.println("商品[" + goods_name + "]库存更新异常: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e); // 将异常抛出以便上层捕获
        } finally {
            super.disConnectDatabase();
        }

    }

    // 修改商品信息
    public int updateGoodsInfo(String oldName, String newName, float price, int storeNum) {
        try {
            super.loadDatabaseAction();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GOODS);

            preparedStatement.setString(1, newName);
            preparedStatement.setFloat(2, price);
            preparedStatement.setInt(3, storeNum);
            preparedStatement.setString(4, oldName);

            int result = preparedStatement.executeUpdate();
            super.disConnectDatabase();

            if (result > 0) {
                return UPDATE_SUCCESS; // 更新成功
            } else {
                return GOODS_NAME_NOT_EXISTS; // 商品不存在
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
    }
    
    // 根据商品名检查商品是否存在
    public int checkGoodsExists(String name) {
        try {
            super.loadDatabaseAction();
            
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_GOODS_BY_NAME);
            preparedStatement.setString(1, name);
            var resultSet = preparedStatement.executeQuery();
            
            if (!resultSet.next()) {
                return GOODS_NAME_NOT_EXISTS; // 没有该商品
            } else {
                return GOODS_NAME_EXISTS; // 商品存在
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return DATABASE_ERROR;
        } finally {
            super.disConnectDatabase();
        }
    }
}