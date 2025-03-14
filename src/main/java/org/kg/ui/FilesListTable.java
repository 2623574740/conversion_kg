package org.kg.ui;

import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.kg.conversion.tools.FileType;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Data
@Component
public class FilesListTable {
    private final String[] columnNames = {"选择", "文件名", "大小"};
    private JTable jTable;
    private JScrollPane jScrollPane;
    private ArrayList<File> filteFiles;

    private File selectedfolder;
    public FilesListTable() {
        initTable(null); // 初始化表格
        initTableScrollPane(); // 初始化滚动面板
    }

    public JTable getJTable() {
        return this.jTable;
    }

    public JScrollPane getTableScrollPane() {
        return this.jScrollPane;
    }

    public void initTableScrollPane() {
        // 创建滚动面板并设置默认大小
        jScrollPane = new JScrollPane(jTable);
        jScrollPane.setPreferredSize(new Dimension(600, 400)); // 设置滚动面板的默认大小
        jScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    public void loadFileList(File selectedFolder, FileType fileType) {
        if (selectedFolder != null){
            selectedfolder = selectedFolder;
        } else {
            selectedFolder = selectedfolder;
        }
        List<File> files = Arrays.asList(selectedFolder.listFiles());
        filteFiles = files.stream()
                .filter(file -> file.isFile() && file.getName().endsWith(fileType.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));

        Object[][] tableData = new Object[filteFiles.size()][3];
        AtomicInteger j = new AtomicInteger(0);
        filteFiles.stream().forEach(file -> {
            int index = j.get();
            tableData[index][0] = false; // 默认不勾选
            tableData[index][1] = file.getName();
            tableData[index][2] = FileUtils.byteCountToDisplaySize(file.length());
            j.incrementAndGet();
        });
        initTable(tableData);
    }

    public void initTable(Object[][] tableData) {
        DefaultTableModel model = new DefaultTableModel(tableData, this.columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // 允许第一列（勾选框）可勾选
            }
        };

        if (jTable == null)
            jTable = new JTable(model);
        else
            jTable.setModel(model);

        this.setTableColumnWidth(jTable, 0, 40); // 第一列宽度为 40
        this.setTableColumnWidth(jTable, 1, 400); // 第二列宽度为 400
        this.setTableColumnWidth(jTable, 2, 140); // 第三列宽度为 140
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    private void setTableColumnWidth(JTable table, int columnIndex, int columnWidth) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        column.setPreferredWidth(columnWidth); // 设置首选宽度
        column.setMinWidth(columnWidth); // 设置最小宽度
        column.setMaxWidth(columnWidth); // 设置最大宽度
    }

    public List<File> getSelectedFiles() {
        List<File> selectedFiles = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean isChecked = (Boolean) model.getValueAt(i, 0); // 获取勾选框的状态
            if (isChecked != null && isChecked) {
                File file = filteFiles.get(i);
                selectedFiles.add(file);
            }
        }
        return selectedFiles;
    }
}