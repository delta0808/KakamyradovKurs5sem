package dialogs;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.URL;

public class DialogComponentСreator {

    public static JButton addButton(JPanel panel, int w, int h, int x, int y, String title){
        final JButton myButton = new JButton(title);
        myButton.setSize(w, h);
        myButton.setLocation(x,y);
        myButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }
            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    myButton.doClick();
                }
            }
        });
        panel.add(myButton);

        return myButton;
    }


    public static JTextField addTextField(JPanel panel, int w, int h, int x, int y){
        JTextField myTextField = new JTextField("");
        myTextField.setSize(w, h);
        myTextField.setLocation(x,y);
        panel.add(myTextField);
        return myTextField;
    }

    public static JPasswordField addPasswordField(JPanel panel, int w, int h, int x, int y){
        JPasswordField myPasswordField = new JPasswordField("");
        myPasswordField.setSize(w, h);
        myPasswordField.setLocation(x,y);
        panel.add(myPasswordField);
        return myPasswordField;
    }

    public static JLabel addLabel(JPanel panel, int w, int h, int x, int y, String title){
        JLabel myLabel = new JLabel(title);
        myLabel.setSize(w, h);
        myLabel.setLocation(x,y);
        panel.add(myLabel);
        return myLabel;
    }

    public static JLabel addPhoto(JPanel panel, int w, int h, int x, int y, String title){
        JLabel myLabel = new JLabel();
        myLabel.setSize(w, h);
        myLabel.setLocation(x,y);
        setIcon(myLabel, title);
        panel.add(myLabel);
        return myLabel;
    }

    public static URL setIcon(JLabel label, String title) {
        try {
            URL url = DialogComponentСreator.class.getClassLoader().getResource("photo/" + title);
            BufferedImage image = ImageIO.read(url);
            BufferedImage newImage = new BufferedImage(label.getWidth(), label.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = newImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(image, 0, 0, label.getWidth(), label.getHeight(), null);
            g.dispose();
            ImageIcon icon = new ImageIcon(newImage);
            label.setIcon(icon);
            return url;
        } catch (Exception ex){
            label.setText("Не найдено!");
            return  DialogComponentСreator.class.getClassLoader().getResource("photo/" + title);
        }
    }

    public static JSeparator addSeparator(JPanel panel, int w, int h, int x, int y){
        JSeparator mySeparator = new JSeparator();
        mySeparator.setSize(w, h);
        mySeparator.setLocation(x,y);
        mySeparator.setBackground(Color.darkGray);
        panel.add(mySeparator);
        return mySeparator;
    }

    public static JCheckBox addCheckBox(JPanel panel, int w, int h, int x, int y, String title, boolean slected){
        JCheckBox myCheckBox = new JCheckBox(title, slected);
        myCheckBox.setSize(w, h);
        myCheckBox.setLocation(x,y);
        panel.add(myCheckBox);
        return myCheckBox;
    }

    public static JTextArea addTextArea(JPanel panel, int w, int h, int x, int y){
        JTextArea myTextArea = new JTextArea("");
        myTextArea.setSize(w, h);
        myTextArea.setLocation(x,y);
        panel.add(myTextArea);
        return myTextArea;
    }


    public static JRadioButton[] addRadioButtonGroupe(JPanel panel, int w, int h, int x, int y, String[] titles){
        JRadioButton[] myRadioButton = new JRadioButton[titles.length];
        ButtonGroup group = new ButtonGroup();
        boolean checked = true;

        for (int i = 0; i < titles.length; i++) {
            myRadioButton[i] = new JRadioButton(titles[i], checked);
            myRadioButton[i].setSize(w, h);
            myRadioButton[i].setLocation(x, y + i*30);

            group.add(myRadioButton[i]);
            panel.add(myRadioButton[i]);
            checked = false;
        }

        return myRadioButton;
    }


    public static JComboBox addComboBox(JPanel panel, boolean isEditable, int w, int h, int x, int y){
        JComboBox myComboBox = new JComboBox();
        myComboBox.setEditable(isEditable);
        myComboBox.setSize(w, h);
        myComboBox.setLocation(x,y);
        panel.add(myComboBox);
        return myComboBox;
    }

    public static JTable addTable(JPanel panel, int w, int h, int x, int y, AbstractTableModel model){

        JTable myTable = new JTable(model);
        myTable.setSize(w, h);
        myTable.setLocation(x,y);

        JScrollPane scrollPane = new JScrollPane(myTable);
        scrollPane.setSize(w, h);
        scrollPane.setLocation(x,y);

        panel.add(scrollPane);
        return myTable;
    }
}
