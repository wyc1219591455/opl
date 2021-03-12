package me.zhengjie.utils;

import me.zhengjie.modules.opl.service.dto.WorkOrderMessageToDingTip;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @program: eladmin
 * @description:
 * @author: ming.cao
 * @create: 2021-03-12 16:32
 **/
public class FatherToChild {

    public static <T> WorkOrderMessageToDingTip fatherToChild(T father, T child) throws Exception {
        if (child.getClass().getSuperclass() != father.getClass()) {
            throw new Exception("child 不是 father 的子类");
        }
        Class<?> fatherClass = father.getClass();
        Field[] declaredFields = fatherClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            Method method = fatherClass.getDeclaredMethod("get" + upperHeadChar(field.getName()));
            Object obj = method.invoke(father);
            field.setAccessible(true);
            field.set(child, obj);
        }
        return null;
    }

    public static String upperHeadChar(String in) {
        String head = in.substring(0, 1);
        String out = head.toUpperCase() + in.substring(1, in.length());
        return out;
    }
}
