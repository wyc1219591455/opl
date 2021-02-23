package me.zhengjie.modules.opl.service;

import me.zhengjie.model.vo.ImageUploadVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @website https://docs.auauz.net
 * @author xin.peng
 * @date 2020-08-06
 **/
public interface CmsFileUploadService {

    /**
     * 用于富文本编辑器上传图片
     * @param multipartFile
     * @return
     */
    ImageUploadVo uploadImage(MultipartFile multipartFile);

    /**
     * 上传文件（图纸之类的pdf等文件）
     * @param multipartFile
     * @return
     */
    Map<String,String> uploadFile(MultipartFile multipartFile);
}
