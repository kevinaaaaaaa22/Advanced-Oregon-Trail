//A helper class used for quick generation of Swing features

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class SwingHelpers {

    public static final int columns = 40;

    //Generates a standard text area
    public static JTextArea textArea(String str){
        JTextArea text = new JTextArea(str, str.length()/columns, columns);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        Font font = new Font("Gill Sans", 0, 14);
        text.setFont(font);
        return text;
    }

    //Generates a standard frame
    public static JFrame getFrame(String title){
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Main.WIDTH, Main.HEIGHT);
        return frame;
    }

    //Generates a standard image label
    public static JLabel image(File img) throws IOException {
        BufferedImage pic = ImageIO.read(img);
        JLabel label = new JLabel(new ImageIcon(pic));
        return label;
    }

    //Generates a standard button
    public static JButton frameSwitch(String txt, JFrame f1, JFrame f2){
        JButton button = new JButton(txt);
        class Listener implements ActionListener {
            public void actionPerformed(ActionEvent e){
                f1.dispose();
                f2.setVisible(true);
            }
        }
        button.addActionListener(new Listener());
        button.setPreferredSize(new Dimension(80, 40));
        return button;
    }
    public static JButton frameSwitch(String txt, JFrame f1, JFrame f2, int width){
        JButton button = new JButton(txt);
        class Listener implements ActionListener {
            public void actionPerformed(ActionEvent e){
                f1.dispose();
                f2.setVisible(true);
            }
        }
        button.addActionListener(new Listener());
        button.setPreferredSize(new Dimension(width, 40));
        return button;
    }

    //Generates a button used for selecting classes
    public static JButton classSelectionButton(String txt, JFrame f1, JFrame f2, String classSelection, OregonTrail game){
        JButton button = new JButton(txt);
        class Listener implements ActionListener {
            public void actionPerformed(ActionEvent e){
                game.changeClass(classSelection);
                f1.setVisible(false);
                f2.setVisible(true);
            }
        }
        button.addActionListener(new Listener());
        button.setPreferredSize(new Dimension(400, 40));
        return button;
    }

}







