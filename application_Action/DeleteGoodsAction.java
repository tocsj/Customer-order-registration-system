package application_Action;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteGoodsAction extends LoadDatabaseAction {
    public DeleteGoodsAction() {

    }

    // 删除商品信息
    public int deleteGoods(String name) {
        try {
            super.loadDatabaseAction();

            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GOODS);
            preparedStatement.setString(1, name);

            int result = preparedStatement.executeUpdate();
            super.disConnectDatabase();
            if (result == 0) {
                return GOODS_NAME_NOT_EXISTS; // 没有该商品
            }

            return DELETE_SUCCESS; // 删除成功
        } catch (Exception e) {
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