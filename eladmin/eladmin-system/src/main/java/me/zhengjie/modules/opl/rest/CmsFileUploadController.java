package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.model.vo.ImageUploadVo;
import me.zhengjie.modules.opl.service.CmsFileUploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @website https://docs.auauz.net
 * @author xin.peng
 * @date 2020-08-06
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "CMS：上传管理")
@RequestMapping("/api/fileUpload")
public class CmsFileUploadController {

    private final CmsFileUploadService cmsFileUploadService;

    @Log("上传图片")
    @ApiOperation("上传图片")
    @PostMapping(produces = "application/json;charset=utf8")
    @AnonymousAccess
    public ImageUploadVo uploadCmsImage(@RequestParam MultipartFile upload){
        return cmsFileUploadService.uploadImage(upload);
    }

    @Log("上传文件")
    @ApiOperation("上传文件")
    @PostMapping(value = "/file" ,produces = "application/json;charset=utf8")
    public Map<String,String> uploadCmsFile(@RequestParam MultipartFile file){
        return cmsFileUploadService.uploadFile(file);
    }

}
