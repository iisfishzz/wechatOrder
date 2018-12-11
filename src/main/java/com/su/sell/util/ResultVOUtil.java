package com.su.sell.util;

import com.su.sell.vo.ResultVO;
/**
* @Description:    创建前端返回对象的工具类
* @Author:         scott
* @CreateDate:     2018/12/8 16:15
* @UpdateUser:     scott
* @UpdateDate:     2018/12/8 16:15
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
