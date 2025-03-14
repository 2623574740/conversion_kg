package org.kg.conversion.tools;

public enum FileType {
    kgm("kgm"),krc("krc"), mp3("mp3"),lrc("lrc");
    private final String type;
    FileType(String type){
        this.type = type;
    }
    public String getValue() {
        return type;
    }
}
