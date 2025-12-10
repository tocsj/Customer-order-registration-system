/*
 * Created by JFormDesigner on Wed Dec 18 16:30:00 CST 2024
 */

package application_Frame_Administrator_ChildFrame;

import application_Action.QueryAdministratorAction;
import application_Constant.Constant;
import application_Frame.Administrator_Frame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class OwnInfo_Frame extends JFrame implements Constant {
    public OwnInfo_Frame() {
        initComponents();
        loadOwnInfo();
    }

    private void loadOwnInfo() {
        // 查询管理员信息
        QueryAdministratorAction queryAdminAction = new QueryAdministratorAction();
        String adminName = queryAdminAction.queryCurrentAdminName();
        
        if (adminName != null) {
            textField_AdminName.setText(adminName);
        } else {
            JOptionPane.showMessageDialog(this, "获取管理员信息失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void button_Close_ActionPerformed(ActionEvent e) {
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        textField_AdminName = new JTextField();
        button_Close_ = new JButton();

        //======== this ========
        setTitle("\u4e2a\u4eba\u4fe1\u606f");
        setMinimumSize(new Dimension(300, 150));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- label1 ----
                label1.setText("\u7ba1\u7406\u5458\u540d\uff1a");
                contentPanel.add(label1);
                label1.setBounds(new Rectangle(new Point(30, 35), label1.getPreferredSize()));
                contentPanel.add(textField_AdminName);
                textField_AdminName.setBounds(105, 30, 130, textField_AdminName.getPreferredSize().height);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < contentPanel.getComponentCount(); i++) {
                        Rectangle bounds = contentPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = contentPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    contentPanel.setMinimumSize(preferredSize);
                    contentPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //---- button_Close_ ----
            button_Close_.setText("\u5173\u95ed");
            button_Close_.addActionListener(e -> button_Close_ActionPerformed(e));
            dialogPane.add(button_Close_, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JTextField textField_AdminName;
    private JButton button_Close_;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}