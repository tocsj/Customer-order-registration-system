package application_Controller;

import application_Action.*;
import application_Constant.Constant;

import java.sql.Date;

public class AddController implements Constant
{
    private AddCustomerAction addCustomerAction;
    private AddGoodsAction addGoodsAction;
    private AddOrderAction addOrderAction;
    private AddGoodsInfoInOrder addGoodsInfoInOrder;
    private AddInvoiceAction addInvoiceAction;
    private QueryCustomerAction queryCustomerAction; // 添加QueryCustomerAction引用

    public AddController()
    {

    }

    //添加客户
    public int addCustomer(String name,String tel,String address)
    {
        try
        {
            //检查输入合法性
            int name_length=name.length();
            if(name_length>20)
                return CUSTOMER_NAME_OVER_LENGTH;//名字超长

            float tel_length=tel.length();
            if(tel_length!=0&&tel_length!=11)
                return CUSTOMER_TEL_OVER_LENGTH;//电话号码长度不对

            int address_length=address.length();
            if(address_length>50)
                return CUSTOMER_ADDRESS_OVER_LENGTH;//地址超长

            addCustomerAction=new AddCustomerAction();
            queryCustomerAction=new QueryCustomerAction(); // 初始化QueryCustomerAction
            
            // 先检查客户信息的合法性
            int isAllowed = addCustomerAction.checkInfo(name, tel, address);
            if (isAllowed == ADD_CUSTOMER_ALLOWED) {
                // 获取新的客户号
                int customerNum = queryCustomerAction.queryCustomerNum(); // 使用QueryCustomerAction的方法
                if (customerNum == DATABASE_ERROR)
                    return DATABASE_ERROR;
                
                // 添加客户到数据库
                int result = addCustomerAction.addCustomer(customerNum, name, tel, address);
                return result;
            } else {
                return isAllowed; // 返回检查失败的原因
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
    }

    //添加商品
    public int addGoods(String name,String price,String storeNum)
    {
        try
        {
            //检查输入合法性
            int name_length=name.length();
            if(name_length>20)
                return GOODS_NAME_INVALID;//商品名超长

            addGoodsAction=new AddGoodsAction();
            // 使用正确的方法名checkInfo替代checkInputLegality
            int isAllowed=addGoodsAction.checkInfo(name,price,storeNum);
            switch (isAllowed)
            {
                case GOODS_PRICE_INVALID -> {return GOODS_PRICE_INVALID;}//价格无效
                case GOODS_STORE_NUM_INVALID -> {return GOODS_STORE_NUM_INVALID;}//库存无效
                case GOODS_NAME_EXISTS -> {return GOODS_NAME_EXISTS;}//商品名已存在
                case ADD_GOODS_ALLOWED -> {
                    //添加商品到数据库
                    int goods_num=addGoodsAction.queryGoodsNum();
                    if(goods_num==DATABASE_ERROR)
                        return DATABASE_ERROR;

                    float goods_price=Float.parseFloat(price);
                    int goods_storeNum=Integer.parseInt(storeNum);
                    int isAdd=addGoodsAction.addGoods(goods_num,name,goods_price,goods_storeNum);
                    if(isAdd==SUCCESS)
                        return SUCCESS;
                    else
                        return DATABASE_ERROR;
                }
                default -> {return DATABASE_ERROR;}
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
    }

    //添加订单记录
    public int addOrder(int cus_num, java.sql.Timestamp order_date, int[] generatedOrderId)
    {
        addOrderAction=new AddOrderAction();
        return addOrderAction.addOrder(cus_num, order_date, generatedOrderId);
    }

    //添加订单中的商品信息
    public int addGoodsInfoInOrder(int order_num,String goods_name,String choose_num)
    {
        addGoodsInfoInOrder=new AddGoodsInfoInOrder();
        return addGoodsInfoInOrder.addGoodsInfoInOrder(order_num,goods_name,choose_num);
    }

    // 添加发票记录
    // 修改方法签名，第一个参数改为int类型的订单ID
    public int addInvoice(int order_id, int cus_num, float total_price, String pay_way, java.sql.Timestamp date )
    {
        addInvoiceAction = new AddInvoiceAction();
        // 调用修改后的addInvoice方法，传入整数类型的订单ID
        return addInvoiceAction.addInvoice(order_id, cus_num, total_price, pay_way, date);
    }
}