/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nextlettergame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Andrea
 */
public class NextLetterGame {
    
    private static ArrayList<String> words;
    
    private static ImageIcon iconUser;
    private static ImageIcon iconNormal;
    private static ImageIcon iconHappy;
    private static ImageIcon iconSad;
    private static ImageIcon iconThinking;
    private static ImageIcon iconWhat;
    
    private static JLabel userAvatar;
    private static JLabel cpuAvatar;
    private static JLabel round;
    private static JLabel word;
    private static JTextField insertLetter;
    
    private static final int USER_AVATAR_SMALL_SIZE = 225;
    private static final int USER_AVATAR_BIG_SIZE = 275;
    private static final int CPU_AVATAR_SMALL_SIZE = 200;
    private static final int CPU_AVATAR_BIG_SIZE = 250;
    
    public static void main(String[] args) {
        loadWords();
        getImages();
        setLookAndFeel();
        createWindow();
        
        insertLetter.addActionListener(new MyActionListener());
    }

    private static void loadWords() {
        words = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(NextLetterGame.class.getResourceAsStream("words.txt")));
        try {
            String line = br.readLine();
            while(line != null){
                words.add(line);
                line = br.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(NextLetterGame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(NextLetterGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static void getImages() {
        iconUser = new ImageIcon(NextLetterGame.class.getResource("imgs/user.png"));
        iconNormal = new ImageIcon(NextLetterGame.class.getResource("imgs/normal.png"));
        iconHappy = new ImageIcon(NextLetterGame.class.getResource("imgs/happy.png"));
        iconSad = new ImageIcon(NextLetterGame.class.getResource("imgs/sad.png"));
        iconThinking = new ImageIcon(NextLetterGame.class.getResource("imgs/thinking.png"));
        iconWhat = new ImageIcon(NextLetterGame.class.getResource("imgs/what.png"));
    }
    
    public static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {}
    }

    private static void createWindow() {
        
        //CREATE USER/AVATAR PANEL
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBackground(Color.blue);
        
        JLabel userName = new JLabel("TU");
        userName.setFont(new Font(userName.getFont().getFontName(), Font.PLAIN, 30));
        userName.setForeground(Color.white);
        userName.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        userAvatar = new JLabel(new ImageIcon(iconUser.getImage().getScaledInstance(USER_AVATAR_BIG_SIZE, USER_AVATAR_BIG_SIZE, Image.SCALE_SMOOTH)));
        userAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel userAvatarPanel = new JPanel(new GridBagLayout());
        userAvatarPanel.add(userAvatar);
        userAvatarPanel.setBackground(Color.blue);
        
        userPanel.add(userName);
        userPanel.add(userAvatarPanel);
        
        
        //CREATE CPU PANEL
        JPanel cpuPanel = new JPanel();
        cpuPanel.setLayout(new BoxLayout(cpuPanel, BoxLayout.Y_AXIS));
        cpuPanel.setBackground(Color.red);
        
        JLabel cpuName = new JLabel("CPU");
        cpuName.setFont(new Font(cpuName.getFont().getFontName(), Font.PLAIN, 30));
        cpuName.setForeground(Color.white);
        cpuName.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        cpuAvatar = new JLabel(new ImageIcon(iconNormal.getImage().getScaledInstance(CPU_AVATAR_SMALL_SIZE, CPU_AVATAR_SMALL_SIZE, Image.SCALE_SMOOTH)));
        cpuAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel cpuAvatarPanel = new JPanel(new GridBagLayout());
        cpuAvatarPanel.add(cpuAvatar);
        cpuAvatarPanel.setBackground(Color.red);
        
        cpuPanel.add(cpuName);
        cpuPanel.add(cpuAvatarPanel);
        
        
        //CREATE PLAYERS PANEL
        JPanel playersPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        playersPanel.add(userPanel);
        playersPanel.add(cpuPanel);
        playersPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        
        //CREATE GAME PANEL
        JPanel gamePanel = new JPanel(new GridBagLayout());
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        round = new JLabel("TURNO: TU");
        round.setFont(new Font(round.getFont().getFontName(), Font.PLAIN, 25));
        round.setAlignmentX(Component.CENTER_ALIGNMENT);
        round.setForeground(Color.blue);
        
        JLabel wordLabel = new JLabel("Parola:");
        wordLabel.setFont(new Font(wordLabel.getFont().getFontName(), Font.PLAIN, 25));
        wordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        wordLabel.setBorder(new EmptyBorder(20,0,0,0));
        
        word = new JLabel("_________");
        //word = new JLabel("ABAC");
        word.setFont(new Font(word.getFont().getFontName(), Font.PLAIN, 72));
        word.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel insertLetterLabel = new JLabel("Inserisci una lettera:");
        insertLetterLabel.setFont(new Font(insertLetterLabel.getFont().getFontName(), Font.PLAIN, 25));
        insertLetterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        insertLetterLabel.setBorder(new EmptyBorder(20,0,0,0));
        
        JPanel insertLetterPanel = new JPanel();
        insertLetter = new JTextField();
        insertLetter.setFont(new Font(insertLetterLabel.getFont().getFontName(), Font.PLAIN, 55));
        insertLetter.setPreferredSize(new Dimension(50, 65));
        insertLetterPanel.add(insertLetter);
        
        gamePanel.add(round);
        gamePanel.add(wordLabel);
        gamePanel.add(word);
        gamePanel.add(insertLetterLabel);
        gamePanel.add(insertLetterPanel);
        
        
        //CREATE WINDOW
        JFrame f = new JFrame("Next Letter Game");
        f.setLayout(new GridLayout(2, 1, 5, 5));
        f.add(playersPanel);
        f.add(gamePanel);
        
        f.setSize(800, 800);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    
    public static void userChoseLetter(String typed){
        if(word.getText().equals("_________")){
            word.setText("");
        }
        
        word.setText(word.getText()+typed.toUpperCase().charAt(0));
        insertLetter.setText("");
        insertLetter.setEnabled(false);
        
        String currentWord = word.getText().toLowerCase();
        
        userAvatar.setIcon(new ImageIcon(iconUser.getImage().getScaledInstance(CPU_AVATAR_SMALL_SIZE, CPU_AVATAR_SMALL_SIZE, Image.SCALE_SMOOTH)));
        round.setText("TURNO: CPU");
        round.setForeground(Color.red);
        
        if(words.contains(currentWord)){
            //YOU LOSE
            cpuAvatar.setIcon(new ImageIcon(iconHappy.getImage().getScaledInstance(CPU_AVATAR_BIG_SIZE, CPU_AVATAR_BIG_SIZE, Image.SCALE_SMOOTH)));
            round.setText("HAI PERSO...");
            round.setForeground(Color.red);
            JOptionPane.showMessageDialog(null,"HAI PERSO...");
        }else{
            cpuAvatar.setIcon(new ImageIcon(iconThinking.getImage().getScaledInstance(CPU_AVATAR_BIG_SIZE, CPU_AVATAR_BIG_SIZE, Image.SCALE_SMOOTH)));
            
            ArrayList<String> possibleWords = new ArrayList<>();
            for(String s : words){
                if(s.startsWith(currentWord))
                    possibleWords.add(s);
            }
            
            if(possibleWords.isEmpty()){
                //UNKNOWN WORD
                cpuAvatar.setIcon(new ImageIcon(iconWhat.getImage().getScaledInstance(CPU_AVATAR_BIG_SIZE, CPU_AVATAR_BIG_SIZE, Image.SCALE_SMOOTH)));
                JOptionPane.showMessageDialog(null,"Il computer non conosce questa parola, l'ultima lettera verrà eliminata");
                word.setText(word.getText().substring(0,word.getText().length()-1));
                userAvatar.setIcon(new ImageIcon(iconUser.getImage().getScaledInstance(CPU_AVATAR_BIG_SIZE, CPU_AVATAR_BIG_SIZE, Image.SCALE_SMOOTH)));
                cpuAvatar.setIcon(new ImageIcon(iconNormal.getImage().getScaledInstance(CPU_AVATAR_SMALL_SIZE, CPU_AVATAR_SMALL_SIZE, Image.SCALE_SMOOTH)));
                round.setText("TURNO: TU");
                round.setForeground(Color.blue);
                insertLetter.setEnabled(true);
            } else {
                ActionListener taskPerformer = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {

                        boolean found = false;
                        for(String s : possibleWords){
                            if(s.length() > currentWord.length()+1){
                                found = true;
                                break;
                            }
                        }
                        
                        if(found){
                            //GO ON
                            for(int i = 0; i < possibleWords.size(); i++){
                                if(possibleWords.get(i).length() == currentWord.length()+1){
                                    possibleWords.remove(possibleWords.get(i));
                                    i--;
                                }
                            }
                            
                            String bestWorld = possibleWords.get(0);
                            int len = bestWorld.length();
                            for(String s : possibleWords){
                                if(!s.equals(bestWorld)){
                                    if(s.length()%2==1 && s.length()<len){
                                        bestWorld = s;
                                        len = s.length();
                                    }
                                }
                            }
                            word.setText(bestWorld.toUpperCase().substring(0, currentWord.length()+1));
                            userAvatar.setIcon(new ImageIcon(iconUser.getImage().getScaledInstance(CPU_AVATAR_BIG_SIZE, CPU_AVATAR_BIG_SIZE, Image.SCALE_SMOOTH)));
                            cpuAvatar.setIcon(new ImageIcon(iconNormal.getImage().getScaledInstance(CPU_AVATAR_SMALL_SIZE, CPU_AVATAR_SMALL_SIZE, Image.SCALE_SMOOTH)));
                            round.setText("TURNO: TU");
                            round.setForeground(Color.blue);
                            insertLetter.setEnabled(true);
                        } else {
                            //VICTORY
                            word.setText(possibleWords.get(0).toUpperCase());
                            cpuAvatar.setIcon(new ImageIcon(iconSad.getImage().getScaledInstance(CPU_AVATAR_SMALL_SIZE, CPU_AVATAR_SMALL_SIZE, Image.SCALE_SMOOTH)));
                            userAvatar.setIcon(new ImageIcon(iconUser.getImage().getScaledInstance(CPU_AVATAR_BIG_SIZE, CPU_AVATAR_BIG_SIZE, Image.SCALE_SMOOTH)));
                            round.setText("HAI VINTO!!!");
                            round.setForeground(Color.blue);
                            JOptionPane.showMessageDialog(null,"HAI VINTO!!!");
                        }
                    }
                };

                Timer timer = new Timer(1000, taskPerformer);
                timer.setRepeats(false);
                timer.start();
            }
        }
    }
}
