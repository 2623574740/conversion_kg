# 项目介绍
conversion_kg采用2种模式：命令行，UI画面；实现以下2种格式文件转换为普通播放器可读格式文件。

- 转换kgm格式文件转mp3
- 转换krc格式文件转lrc

# 使用说明

打开CMD（Windows系统）按需求选择如下命令并修改参数输入

使用UI界面操作
```shell
java -jar .\conversion_kg-1.0.jar -ui
```

使用命令行转码操作
```shell
# 转码krc文件 第一个参数是源文件夹，第二个参数是输出文件夹
java -jar .\conversion_kg-1.0.jar -lrc "D:\KuGou\Lyric" "D:\mp3"

# 转码kgm文件，参数同上
java -jar .\conversion_kg-1.0.jar -music "D:\KuGou\KugouMusic" "D:\mp3"
```

> :warning:
> 
> 使用命令行操作会把目标文件夹下所有的kgm/krc文件转码，并输出到目标文件夹下

> tips
> 
> 目前在mac上使用界面上的krc单选按钮看不到，这个bug我之后找时间修改