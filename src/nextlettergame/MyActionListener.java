/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nextlettergame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Andrea
 */
public class MyActionListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent ae) {
        String typed = ((JTextField)ae.getSource()).getText();
        if(Character.isLetter(typed.charAt(0)))
            NextLetterGame.userChoseLetter(typed);
        else{
            JOptionPane.showMessageDialog(null,"Devi inserire una lettera","Attenzione!",JOptionPane.WARNING_MESSAGE);
        }
    }
    
}
