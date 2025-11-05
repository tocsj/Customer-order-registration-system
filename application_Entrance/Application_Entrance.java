
/*
 * @author W-nut
 * 业务流程：登记客户信息->选购商品->生成订单->开具发票
 * 框架模式：模仿MVC(model-view-controller)
 */


package application_Entrance;

import application_Frame.Login_Frame;

@SuppressWarnings("unused")

public class Application_Entrance
{
    public static void main(String[] args)
    {
        Login_Frame login_frame=new Login_Frame();
    }
}
