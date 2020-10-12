package me.zhengjie.utils;

import me.zhengjie.model.vo.RCodeEnum;
import me.zhengjie.model.vo.ResultVO;

/**
 * 封装返回模型构建方法
 */
public class ResultVOUtils {
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(RCodeEnum.OK.getCode());
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code, String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(RCodeEnum.OK.getCode());
        resultVO.setMessage(msg);
        resultVO.setIsError(true);
        return resultVO;
    }
}
