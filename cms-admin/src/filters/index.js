// 过滤器
import { dateFormat } from "@/util/date";

export const toBool = (bool)=>{
    return bool ? "是" : "否";
};

// 状态过滤器
export const fStatus = (bool)=>{
    return bool ? "激活" : "冻结";
};

// 格式化日期
export function fDate(date, fat) {
    if(!date){
        return "";
    }
    date = new Date(date);
    return dateFormat(date, fat);
}
// 更改菜单配置

export function isDisabled(value) {
    if(!value){
        return false;
    }
    let status = value.every((item) => {
        return !!item.isCreated;
    });
    return status;
}