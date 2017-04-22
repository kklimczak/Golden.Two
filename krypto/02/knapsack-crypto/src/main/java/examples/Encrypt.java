package examples;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Encrypt extends Applet implements ActionListener {
    private JTextField plaintext, encode, decode, multiplier, modulus;
    private JTextField[] privatekey, publickey;
    private JButton record, encodebutton, decodebutton;

    public void init() {
        privatekey = new JTextField[8];
        setLayout(new GridLayout(14,1,10,10));
        add(new Label("1. Enter Private Key"));

        Panel p=new Panel();
        p.setLayout(new GridLayout(1,8));
        int x=1;
        for (int i=0 ; i < 8 ; i++) {
            p.add(privatekey[i]=new JTextField(String.valueOf(x)));
            x *= 2;
        }
        add(p);
        add(new Label("2. Enter Multiplier and Modulus and Press Record"));
        Panel q=new Panel();
        q.setLayout(new FlowLayout());
        q.add (new Label ("Multiplier"));
        q.add (multiplier = new JTextField("4571"));
        q.add (new Label ("Modulus"));
        q.add (modulus = new JTextField("7187"));
        q.add (new Label (""));
        q.add (record = new JButton("Record"));
        record.addActionListener(this);
        add(q);
        add (new Label("Public Key"));
        Panel z = new Panel();
        z.setLayout(new GridLayout(1,8));
        publickey = new JTextField[8];
        for (int i=0 ; i < 8 ; i++)
            z.add(publickey[i] = new JTextField(""));
        add(z);
        add(new Label("3. Enter Text Message"));
        add(plaintext=new JTextField(""));
        Panel r = new Panel();
        r.setLayout(new GridLayout(1,4));
        r.add(new Label(""));
        r.add(new Label("4. Press Encrypt"));
        r.add(new Label(""));
        r.add(encodebutton = new JButton("Encrypt"));
        encodebutton.addActionListener(this);
        add(r);
        add(new Label("Encoded Message"));
        add(encode = new JTextField(""));
        Panel t = new Panel();
        t.setLayout(new GridLayout(1,4));
        t.add(new Label(""));
        t.add(new Label("5. Press Decrypt"));
        t.add(new Label(""));
        t.add(decodebutton = new JButton("Decrypt"));
        decodebutton.addActionListener(this);
        add(t);
        add(new Label("Decoded Message"));
        add(decode = new JTextField(""));
    }

    private int getInt(JTextField t)  {  return Integer.parseInt(t.getText());  }

    private void setInt(int i, JTextField t)  {  t.setText(String.valueOf(i));  }

    private int modulo(int i, int j)  {  return i - (i/j)*j;  }

    private void computePublicKey() {
        for (int i=0 ; i < 8 ; i++)
            setInt(modulo(getInt(privatekey[i])*getInt(multiplier),
                    getInt(modulus)), publickey[i]);
    }

    private int translateChar(char x, JTextField[] publicKey) {
        int sum=0;

        for(int i=0; i < 8; i++) {
            if(2*(x/2) != x) sum += getInt(publicKey[i]);
            x /=2;
        }
        return sum;
    }

    private void computeEncodedText() {
        StringBuilder s;
        String m = plaintext.getText();

        int len = m.length();
        if (len <= 0) return;

        s = new StringBuilder(String.valueOf(translateChar(m.charAt(0), publickey)));
        for (int i=1 ; i < len ; i++) {
            s.append("+").append(String.valueOf(translateChar(m.charAt(i), publickey)));
        }
        encode.setText(s.toString());
    }

    private int xlateBack(int x) {
        int m=128;
        int ch=0;
        for (int i=7 ; i >= 0 && x > 0 ; i--) {
            if (getInt(privatekey[i]) <= x) {
                x -= getInt(privatekey[i]);
                ch += m;
            }
            m /= 2;
        }
        return ch;
    }

    private int inverse() {
        int f;
        int a = getInt(multiplier);
        int b = getInt(modulus);
        for (int k=1 ; k < b ; k++) {
            f = 0;
            for (int j=0 ; j < b ; j++)
                if (modulo(k*modulo(a*j,b), b) == j) f += 1; else break;
            if (f == b) return k;
        }
        return 0;
    }

    private void computeDecodeText(String s) {
        StringBuilder str = new StringBuilder();
        int tok;
        int inv = inverse();
        try {
            StringTokenizer t = new StringTokenizer(s, "+");
            while (true) {
                tok = Integer.parseInt(t.nextToken());
                str.append((char) xlateBack(modulo(inv * tok, getInt(modulus))));
            }
        }
        catch (NoSuchElementException e) {}
        decode.setText(str.toString());
    }

    public void actionPerformed (ActionEvent evt) {
        for (int i=0 ; i < 8 ; i++) {
            if (evt.getSource() == privatekey[i]) {
                superIncrease();
                return;
            }
        }

        if (evt.getSource() == record) computePublicKey();
        else if (evt.getSource() == encodebutton) computeEncodedText();
        else if (evt.getSource() == decodebutton)
            computeDecodeText(encode.getText());
    }

    private void superIncrease() {
        int sum = getInt(privatekey[0]);
        for (int i=1 ; i < 8 ; i++) {
            if (sum >= getInt(privatekey[i])) setInt(sum+1, privatekey[i]);
            sum += getInt(privatekey[i]);
        }
        if (getInt(modulus) <= getInt(privatekey[7]))
            setInt(getInt(privatekey[7])+1, modulus);
    }

    public void processEvent (AWTEvent evt) {
        if (((WindowEvent)evt).getNewState() == WindowEvent.WINDOW_CLOSED)
            System.out.println("Yikes");
    }
}