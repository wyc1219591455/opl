package me.zhengjie.config;

import com.alibaba.druid.support.json.JSONUtils;
import me.zhengjie.model.vo.ImageUploadVo;
import me.zhengjie.model.vo.ResultVO;
import me.zhengjie.utils.ResultVOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 处理返回数据，对返回数据进行包装
 */
@ControllerAdvice
public class ResponseBodyHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Class returnType = methodParameter.getMethod().getReturnType();
//        || o instanceof ImageUploadVo
        if(o instanceof ImageUploadVo){ //富文本编辑器上传图片不需要做封装处理
            return o;
        }
        if (o instanceof ResultVO) {
            return o;
        }
        if (returnType.equals(ResponseEntity.class)) {
            return o;
        }

        if (o instanceof String) {
            return JSONUtils.toJSONString(o);
        }

        return ResultVOUtils.success(o);
    }
}
