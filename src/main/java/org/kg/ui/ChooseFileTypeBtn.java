package org.kg.ui;

import lombok.Getter;
import org.kg.conversion.tools.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class ChooseFileTypeBtn {
    private ButtonGroup buttonGroup;
    @Autowired
    private ChooseDocumentBtn chooseInputDocumentBtn;
    @Autowired
    private FilesListTable filesListTable;
    private FileType fileType = FileType.kgm;
    private final List<JRadioButton> radioButtons = new ArrayList<>();
    public ChooseFileTypeBtn(){
        this.createButtonGroup();
    }
    private final ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton selectedButton = (JRadioButton) e.getSource();
            fileType = FileType.valueOf(selectedButton.getText());
            if (chooseInputDocumentBtn.getSelectedFolder() != null){
                filesListTable.loadFileList(chooseInputDocumentBtn.getSelectedFolder(),fileType);
            }
        }
    };
    private void createButtonGroup(){

        JRadioButton kgmBtn = new JRadioButton(FileType.kgm.getValue());
        JRadioButton krcBtn = new JRadioButton(FileType.krc.getValue());
        kgmBtn.setSelected(true);

        // 添加监听事件
        kgmBtn.addActionListener(listener);
        krcBtn.addActionListener(listener);
        this.buttonGroup = new ButtonGroup();
        buttonGroup.add(kgmBtn);
        buttonGroup.add(krcBtn);

        this.radioButtons.add(kgmBtn);
        this.radioButtons.add(krcBtn);
    }
}
