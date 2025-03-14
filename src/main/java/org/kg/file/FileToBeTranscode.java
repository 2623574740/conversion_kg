package org.kg.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
/**
 * 待转码文件
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileToBeTranscode {
    private boolean choose;
    private File file;
    private Integer size;
}
