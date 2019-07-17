/*
 * grouplayout.java
 *
 * Created on 7 лютого 2008, 8:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package layout;
import javax.swing.*;
/**
 *
 * @author Technik
 */
public class grouplayout extends JFrame{
    
    /** Creates a new instance of grouplayout */
    public grouplayout() {
        super("example GroupLayout manager layout");
        this.setSize(300,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel_main=new JPanel();
        JPanel panel_top=new JPanel();
        JPanel panel_middle=new JPanel();
        JPanel panel_bottom=new JPanel();
        panel_top.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        panel_middle.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        panel_bottom.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        
        GroupLayout group_layout=new GroupLayout(panel_main);
        panel_main.setLayout(group_layout);
        
        group_layout.setAutoCreateGaps(true);
        group_layout.setAutoCreateContainerGaps(true);
        
        // Horizontal Group
        GroupLayout.SequentialGroup horizontal_group=group_layout.createSequentialGroup();
        horizontal_group.addGroup(group_layout.createParallelGroup()
                                .addComponent(panel_top,200,200,Short.MAX_VALUE)
                                .addComponent(panel_middle,200,200,Short.MAX_VALUE)
                                .addComponent(panel_bottom,200,200,Short.MAX_VALUE)
                                );
        group_layout.setHorizontalGroup(horizontal_group);
        
        // Vertical Group
        GroupLayout.SequentialGroup vertical_group=group_layout.createSequentialGroup();
        vertical_group.addGroup(group_layout.createParallelGroup()
                                .addComponent(panel_top,100,100,100)
                                );
        vertical_group.addGroup(group_layout.createParallelGroup()
                                .addComponent(panel_middle,100,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
                                );
        vertical_group.addGroup(group_layout.createParallelGroup()
                                .addComponent(panel_bottom,100,100,100)
                                );
        
        group_layout.setVerticalGroup(vertical_group);
        
        
        this.getContentPane().add(new javax.swing.JScrollPane(panel_main));
        this.setVisible(true);
    }
    
}
