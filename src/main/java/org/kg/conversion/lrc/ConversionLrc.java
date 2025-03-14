package org.kg.conversion.lrc;

import org.kg.conversion.FileAttribute;
import org.kg.conversion.tools.FileType;
import org.kg.conversion.tools.PublicTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.Inflater;

@Component
public class ConversionLrc extends FileAttribute {
    private static final char key[] = {'@', 'G', 'a', 'w', '^', '2', 't', 'G', 'Q', '6', '1', '-', '\316', '\322', 'n', 'i'};
    private static final Logger logger = LoggerFactory.getLogger(ConversionLrc.class);
    public ArrayList<File> fileList;

    public ConversionLrc(String output, String input) {
        super(output, input);
    }

    private void beginConversion(List<File> fileList) throws Exception {
        if (fileList == null) {
            fileList = PublicTools.getKrcFiles(this.getInput());
        }
        for (File krcFile : fileList) {
            krcFile.setReadable(true);
            doConversion(krcFile);
        }
    }

    private void doConversion(File file) throws Exception {
        String fileName = file.getAbsolutePath();
        RandomAccessFile raf = new RandomAccessFile(fileName, "r");
        byte[] content = new byte[(int) (raf.length() - 4)];
        raf.skipBytes(4);
        raf.read(content);
        raf.close();

        for (int i = 0, length = content.length; i < length; i++) {
            int j = i % 16; // 循环异或解密
            content[i] ^= key[j];
        }

        String lrc = null;

        lrc = new String(decompress(content), "utf-8"); // 解压为 utf8

        String final_lrc = lrc.replaceAll("<([^>]*)>", "").replaceAll(",([^]]*)]", "] ");
        /* 处理时间标签 */
        Pattern p = Pattern.compile("\\[\\d+?\\]");
        Matcher m = p.matcher(final_lrc);
        while (m.find()) {
            final_lrc = m.replaceFirst(toTime(m.group()));
            m = p.matcher(final_lrc);
        }
        String krcName = Paths.get(file.getAbsolutePath()).getFileName().toString();
        File outLrc = new File(getOutput(),krcName.substring(0,krcName.lastIndexOf("."))+".lrc");
        String lrcFileName =  outLrc.getAbsolutePath();
        FileOutputStream fos = new FileOutputStream(lrcFileName);
        byte[] lrcbyte = final_lrc.getBytes();
        fos.write(lrcbyte);
        fos.close();
        logger.info("文件保存为：" + lrcFileName);
    }

    public void moveLrcFileToTargetDocument() {
        File selectedFolder = new File(getInput());
        List<File> files = Arrays.asList(selectedFolder.listFiles());
        List<File> fileList = files.stream()
                .filter(file -> file.isFile() && file.getName().endsWith(FileType.lrc.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));
        fileList.stream().forEach(file -> {
            try {
                File targetFile = new File(getOutput(), file.getName());
                Files.move(file.getAbsoluteFile().toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void loadFileList() {
        File selectedFolder = new File(getInput());
        List<File> files = Arrays.asList(selectedFolder.listFiles());
        this.fileList = files.stream()
                .filter(file -> file.isFile() && file.getName().endsWith(FileType.krc.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void beginConversionLrc(List<File> fileList) throws Exception {
        if (fileList == null) {
            fileList = PublicTools.getKrcFiles(this.getInput());
        }
        for (File krcFile : fileList) {
            krcFile.setReadable(true);
            doConversion(krcFile);
        }
    }
    private String toTime(String num) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        long time = Long.parseLong(num.substring(1, num.length() - 1));
        return "[" + sdf.format(time) + "." + ((time % 1000) / 10) + "]";
    }
    private byte[] decompress(byte[] data) throws Exception {

        byte[] output = new byte[0];
        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data);
        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        byte[] buf = new byte[1024];
        while (!decompresser.finished()) {
            int i = decompresser.inflate(buf);
            o.write(buf, 0, i);
        }
        output = o.toByteArray();
        o.close();
        decompresser.end();
        return output;
    }
}