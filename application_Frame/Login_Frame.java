
/*
 * @author W-nut
 */
package application_Frame;

import application_Controller.LoginController;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login_Frame extends JFrame
{
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton button_Login;
    private JButton button_Esc;
    private JTextField textField_User_Name;
    private JLabel label_User_Name;
    private JLabel label_Password;
    private JPasswordField passwordField_Password;

    private Administrator_Frame administrator_frame;
    private LoginController loginController;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public Login_Frame()
    {
        initComponents();
        Load_Login_Frame();
    }
    public String get_textField_User_Name()
    {
        return textField_User_Name.getText();
    }
    public String get_passwordField_Password()
    {
        return String.valueOf(passwordField_Password.getPassword());
    }

    private void Load_Login_Frame()
    {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400,300);
        this.setVisible(true);
    }
    private void button_Login_MouseClicked(MouseEvent e)
    {
        boolean input_message= !get_textField_User_Name().equals("") && !get_passwordField_Password().equals("");
        if(input_message)
        {
            loginController=new LoginController(get_textField_User_Name(),get_passwordField_Password());
            boolean id_verifiedMessage = loginController.getVerifiedMessage();
            if(id_verifiedMessage)
            {
                administrator_frame = new Administrator_Frame();
                this.setVisible(false);
            }
            else
                JOptionPane.showMessageDialog(this,"用户名或密码错误！");
        }
        else
            JOptionPane.showMessageDialog(this,"用户名和密码不能为空！");
    }

    private void button_Esc_MouseClicked(MouseEvent e)
    {
        System.exit(0);
    }

    private void initComponents()
    {

        // Generated using JFormDesigner Evaluation license - nut
        button_Login = new JButton();
        button_Esc = new JButton();
        textField_User_Name = new JTextField();
        label_User_Name = new JLabel();
        label_Password = new JLabel();
        passwordField_Password = new JPasswordField();

        //======== this ========
        setTitle("W-nut Login");
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button_Login ----
        button_Login.setText("Login");
        button_Login.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                button_Login_MouseClicked(e);
            }
        });
        contentPane.add(button_Login);
        button_Login.setBounds(new Rectangle(new Point(115, 215), button_Login.getPreferredSize()));

        //---- button_Esc ----
        button_Esc.setText("Esc");
        button_Esc.addMouseListener(new MouseAdapter()
        {
            @Override       //重写父类方法声明
            public void mouseClicked(MouseEvent e)
            {
                button_Esc_MouseClicked(e);
            }
        });
        contentPane.add(button_Esc);
        button_Esc.setBounds(new Rectangle(new Point(265, 215), button_Esc.getPreferredSize()));
        contentPane.add(textField_User_Name);
        textField_User_Name.setBounds(155, 65, 135, textField_User_Name.getPreferredSize().height);

        //---- label_User_Name ----
        label_User_Name.setText("User_Name");
        contentPane.add(label_User_Name);
        label_User_Name.setBounds(new Rectangle(new Point(70, 70), label_User_Name.getPreferredSize()));

        //---- label_Password ----
        label_Password.setText("Password");
        contentPane.add(label_Password);
        label_Password.setBounds(new Rectangle(new Point(70, 125), label_Password.getPreferredSize()));
        contentPane.add(passwordField_Password);
        passwordField_Password.setBounds(155, 115, 135, passwordField_Password.getPreferredSize().height);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++)
            {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

    }


}
