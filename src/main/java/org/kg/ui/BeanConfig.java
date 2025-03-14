package org.kg.ui;

import org.kg.conversion.music.ConversionMusic;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfig {
    @Bean(name = {"chooseFileTypeBtn"})
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON )
    public ChooseFileTypeBtn chooseFileTypeBtn(){
        return new ChooseFileTypeBtn();
    }

    @Bean(name = {"chooseInputDocumentBtn"})
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON )
    public ChooseDocumentBtn chooseInputDocumentBtn(){
        return new ChooseDocumentBtn("input");
    }

    @Bean(name = {"chooseOutputDocumentBtn"})
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON )
    public ChooseDocumentBtn chooseOutputDocumentBtn(){
        return new ChooseDocumentBtn("output");
    }

    @Bean(name = {"conversionBtn"})
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON )
    public ConversionBtn conversionBtn(){
        return new ConversionBtn();
    }

    @Bean(name = {"filesListTable"})
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON )
    public FilesListTable filesListTable(){
        return new FilesListTable();
    }

    @Bean(name = {"inputFolderPathField"})
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON )
    public FolderPathField inputFolderPathField(){
        return new FolderPathField();
    }

    @Bean(name = {"outputFolderPathField"})
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON )
    public FolderPathField outputFolderPathField(){
        return new FolderPathField();
    }

    @Bean(name = {"frame"})
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON )
    public Frame frame(){
        return new Frame();
    }

    @Bean(name = {"outputLabel"})
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public TextLabel outputLabel(){
        return new TextLabel("输出");
    }

    @Bean(name = {"inputLabel"})
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public TextLabel inputLabel(){
        return new TextLabel("输入");
    }

    @Bean(name = {"inputPath"})
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public String inputPath(){
        return "";
    }

    @Bean(name = {"outputPath"})
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public String outputPath(){
        return "";
    }
    @Bean(name = {"layout"})
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Layout layout(){
        return new Layout();
    }

    /*-------------------------------------------------*/
    @Bean(name = {"conversionMusic"})
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ConversionMusic conversionMusic(){
        return new ConversionMusic();
    }
}
