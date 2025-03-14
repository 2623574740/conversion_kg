package org.kg.conversion.music;

import org.slf4j.Logger;
import org.kg.conversion.FileAttribute;
import org.kg.conversion.tools.FileType;
import org.kg.conversion.tools.PublicTools;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
// 音乐转换
public class ConversionMusic extends FileAttribute {
    public ArrayList<File> fileList;
    private static final Logger logger = LoggerFactory.getLogger(ConversionMusic.class);
    public ConversionMusic() {
        super(null, null);
    }

    public ConversionMusic(String output, String input) {
        super(output, input);
    }

    // 开始转换
    public void beginConversionMusic(List<File> fileList) {
        if (fileList == null) {
            fileList = PublicTools.getMusicFiles(this.getInput());
        }
        for (File musicFile : fileList) {
            musicFile.setReadable(true);
            doConversion(musicFile);
        }
    }

    // 转换
    private void doConversion(File file) {
        callExeFile(file);
    }

    private static void callExeFile(File file) {
        String exeFileName = "kgm_decoder.exe"; // 替换为你的 .exe 文件名
        try {
            File exeFile = extractExeFromResources("/win/" + exeFileName);
            // 2. 构建命令
            List<String> command = new ArrayList<>();
            command.add("cmd.exe");
            command.add("/c"); // /c 参数表示执行完命令后关闭 CMD
            command.add(exeFile.getAbsolutePath()); // .exe 文件的绝对路径
            command.add("-k"); // 第一个参数
            command.add("\"" + file.getAbsolutePath() + "\""); // 第二个参数
            // 3. 创建 ProcessBuilder 并启动进程
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true); // 将错误流合并到标准输出流
            Process process = processBuilder.start();
            // 4. 读取命令输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info(line); // 打印命令输出
            }
            // 5. 等待命令执行完成
            int exitCode = process.waitFor();
            logger.info("命令执行完毕，退出码: " + exitCode);
            // 6. 清理临时文件（可选）
            exeFile.deleteOnExit(); // 程序退出时删除临时文件
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从 resources 目录提取 .exe 文件到临时目录
     *
     * @param resourcePath 资源路径（例如：/win/your-program.exe）
     * @return 提取后的 .exe 文件
     * @throws IOException 如果提取失败
     */
    private static File extractExeFromResources(String resourcePath) throws IOException {
        // 获取资源流
        InputStream inputStream = ConversionMusic.class.getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IOException("未找到资源文件: " + resourcePath);
        }

        // 创建临时文件
        File tempFile = File.createTempFile("temp-", ".exe");
        tempFile.deleteOnExit(); // 程序退出时删除临时文件

        // 将资源文件复制到临时文件
        Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return tempFile;
    }

    public void loadFileList() {
        File selectedFolder = new File(getInput());
        List<File> files = Arrays.asList(selectedFolder.listFiles());
        this.fileList = files.stream()
                .filter(file -> file.isFile() && file.getName().endsWith(FileType.kgm.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void moveMp3FileToTargetDocument() {
        File selectedFolder = new File(getInput());
        List<File> files = Arrays.asList(selectedFolder.listFiles());
        List<File> fileList = files.stream()
                .filter(file -> file.isFile() && file.getName().endsWith(FileType.mp3.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));
        // 移动mp3文件到指定目录下
        fileList.stream().forEach(file -> {
            try {
                File targetFile = new File(getOutput(), file.getName());
                Files.move(file.getAbsoluteFile().toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
