package org.kg.ui;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.JTextField;
/**
 * 文本框，用于显示选择的文件夹路径
 */
@Getter
@Component
public class FolderPathField {
    //文本框，用于显示选择的文件夹路径
    private final JTextField jTextField;
    public FolderPathField(){
        this.jTextField = new JTextField(30);
        jTextField.setEditable(false); // 禁止输入框输入
    }
}
