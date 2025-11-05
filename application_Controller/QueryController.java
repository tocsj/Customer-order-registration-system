/*
 *@author W-nut
 *
 */

package application_Controller;

import application_Action.*;
import application_Constant.Constant;

public class QueryController implements Constant
{
    private QueryCustomerAction queryCustomerAction;
    private QueryGoodsAction queryGoodsAction;
    private QueryOrderAction queryOrderAction;
    private QueryInvoiceAction queryInvoiceAction;
    private QueryGoodsInfoInOrderAction queryGoodsInfoInOrderAction;

    public QueryController()
    {

    }
    //初始化客户Table表头
    public void InitCustomerHeader()
    {
        queryCustomerAction=new QueryCustomerAction();
        queryCustomerAction.InitCustomerHeader();
    }
    //查询客户Table表信息
    public void QueryCustomerInfo()
    {
        queryCustomerAction=new QueryCustomerAction();
        queryCustomerAction.QueryCustomerInfo();
    }

    //按客户名查询
    public int QueryCustomerByName(String name)
    {
        queryCustomerAction=new QueryCustomerAction();
        return queryCustomerAction.queryByName(name);
    }

    //按客户名查询
    public int QueryCustomerByName(String name, boolean verify)
    {
        queryCustomerAction=new QueryCustomerAction();
        return queryCustomerAction.queryByName(name,verify);
    }

    //按客户名查询客户号
    public int QueryCustomerNumByName(String cus_name)
    {
        queryCustomerAction=new QueryCustomerAction();
        return queryCustomerAction.queryCustomerNumByName(cus_name);
    }

    //初始化商品Table表头
    public void InitGoodsHeader()
    {
        queryGoodsAction=new QueryGoodsAction();
        queryGoodsAction.InitGoodsHeader();
    }
    //查询商品Table表信息
    public void QueryGoodsInfo()
    {
        queryGoodsAction=new QueryGoodsAction();
        queryGoodsAction.QueryGoodsInfo();
    }

    //通过商品名查询商品所有信息
    public void QueryGoodsInfoByName(String goods_name)
    {
        queryGoodsAction=new QueryGoodsAction();
        queryGoodsAction.queryGoodsInfoByName(goods_name);
    }

    //判断输入的购买数量是否合法
    public int checkInputBuyNum(String buyName,String buyNum)
    {
        queryGoodsAction=new QueryGoodsAction();
        return queryGoodsAction.checkInputBuyNum(buyName,buyNum);
    }

    //查询最后一条订单号
    public String QueryOrderNum()
    {
        queryOrderAction=new QueryOrderAction();
        return queryOrderAction.queryOrderNum();
    }

    //查询最后一条发票号
    public String QueryInvoiceNum()
    {
        queryInvoiceAction=new QueryInvoiceAction();
        return queryInvoiceAction.queryInvoiceNum();
    }

    //按按订单号查询商品信息(已选购商品名，商品选购数量)，结果保存到GOODS_SELECTED_NAME，GOODS_SELECTED_CHOOSE_NUM动态数组中
    public void QueryGoodsInfoInOrder(String order_num)
    {
        queryGoodsInfoInOrderAction=new QueryGoodsInfoInOrderAction();
        queryGoodsInfoInOrderAction.queryGoodsNameByOrderNum(order_num);
    }
}
