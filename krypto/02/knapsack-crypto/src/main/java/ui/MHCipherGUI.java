package ui;

import algorithm.MHCipher;
import algorithm.SimpleKeyGenerator;
import exception.MessageException;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MHCipherGUI extends Applet implements ActionListener {
    private JTextField messageField, cipherField, decryptField, multiplierField, modulusField;
    private JTextField[] privateKeyField, publicKeyField;
    private JButton generatePublicKeyButton, encryptButton, decryptButton;

    private MHCipher cipher;

    public void init() {
        setLayout(new GridLayout(22, 1));

        initMessageEncryptAndDecrypt();
        separate();
        initCipher();
        initPrivateKey();
        initMultiplierAndModulus();
        initPublicKey();
    }

    private void separate() {
        add(new Label());
        Panel panel = new Panel();
        panel.setLayout(new FlowLayout());
        panel.add(new Label("KEY SETTINGS"));
        add(panel);
        add(new JSeparator());
    }

    private void initMessageEncryptAndDecrypt() {
        add(new Label());
        add(messageField = new JTextField());
        messageField.setText("Your message");

        add(new Label(""));
        add(encryptButton = new JButton("Encrypt"));
        add(new Label(""));
        encryptButton.addActionListener(this);
        add(cipherField = new JTextField(""));

        add(new Label(""));
        add(decryptButton = new JButton("Decrypt"));
        add(new Label(""));
        decryptButton.addActionListener(this);
        add(decryptField = new JTextField(""));
    }

    private void initPublicKey() {
        add(new JLabel(""));
        add(generatePublicKeyButton = new JButton("Generate public key"));
        generatePublicKeyButton.addActionListener(this);
        add(new Label("Public Key"));
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(1, 8));
        publicKeyField = new JTextField[8];
        long[] publicKey = cipher.getPublicKey();
        for (int i = 0; i < 8; i++) {
            panel.add(publicKeyField[i] = new JTextField());
            publicKeyField[i].setText(String.valueOf(publicKey[i]));
        }
        add(panel);
    }

    private void initMultiplierAndModulus() {
        Panel panel = new Panel();
        Panel labels = new Panel();

        panel.setLayout(new GridLayout(1, 2));
        labels.setLayout(new GridLayout(1, 2));

        labels.add(new Label("Multiplier"));
        labels.add(new Label("Modulus"));

        JTextField jTextField = new JTextField();
        jTextField.setDocument(new JTextFieldLimit(6));
        jTextField.setText(String.valueOf(cipher.getKeyGen().getMultiplier()));
        panel.add(multiplierField = jTextField);

        jTextField = new JTextField();
        jTextField.setDocument(new JTextFieldLimit(6));
        jTextField.setText(String.valueOf(cipher.getKeyGen().getModulus()));
        panel.add(modulusField = jTextField);

        add(labels);
        add(panel);
    }

    private void initPrivateKey() {
        privateKeyField = new JTextField[8];
        add(new Label("Private Key"));

        Panel panel = new Panel();
        panel.setLayout(new GridLayout(1, 8));
        long[] privateKey = cipher.getPrivateKey();
        for (int i = 0; i < 8; i++) {
            panel.add(privateKeyField[i] = new JTextField());
            privateKeyField[i].setDocument(new JTextFieldLimit(6));
            privateKeyField[i].setText(String.valueOf(privateKey[i]));
        }
        add(panel);
    }

    private void initCipher() {
        SimpleKeyGenerator simpleKeyGenerator = new SimpleKeyGenerator();
        cipher = new MHCipher(simpleKeyGenerator);
    }

    private void decrypt() {
        String msg = cipherField.getText();
        String[] splitMsg = msg.split(" ");
        int msgLength = splitMsg.length;
        long[] encode = new long[msgLength];
        for (int i = 0; i < msgLength; i++) {
            encode[i] = Long.parseLong(splitMsg[i]);
        }

        String decrypt = cipher.decrypt(encode);
        decryptField.setText(decrypt);
    }

    private void encrypt() throws MessageException {
        long[] encrypt = cipher.encrypt(messageField.getText());
        StringBuilder stringBuffer = new StringBuilder();
        for (long element : encrypt) {
            stringBuffer
                    .append(element)
                    .append(" ");
        }
        cipherField.setText(stringBuffer.toString());
    }

    private void updateCipher() {
        updatePrivateKey();
        updateMultiplierAndModulus();
        updatePublicKey();
    }

    private void updatePrivateKey() {
        long[] newPrivateKey = new long[8];
        for (int i = 0; i < privateKeyField.length; i++) {
            newPrivateKey[i] = Long.parseLong(privateKeyField[i].getText());
        }
        cipher.setPrivateKey(newPrivateKey);
    }

    private void updateMultiplierAndModulus() {
        cipher.getKeyGen().setMultiplier(Long.parseLong(multiplierField.getText()));
        cipher.getKeyGen().setModulus(Long.parseLong(modulusField.getText()));
    }

    private void updatePublicKey() {
        cipher.generatePublicKey();
        long[] publicKey = cipher.getPublicKey();

        for (int i = 0; i < publicKeyField.length; i++) {
            publicKeyField[i].setText(String.valueOf(publicKey[i]));
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == generatePublicKeyButton)
            updateCipher();
        else if (actionEvent.getSource() == encryptButton)
            try {
                encrypt();
            } catch (MessageException e) {
                messageField.setText("Your message");
            }
        else if (actionEvent.getSource() == decryptButton)
            decrypt();
    }
}
