package org.kg;

import org.kg.conversion.lrc.ConversionLrc;
import org.kg.conversion.music.ConversionMusic;
import org.kg.ui.BeanConfig;
import org.kg.ui.Layout;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"org.kg"})
public class Main {
    public static void main(String[] args) throws Exception {
        if ( args[0].equals("-ui")){
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
            Layout layout = context.getBean(Layout.class);
            layout.run();
        } else {
            String fileType = args[0];
            // 输入文件夹
            String input = args[1];
            // 输出文件夹
            String output = args[2];
            if ( fileType.equals("-lrc") ){
                ConversionLrc conversionLrc = new ConversionLrc(output, input);
                conversionLrc.loadFileList();
                conversionLrc.beginConversionLrc(conversionLrc.fileList);
                conversionLrc.moveLrcFileToTargetDocument();
            } else if (fileType.equals("-music")){
                ConversionMusic conversionMusic = new ConversionMusic(output,input);
                conversionMusic.loadFileList();
                conversionMusic.beginConversionMusic(conversionMusic.fileList);
                conversionMusic.moveMp3FileToTargetDocument();
            }
        }
    }
}