package org.kg.ui;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 主界面
 */
public class MainPanel {
    /**
     * 主界面
     *
     * @return {@link JPanel} 主界面
     */
    public static JPanel initPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 使用 BorderLayout
        Border lineBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1); // 创建
        Border paddingBorder = new EmptyBorder(2, 2, 2, 2); // 创建内边距（上、左、下、右）
        Border compoundBorder = new CompoundBorder(lineBorder, paddingBorder); // 组合边框和内边距
        panel.setBorder(compoundBorder);
        return panel;
    }
}
