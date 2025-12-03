package application_Controller;

import application_Action.DeleteCustomerAction;
import application_Action.DeleteGoodsAction;
import application_Constant.Constant;

public class DeleteController implements Constant {
    private DeleteCustomerAction deleteCustomerAction;
    private DeleteGoodsAction deleteGoodsAction;
    
    public DeleteController() {

    }
    
    public int deleteCustomer(String name) {
        deleteCustomerAction=new DeleteCustomerAction();
        return deleteCustomerAction.deleteCustomer(name);
    }
    
    public int deleteGoods(String name) {
        deleteGoodsAction = new DeleteGoodsAction();
        return deleteGoodsAction.deleteGoods(name);
    }
    
    public int checkGoodsExists(String name) {
        deleteGoodsAction = new DeleteGoodsAction();
        return deleteGoodsAction.checkGoodsExists(name);
    }
}