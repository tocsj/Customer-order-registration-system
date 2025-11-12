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
    public int addGoods()
    {
        queryGoodsAction=new QueryGoodsAction();

        return 0;
    }

    //添加新订单记录
    public int addOrder(String order_num, int cus_num, Date order_date)
    {
        addOrderAction=new AddOrderAction();
        return addOrderAction.addOrder(order_num,cus_num,order_date);
    }

    //添加记录：订单中的商品信息表(自动生成主码int信息编号)
    public int addGoodsInfoInOrder(String order_num,String goods_num,String goods_name,String chooseNum)
    {
        queryGoodsInfoInOrderAction=new QueryGoodsInfoInOrderAction();
        int PK_info=queryGoodsInfoInOrderAction.queryLastNumPK();
        addGoodsInfoInOrder=new AddGoodsInfoInOrder();
        return addGoodsInfoInOrder.addGoodsInfoInOrder(PK_info,order_num,goods_num,goods_name,chooseNum);
    }

    //添加记录：发票表
    public int addInvoice(String invoice_num,String order_num,int cus_num,float total_price,String pay_way,Date date )
    {
        addInvoiceAction=new AddInvoiceAction();
        return addInvoiceAction.addInvoice(invoice_num,order_num,cus_num,total_price,pay_way,date);
    }
}
