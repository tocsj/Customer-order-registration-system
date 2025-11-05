/*
 * @author W-nut
 * FileInfo:view->登录控制器->模型行为操作->view
 */

package application_Controller;

import application_Action.QueryAdministratorAction;
import application_Constant.Constant;

public class LoginController implements Constant
{
    private final boolean id_verifiedMessage;

    public LoginController(String name,String password)
    {
        //模型层处理身份验证(即 用户名、密码判断) 后获取验证结果
        QueryAdministratorAction administratorLoginAction = new QueryAdministratorAction();
        administratorLoginAction.checkAdministrator(name, password);
        id_verifiedMessage= administratorLoginAction.getIDVerifiedMessage();
        //控制层将验证结果发送给视图层
    }

    public boolean getVerifiedMessage()
    {
        return id_verifiedMessage;
    }

}
