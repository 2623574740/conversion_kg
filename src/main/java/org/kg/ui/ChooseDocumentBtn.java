package org.kg.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@Component
public class ChooseDocumentBtn {
    private File selectedFolder;
    private JButton button;
    @Autowired
    private Frame frame;

    @Autowired
    @Qualifier("inputFolderPathField")
    private FolderPathField inputFolderPathField;
    @Autowired
    @Qualifier("outputFolderPathField")
    private FolderPathField outputFolderPathField;
    @Autowired
    private FilesListTable filesListTable;
    @Autowired
    private ChooseFileTypeBtn chooseFileTypeBtn;
    /**
     * 构造方法
     * */
    public ChooseDocumentBtn(String flag) {
        this.initButton(flag);
    }

    public JButton getButton() {
        return this.button;
    }

    /**
     * 选择文件夹按钮
     *
     * @return {@link JButton} 按钮
     */
    private void initButton(String flag) {
        // 创建一个按钮
        JButton button = new JButton("...");
        // 为按钮添加事件监听器
        if (flag.equals("input"))
            button.addActionListener(inputActionListener);
        else
            button.addActionListener(outputActionListener);
        this.button = button;
    }
    ActionListener inputActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 创建 JFileChooser 实例，并设置为只能选择文件夹
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            // 弹出文件夹选择对话框
            int result = fileChooser.showOpenDialog(frame.getFrame());
            if (result == JFileChooser.APPROVE_OPTION) {
                // 更新类的成员变量
                selectedFolder = fileChooser.getSelectedFile();
                inputFolderPathField.getJTextField().setText(selectedFolder.getAbsolutePath()); // 显示文件夹路径
                filesListTable.loadFileList(selectedFolder,chooseFileTypeBtn.getFileType());
            }
        }
    };
    ActionListener outputActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 创建 JFileChooser 实例，并设置为只能选择文件夹
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            // 弹出文件夹选择对话框
            int result = fileChooser.showOpenDialog(frame.getFrame());
            if (result == JFileChooser.APPROVE_OPTION) {
                // 更新类的成员变量
                selectedFolder = fileChooser.getSelectedFile();
                outputFolderPathField.getJTextField().setText(selectedFolder.getAbsolutePath()); // 显示文件夹路径
            }
        }
    };
    /**
     * 获取用户选择的文件夹
     *
     * @return 用户选择的文件夹路径，如果没有选择则返回null
     */
    public File getSelectedFolder() {
        return selectedFolder;
    }
}