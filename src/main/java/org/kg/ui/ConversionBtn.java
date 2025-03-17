package org.kg.ui;

import org.kg.conversion.lrc.ConversionLrc;
import org.kg.conversion.music.ConversionMusic;
import org.kg.conversion.tools.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

/**
 * 转换按钮
 * */
@Component
public class ConversionBtn {
    private final JButton button;
    @Autowired
    private FilesListTable filesListTable;
    @Autowired
    private ConversionMusic conversionMusic;
    @Autowired
    private ConversionLrc conversionLrc;
    @Autowired
    @Qualifier("outputFolderPathField")
    private FolderPathField outputFolderPathField;
    @Autowired
    @Qualifier("inputFolderPathField")
    private FolderPathField inputFolderPathField;
    @Autowired
    private ChooseFileTypeBtn chooseFileTypeBtn;
    private final ActionListener actionListener = e -> {
        //调用转码方法，获取勾选的内容
        List<File> selectedFile = filesListTable.getSelectedFiles();
        FileType fileType = chooseFileTypeBtn.getFileType();
        if ( fileType == FileType.kgm){
            conversionMusic.setInput(inputFolderPathField.getJTextField().getText());
            conversionMusic.setOutput(outputFolderPathField.getJTextField().getText());
            conversionMusic.beginConversionMusic(selectedFile);
            conversionMusic.moveMp3FileToTargetDocument();
        }else {
            conversionLrc.setInput(inputFolderPathField.getJTextField().getText());
            conversionLrc.setOutput(outputFolderPathField.getJTextField().getText());
            try {
                conversionLrc.beginConversionLrc(selectedFile);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            conversionLrc.moveLrcFileToTargetDocument();
        }

        JOptionPane.showMessageDialog(null, "转码完成", "提示", JOptionPane.INFORMATION_MESSAGE);
        filesListTable.loadFileList(null,chooseFileTypeBtn.getFileType());
    };
    public ConversionBtn(){
        this.button = new JButton("转码");
        button.addActionListener(actionListener);
    }

    public JButton getButton() {
        return this.button;
    }
}
