package me.zhengjie.modules.opl.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.config.FileProperties;
import me.zhengjie.model.vo.ImageUploadVo;
import me.zhengjie.modules.opl.service.CmsFileUploadService;
import me.zhengjie.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * @website https://docs.auauz.net
 * @author xin.peng
 * @date 2020-08-06
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CmsFileUploadServiceImpl implements CmsFileUploadService {

    @Value("${image-base-url}")
    private String imageBaseUrl;

    @Value("${file-base-url}")
    private String fileBaseUrl;

    private final FileProperties fileProperties;

    @Override
    public ImageUploadVo uploadImage(MultipartFile multipartFile) {
        File file = FileUtil.upload(multipartFile, fileProperties.getPath().getAvatar());
        String fileName = file.getName();
        String fileUrl = imageBaseUrl + fileName;
        return new ImageUploadVo(1,fileName,fileUrl);
    }

    @Override
    public Map<String, String> uploadFile(MultipartFile multipartFile) {
        File file = FileUtil.upload(multipartFile,fileProperties.getPath().getPath());
        String fileName = file.getName();
        String fileUrl = fileBaseUrl + fileName;
        Map<String,String> map = new HashMap<String,String>(2){{
            put("fileName", fileName);
            put("fileUrl", fileUrl);
        }};
        return map;
    }

}
