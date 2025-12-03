package application_Controller;

import application_Action.DeleteCustomerAction;
import application_Action.ModifyCustomerAction;
import application_Constant.Constant;

public class DeleteController {
    private DeleteCustomerAction deleteCustomerAction;
    public int deleteCustomer(String name) {
        deleteCustomerAction=new DeleteCustomerAction();
        return deleteCustomerAction.deleteCustomer(name);
    }
}
