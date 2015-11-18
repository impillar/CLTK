package edu.ntu.cltk.crypto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CryptoUI extends JFrame {
    private JTextArea textArea;
    private JTextArea resultArea;
    private ButtonGroup bg;
    private JRadioButton decodeRadioButton;
    private JRadioButton encodeRadioButton;

    public CryptoUI() {

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{1};
        gridBagLayout.rowHeights = new int[]{0, 0};
        gridBagLayout.columnWeights = new double[]{1.0};
        gridBagLayout.rowWeights = new double[]{0.5, 0.5};
        getContentPane().setLayout(gridBagLayout);

        JPanel textAreaPanel = new JPanel();
        GridBagConstraints gbc_textAreaPanel = new GridBagConstraints();
        gbc_textAreaPanel.fill = GridBagConstraints.BOTH;
        gbc_textAreaPanel.insets = new Insets(0, 0, 5, 0);
        gbc_textAreaPanel.gridx = 0;
        gbc_textAreaPanel.gridy = 0;
        getContentPane().add(textAreaPanel, gbc_textAreaPanel);
        textAreaPanel.setLayout(new BorderLayout(0, 0));

        textArea = new JTextArea();
        textAreaPanel.add(textArea);
        GridBagConstraints gbc_textArea = new GridBagConstraints();
        gbc_textArea.insets = new Insets(0, 0, 5, 0);
        gbc_textArea.gridx = 0;
        gbc_textArea.gridy = 0;


        JPanel resultPanel = new JPanel();
        GridBagConstraints gbc_resultPanel = new GridBagConstraints();
        gbc_resultPanel.insets = new Insets(0, 0, 5, 0);
        gbc_resultPanel.fill = GridBagConstraints.BOTH;
        gbc_resultPanel.gridx = 0;
        gbc_resultPanel.gridy = 1;
        getContentPane().add(resultPanel, gbc_resultPanel);

        GridBagLayout gbl_resultPanel = new GridBagLayout();
        gbl_resultPanel.columnWidths = new int[]{1, 1};
        gbl_resultPanel.rowHeights = new int[]{1, 1};
        gbl_resultPanel.columnWeights = new double[]{0.2, 0.8};
        gbl_resultPanel.rowWeights = new double[]{1.0, 1.0};
        resultPanel.setLayout(gbl_resultPanel);

        JPanel menuPanel = new JPanel();
        GridBagConstraints gbc_menuPanel = new GridBagConstraints();
        gbc_menuPanel.gridheight = 2;
        gbc_menuPanel.insets = new Insets(0, 0, 5, 0);
        gbc_menuPanel.fill = GridBagConstraints.BOTH;
        gbc_menuPanel.gridx = 0;
        gbc_menuPanel.gridy = 0;
        resultPanel.add(menuPanel, gbc_menuPanel);
        menuPanel.setLayout(new GridLayout(0, 1, 0, 0));

        decodeRadioButton = new JRadioButton("Base64 Decode");
        decodeRadioButton.setSelected(true);
        menuPanel.add(decodeRadioButton);
        //decodeRadioButton.addActionListener(new RadioActionListener());

        encodeRadioButton = new JRadioButton("Base64 Encode");
        menuPanel.add(encodeRadioButton);
        //encodeRadioButton.addActionListener(new RadioActionListener());

        bg = new ButtonGroup();
        bg.add(decodeRadioButton);
        bg.add(encodeRadioButton);

        JPanel panel_2 = new JPanel();
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.gridheight = 2;
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 1;
        gbc_panel_2.gridy = 0;
        resultPanel.add(panel_2, gbc_panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));

        resultArea = new JTextArea();
        panel_2.add(resultArea);

        JButton pasteButton = new JButton("RUN");
        pasteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //bg.getSelection().
                if (decodeRadioButton.isSelected()) {
                    String str = textArea.getText();
                    String res = CryptoUtil.base64Decoding(str);
                    resultArea.setText(res);
                } else if (encodeRadioButton.isSelected()) {
                    String str = textArea.getText();
                    String res = CryptoUtil.base64Encoding(str, true);
                    resultArea.setText(res);
                }
            }
        });
        panel_2.add(pasteButton, BorderLayout.NORTH);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        CryptoUI cui = new CryptoUI();
        cui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cui.pack();
        cui.setSize(600, 400);
        cui.setVisible(true);
    }

    public class RadioActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub

        }

    }
}
