package org.kg.ui;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.JLabel;
@Getter
@Component
public class TextLabel {
    private final JLabel jLabel;
    public TextLabel(String str){
        this.jLabel = new JLabel();
        jLabel.setText(str);
    }
}
