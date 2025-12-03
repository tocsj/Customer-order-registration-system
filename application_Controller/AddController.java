/*
 *@author W-nut
 *
 */


package application_Controller;

import application_Action.*;
import application_Constant.Constant;

import java.sql.Date;

public class AddController implements Constant
{
    private AddCustomerAction addCustomerAction;
    private AddOrderAction addOrderAction;
    private AddGoodsInfoInOrder addGoodsInfoInOrder;
    private AddInvoiceAction addInvoiceAction;
    private QueryCustomerAction queryCustomerAction;
    private QueryGoodsAction queryGoodsAction;
    private QueryGoodsInfoInOrderAction queryGoodsInfoInOrderAction;
    private AddGoodsAction addGoodsAction;

    public AddController()
    {

    }

    //添加新客户
    public int addCustomer(String name,String tel,String address)
    {
        queryCustomerAction=new QueryCustomerAction();
        int new_cus_num=queryCustomerAction.queryCustomerNum();

        addCustomerAction=new AddCustomerAction();
        int state=addCustomerAction.checkInfo(name,tel,address);
        if(state==ADD_CUSTOMER_ALLOWED)
            state=addCustomerAction.addCustomer(new_cus_num,name,tel,address);

        return state;
    }

    public int addGoods(String name, String price, String storeNum)
    {
        addGoodsAction = new AddGoodsAction();
        int state = addGoodsAction.checkInfo(name, price, storeNum);
        if (state == ADD_GOODS_ALLOWED) {
            int goodsNum = addGoodsAction.queryGoodsNum();
            float goodsPrice = Float.parseFloat(price);
            int goodsStoreNum = Integer.parseInt(storeNum);
            state = addGoodsAction.addGoods(goodsNum, name, goodsPrice, goodsStoreNum);
        }
        return state;
    }

    //添加新订单记录
    public int addOrder(int cus_num, Date order_date, int[] generatedOrderId)
    {
        addOrderAction=new AddOrderAction();
        return addOrderAction.addOrder(cus_num, order_date, generatedOrderId);
    }

    //添加记录：订单中的商品信息表(自动生成主码int信息编号)
    public int addGoodsInfoInOrder(int order_num, String goods_name, String chooseNum)
    {
        addGoodsInfoInOrder=new AddGoodsInfoInOrder();
        return addGoodsInfoInOrder.addGoodsInfoInOrder(order_num, goods_name, chooseNum);
    }

    //添加记录：发票表
    public int addInvoice(String invoice_num,String order_num,int cus_num,float total_price,String pay_way,Date date )
    {
        addInvoiceAction=new AddInvoiceAction();
        return addInvoiceAction.addInvoice(invoice_num,order_num,cus_num,total_price,pay_way,date);
    }
}
