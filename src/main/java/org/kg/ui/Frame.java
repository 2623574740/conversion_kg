package org.kg.ui;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class Frame {
    private final JFrame frame;
    public Frame(){
        this.frame = new JFrame("转换工具");
        this.frame.setResizable(false);//限制调整窗口大小
        this.frame.setSize(new Dimension(600,500));
        this.frame.setIconImage(new ImageIcon(getClass().getResource("/img/conversion.png")).getImage());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JFrame getFrame() {
        return frame;
    }
}
