package me.zhengjie.model.vo;

import lombok.Data;

@Data
public class ImageUploadVo {
    private int uploaded;
    private String fileName;
    private String url;

    public ImageUploadVo() {
    }

    public ImageUploadVo(int uploaded, String fileName, String url) {
        this.uploaded = uploaded;
        this.fileName = fileName;
        this.url = url;
    }
}
