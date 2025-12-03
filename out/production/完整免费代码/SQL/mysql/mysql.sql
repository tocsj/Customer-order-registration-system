create table if not exists customer_purchase.cp_administrator
(
    ad_Name     varchar(50)  not null
        primary key,
    ad_Password varchar(100) not null
);

create table if not exists customer_purchase.cp_customer
(
    cus_Num     int auto_increment
        primary key,
    cus_Name    varchar(100) not null,
    cus_Tel     varchar(30)  null,
    cus_Address varchar(255) null
);

create table if not exists customer_purchase.cp_goods
(
    goods_Num      int auto_increment
        primary key,
    goods_Name     varchar(100)   not null,
    goods_Price    decimal(10, 2) not null,
    goods_StoreNum int default 0  not null,
    constraint goods_Name
        unique (goods_Name)
);

create table if not exists customer_purchase.cp_order
(
    order_Num         int auto_increment
        primary key,
    order_CustomerNum int                                not null,
    order_Date        datetime default CURRENT_TIMESTAMP null,
    constraint cp_order_ibfk_1
        foreign key (order_CustomerNum) references customer_purchase.cp_customer (cus_Num)
);

create table if not exists customer_purchase.cp_goodsinfoinorder
(
    info_PK         int auto_increment
        primary key,
    order_Num       int            not null,
    goods_Name      varchar(100)   not null,
    goods_Price     decimal(10, 2) not null,
    goods_ChooseNum int            not null,
    constraint cp_goodsinfoinorder_ibfk_1
        foreign key (order_Num) references customer_purchase.cp_order (order_Num)
);

create index order_Num
    on customer_purchase.cp_goodsinfoinorder (order_Num);

create table if not exists customer_purchase.cp_invoice
(
    inv_Num         int auto_increment
        primary key,
    inv_OrderNum    int                                not null,
    inv_CustomerNum int                                not null,
    inv_TotalMoney  decimal(12, 2)                     not null,
    inv_PayType     varchar(20)                        null,
    inv_Date        datetime default CURRENT_TIMESTAMP null,
    constraint cp_invoice_ibfk_1
        foreign key (inv_OrderNum) references customer_purchase.cp_order (order_Num),
    constraint cp_invoice_ibfk_2
        foreign key (inv_CustomerNum) references customer_purchase.cp_customer (cus_Num)
);

create index inv_CustomerNum
    on customer_purchase.cp_invoice (inv_CustomerNum);

create index inv_OrderNum
    on customer_purchase.cp_invoice (inv_OrderNum);

create index order_CustomerNum
    on customer_purchase.cp_order (order_CustomerNum);

