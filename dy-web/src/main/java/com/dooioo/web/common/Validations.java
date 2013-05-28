package com.dooioo.web.common;

import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.util.regex.Pattern;

/**
 * 验证工具类
 * User: kuang
 * Date: 12-8-17
 * Time: 下午2:41
 */
public class Validations {


    public static void rejectIfNull(Errors errors, String field, String message) {
        ValidationUtils.rejectIfEmpty(errors, field, null, message);
    }
    /**
     * 验证字段值必须不为空（包括空白）
     * @param errors
     * @param field
     * @param message
     */
    public static void rejectIfEmpty(Errors errors, String field, String message) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, null, message);
    }

    /**
     * 验证字段值必须为整数
     * @param errors
     * @param field
     * @param message
     */
    public static void rejectIfNotInteger(Errors errors, String field, String message) {
        Assert.notNull(errors, "Errors object must not be null");
        String value = getFieldValue(errors, field, message);
        Pattern pattern = Pattern.compile("[0-9]*");
        if(pattern.matcher(value + "").matches() == false) {
            errors.rejectValue(field, null, message);
        }
    }

    /**
     * 验证长度
     * @param errors
     * @param field
     * @param minLength 最小值
     * @param maxLenght 最大值
     * @param message
     */
    public static void rejectLength(Errors errors, String field, int minLength, int maxLenght, String message) {
        String value = getFieldValue(errors, field, message);
        if(value.length() < minLength || value.length() > maxLenght) {
            errors.rejectValue(field, null, message);
        }
    }

    public static void rejectValue(Errors errors, String field, String message) {
        errors.reject(field, null, message);
    }


    private static String getFieldValue(Errors errors, String field, String message) {
        Assert.notNull(errors, "Errors object must not be null");
        Object value = errors.getFieldValue(field);
        if(value == null) {
            errors.rejectValue(field, null, message);
        }
        return String.valueOf(value);
    }
}
