package application_Controller;

import application_Action.LoadDatabaseAction;
import application_Constant.Constant;
import java.lang.reflect.InvocationTargetException;

public class LoadDatabaseController implements Constant
{
    private LoadDatabaseAction loadDatabaseAction;
    public LoadDatabaseController()
    {
        try
        {
            Object ob=Class.forName(LOAD_DATABASE).getDeclaredConstructor().newInstance();
        }
        catch(ClassNotFoundException | IllegalAccessException |
                InstantiationException | NoSuchMethodException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
    public void LoadDataBase()
    {
        loadDatabaseAction=new LoadDatabaseAction();
        loadDatabaseAction.loadDatabaseAction();
    }

}
