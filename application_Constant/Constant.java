/*
 *@author W-nut
 *
 */

package application_Constant;

import java.util.ArrayList;

public interface Constant
{
    //the link to database
    String DATABASE_URL="jdbc:mysql://localhost:3306/customer_purchase?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
    String USER_NAME="root";
    String USER_PASSWORD="1234";

    //the name of actions
    String LOAD_DATABASE="application_Action.LoadDatabaseAction";

    //the query of SQL
    String QUERY_ADMINISTRATOR_NAME_PASSWORD="select ad_Name,ad_Password from CP_administrator";
    String QUERY_CUSTOMER="select* from CP_customer";
    String QUERY_CUSTOMER_NUM="select cus_Num from CP_customer";
    String QUERY_CUSTOMER_BY_NAME="select cus_Name from CP_customer where cus_Name=?";
    String QUERY_CUSTOMER_ALL_BY_NAME="select cus_Num,cus_Name,cus_Tel,cus_Address from CP_customer where cus_Name=?";
    String QUERY_CUSTOMER_NUM_BY_NAME="select cus_Num from CP_customer where cus_Name=?";
    String QUERY_CUSTOMER_NAME="select cus_Name from CP_customer";
    String QUERY_GOODS= "select* from CP_goods";
    String QUERY_GOODS_BY_NAME="select * from CP_goods where goods_Name=?";
    String QUERY_GOODS_STORE_NUM_BY_NAME="select goods_StoreNum from CP_goods where goods_Name=?";
    String QUERY_GOODS_STORE_NUM="select goods_StoreNum from CP_goods where goods_Name=?";
    String QUERY_GOODS_INFO_IN_ORDER_BY_ORDER_NUM= "select goods_Name,goods_ChooseNum from CP_goodsInfoInOrder where order_Num=?";
    String QUERY_GOODS_INFO_IN_ORDER_LAST_PK="select info_PK from CP_goodsInfoInOrder ";
    String QUERY_ORDER_NUM="select order_Num from CP_Order";
    String QUERY_INVOICE_NUM="select inv_Num from CP_invoice";

    String ADD_CUSTOMER="insert into CP_customer values(?,?,?,?) ";
    String ADD_ORDER="insert into CP_order(order_Num,order_CustomerNum,order_Date) values(?,?,?)";
    String ADD_GOODS_INFO_IN_ORDER="insert into CP_goodsInfoInOrder values(?,?,?,?,?)";
    String ADD_INVOICE="insert into CP_invoice values(?,?,?,?,?,?)";

    String MODIFY_CUSTOMER="update CP_customer set cus_Name=?,cus_Tel=?,cus_Address=? where cus_Num=?";

    String DELETE_CUSTOMER="delete from CP_customer where cus_Name=?";

    String UPDATE_GOODS_STORE_NUM="update CP_goods set goods_StoreNum=? where goods_Name=?";

    //the table info of customer
    ArrayList<String>CUSTOMER_INFO_HEADER=new ArrayList<>();            //客户表表头
    ArrayList<String>CUSTOMER_INFO_NUM=new ArrayList<>();               //客户号
    ArrayList<String>CUSTOMER_INFO_NAME=new ArrayList<>();              //客户名
    ArrayList<String>CUSTOMER_INFO_TEL=new ArrayList<>();               //客户电话
    ArrayList<String>CUSTOMER_INFO_ADDRESS=new ArrayList<>();           //客户地址
    ArrayList<String>CUSTOMER_INFO_ALL=new ArrayList<>();               //客户全部信息

    //the table info of goods
    ArrayList<String>GOODS_INFO_HEADER=new ArrayList<>();               //商品表表头
    ArrayList<String>GOODS_INFO_SELECTED_HEADER=new ArrayList<>();      //已选商品表表头
    ArrayList<String>GOODS_INFO_NUM=new ArrayList<>();                  //商品号
    ArrayList<String>GOODS_INFO_NAME=new ArrayList<>();                 //商品名
    ArrayList<Float>GOODS_INFO_PRICE=new ArrayList<>();                 //商品单价
    ArrayList<Integer>GOODS_INFO_STORE_NUM=new ArrayList<>();           //商品库存

    ArrayList<String>GOODS_SELECTED_HEADER=new ArrayList<>();           //已选商品表格头
    ArrayList<String>GOODS_SELECTED_NAME=new ArrayList<>();             //已选商品名
    ArrayList<String>GOODS_SELECTED_CHOOSE_NUM=new ArrayList<>();       //已选商品个数

    //the info of order table
    ArrayList<String>ORDER_INFO_NUM=new ArrayList<>();                  //查询的订单号
    ArrayList<String>INVOICE_INFO_NUM=new ArrayList<>();                //查询的发票号

    //some state flags
    int CUSTOMER_NAME_OVER_LENGTH=0;                                    //输入客户名长度错误
    int CUSTOMER_NAME_OVERLOAD=1;                                       //输入客户名重名
    int CUSTOMER_NAME_NOT_OVERLOAD=2;                                   //客户名不重名
    int CUSTOMER_TEL_OVER_LENGTH=3;                                     //输入客户名电话长度错误
    int CUSTOMER_ADDRESS_OVER_LENGTH=4;                                 //输入客户名地址长度错误
    int ADD_CUSTOMER_ALLOWED=5;                                         //输入客户信息全部合法
    int DATABASE_ERROR=6;                                               //数据库错误

    int GOODS_STORE_NUM_SHORT=7;                                        //商品库存不够
    int GOODS_STORE_NUM_ENOUGH=8;                                       //商品库存充足
    int INPUT_NOT_INTEGER=9;                                            //输入的不是整数
    int ERROR=10;                                                       //操作成功
    int SUCCESS=11;                                                     //操作失败
    int DELETE_SUCCESS=12;
    
    //goods info state(商品信息状态）
    int GOODS_NAME_INVALID             =13;         //商品名无效
    int GOODS_PRICE_INVALID            =14;         //商品价格无效
    int GOODS_STORE_NUM_INVALID        =15;         //商品库存无效
    int GOODS_NAME_EXISTS              =16;         //商品名已存在
    int ADD_GOODS_ALLOWED              =17;         //允许添加商品
    int GOODS_NAME_NOT_EXISTS          =18;         //商品名不存在
    int UPDATE_SUCCESS                 =19;         //更新成功

    //the ways of paying
    String [] PAY_WAYS={"支付宝","微信","银行卡","现金"};                   //支付方式
    
    //Additional SQL queries for goods management
    String QUERY_GOODS_NUM = "select goods_Num from CP_goods";
    String QUERY_GOODS_NAME = "select goods_Name from CP_goods";
    String ADD_GOODS = "insert into CP_goods values(?,?,?,?)";
    String DELETE_GOODS = "delete from CP_goods where goods_Name=?";
    String UPDATE_GOODS = "update CP_goods set goods_Name=?, goods_Price=?, goods_StoreNum=? where goods_Name=?";
}
