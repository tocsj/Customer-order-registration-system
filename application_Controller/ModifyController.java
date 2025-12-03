package application_Controller;

import application_Action.ModifyCustomerAction;

/**
 * 用户修改控制层
 */
public class ModifyController {
    private ModifyCustomerAction modifyCustomerAction;

    public int modifyCustomer(String id, String name, String tel, String address) {
        modifyCustomerAction=new ModifyCustomerAction();
        //int state=modifyCustomerAction.checkInfo(name,tel,address);
        //if(state==ModifyCustomerAction.ADD_CUSTOMER_ALLOWED)
           int state=modifyCustomerAction.modifyCustomer(id,name,tel,address);
        return state;
    }
}
