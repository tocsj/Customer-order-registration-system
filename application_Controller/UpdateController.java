package application_Controller;

import application_Action.QueryGoodsAction;
import application_Action.UpdateGoodsAction;
import application_Constant.Constant;

public class UpdateController implements Constant
{
    private UpdateGoodsAction updateGoodsAction;
    private QueryGoodsAction queryGoodsAction;

    public UpdateController()
    {

    }

    //通过商品名，商品选购数量减少库存(仅操作单个商品)
    public void UpdateGoodsStoreNum(String goods_name,String choose_num )
    {
        //查询该商品的库存数量
        queryGoodsAction=new QueryGoodsAction();
        int storeNum=queryGoodsAction.queryGoodsStoreNumByName(goods_name);

        //更新库存数量
        updateGoodsAction=new UpdateGoodsAction();
        updateGoodsAction.DecreaseGoodsStoreNum(goods_name,storeNum,choose_num);
    }


}
