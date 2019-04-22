package com.mxkj.econtrol.bean;

import com.mxkj.econtrol.base.BaseEntity;

/**
 * Created by ${  chenjun  } on 2018/12/17.
 */

public class FileBean {

    public String filePath;
    public String fileName;
    public String fileSize;
    public int fileType = 0; // 文件类型 1图片 2音频 3 视频
    public boolean isClick= false;


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
