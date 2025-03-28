package com.oneitthing.wda.view.information;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class JTextAreaStream extends OutputStream {

    private JTextArea _area;
    private ByteArrayOutputStream _buf;

    public JTextAreaStream(JTextArea area) {
        _area = area;
        _buf = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this, true));    // true は AutoFlush の設定
    }
    
    @Override
    public void write(int b) throws IOException {
        _buf.write(b);
    }
    
    @Override
    public void flush() throws IOException {

        // Swing のイベントスレッドにのせる
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                _area.append(_buf.toString());
                _buf.reset();
            }
        });
    }

    public static void main(String[] args) {
        JTextArea area = new JTextArea();
        area.setEditable(false);  // ReadOnly に
        JTextAreaStream stream = new JTextAreaStream(area);
        System.setOut(new PrintStream(stream, true));    // true は AutoFlush の設定

        JFrame frame = new JFrame();
        frame.getContentPane().add(area);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(50,100);
        frame.setVisible(true);

        System.out.println("あああ");
        System.out.println("いいい");
        System.out.println("ううう");
        System.out.println("ううう");
        System.out.println("ううう");
        System.out.println("ううう");
        System.out.println("ううう");
        System.out.println("ううう");
        System.out.println("ううう");
    }
}