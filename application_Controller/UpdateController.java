package application_Controller;

import application_Action.QueryGoodsAction;
import application_Action.UpdateGoodsAction;
import application_Constant.Constant;

public class UpdateController implements Constant {
    private UpdateGoodsAction updateGoodsAction;
    private QueryGoodsAction queryGoodsAction;

    public UpdateController() {

    }

    // 通过商品名，商品选购数量减少库存(仅操作单个商品)
    public void UpdateGoodsStoreNum(String goods_name, String choose_num) {
        // 查询该商品的库存数量
        queryGoodsAction = new QueryGoodsAction();
        int storeNum = queryGoodsAction.queryGoodsStoreNumByName(goods_name);

        // 更新库存数量
        updateGoodsAction = new UpdateGoodsAction();
        updateGoodsAction.DecreaseGoodsStoreNum(goods_name, storeNum, choose_num);
    }
    
    // 修改商品信息
    public int updateGoodsInfo(String oldName, String newName, String price, String storeNum) {
        try {
            // 检查价格是否为有效数字
            float goodsPrice = Float.parseFloat(price);
            
            // 检查库存是否为有效整数
            int goodsStoreNum = Integer.parseInt(storeNum);
            
            updateGoodsAction = new UpdateGoodsAction();
            return updateGoodsAction.updateGoodsInfo(oldName, newName, goodsPrice, goodsStoreNum);
        } catch (NumberFormatException e) {
            if (!price.matches("-?\\d+(\\.\\d+)?")) {
                return GOODS_PRICE_INVALID; // 价格无效
            }
            return GOODS_STORE_NUM_INVALID; // 库存无效
        }
    }
    
    // 检查商品是否存在
    public int checkGoodsExists(String name) {
        updateGoodsAction = new UpdateGoodsAction();
        return updateGoodsAction.checkGoodsExists(name);
    }
}