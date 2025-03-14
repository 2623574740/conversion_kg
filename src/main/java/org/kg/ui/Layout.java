package org.kg.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class Layout {
    @Autowired
    private Frame frame;
    @Autowired
    @Qualifier("inputFolderPathField")
    private FolderPathField inputFolderPathField;
    @Autowired
    @Qualifier("outputFolderPathField")
    private FolderPathField outputFolderPathField;
    @Autowired
    private ChooseFileTypeBtn chooseFileTypeBtn;
    @Autowired
    private ChooseDocumentBtn chooseInputDocumentBtn;
    @Autowired
    private ChooseDocumentBtn chooseOutputDocumentBtn;
    @Autowired
    private ConversionBtn conversionBtn;
    @Autowired
    private TextLabel inputLabel;
    @Autowired
    private TextLabel outputLabel;
    @Autowired
    private FilesListTable filesListTable;

    public void run() {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            JFrame f = frame.getFrame();
            JPanel p_top = new JPanel();
            p_top.setLayout(new BorderLayout());
            // 初始化顶部面板
            JPanel p_top_0 = MainPanel.initPanel();
            JPanel p_top_1 = MainPanel.initPanel();
            p_top_0.add(inputLabel.getJLabel());
            p_top_0.add(inputFolderPathField.getJTextField());
            p_top_0.add(chooseInputDocumentBtn.getButton());
            chooseFileTypeBtn.getRadioButtons().forEach(e -> p_top_0.add(e));
            p_top_1.add(outputLabel.getJLabel());
            p_top_1.add(outputFolderPathField.getJTextField());
            p_top_1.add(chooseOutputDocumentBtn.getButton());
            p_top.add(p_top_0,BorderLayout.NORTH);
            p_top.add(p_top_1,BorderLayout.CENTER);
            // 初始化中间面板
            JPanel p_mid = MainPanel.initPanel();
            p_mid.setLayout(new BorderLayout()); // 设置中间面板为 BorderLayout
            p_mid.add(filesListTable.getTableScrollPane(), BorderLayout.CENTER); // 添加滚动面板

            // 初始化底部面板
            JPanel p_bottom = MainPanel.initPanel();
            p_bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
            p_bottom.add(conversionBtn.getButton());

            // 设置主窗口布局
            f.setLayout(new BorderLayout());
            f.add(p_top, BorderLayout.NORTH);
            f.add(p_mid, BorderLayout.CENTER);
            f.add(p_bottom, BorderLayout.SOUTH);

            // 显示窗口
            f.setVisible(true);
        });

    }
}