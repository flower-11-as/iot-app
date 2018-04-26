package com.scrawl.iot.paper.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Description:
 * Created by as on 2018/4/26.
 */
@Slf4j
public class IotDataCastUtil {
    private static String CHARSET_ISO88591 = "iso-8859-1";
    private static String CHARSET_UTF8 = "UTF-8";

    @Getter
    @AllArgsConstructor
    enum IotTypeEnum {
        // byte,short,int,long,float,double,bool,enum,string-iso8859-1,binary,string
        // BYTE,SHORT,INT,LONG,FLOAT,DOUBLE,BOOL,ENUM,STRING-ISO8859-1,BINARY,STRING
        BYTE("byte", false),
        SHORT("short", false),
        INT("int", false),
        LONG("long", false),
        FLOAT("float", false),
        DOUBLE("double", false),
        BOOL("bool", false),
        ENUM("enum", false),
        STRING_ISO8859_1("string-iso8859-1", true),
        BINARY16("binary", true),// string16进制
        STRING("string", true);

        private String code;
        private boolean needBinaryCast;

        public static IotTypeEnum findEnumByCode(String code) {
            if (StringUtils.isEmpty(code)) {
                return null;
            }

            code = code.toLowerCase();
            for (IotTypeEnum e : values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }

            return null;
        }
    }

    public static String commonCast(Object v, String dataType) {
        if (null == v) {
            return "";
        }

        IotTypeEnum typeEnum = IotTypeEnum.findEnumByCode(dataType);
        if (null == typeEnum) {
            return "";
        }

        String s;
        switch (typeEnum) {
            case BYTE:
            case SHORT:
            case INT:
            case LONG:
            case FLOAT:
            case DOUBLE:
            case BOOL:
            case ENUM:
                s = v.toString();
                break;
            case STRING_ISO8859_1:
                s = stringISO8859Cast(v);
                break;
            case BINARY16:
                s = binary16Cast(v);
                break;
            case STRING:
                s = stringCast(v);
                break;
            default:
                s = null;
                break;
        }

        if (s == null) {
            return "";
        }

        return s;
    }

    public static Object commonReversion(String v, String dataType) {
        if (null == v) {
            return null;
        }

        IotTypeEnum typeEnum = IotTypeEnum.findEnumByCode(dataType);
        if (null == typeEnum) {
            return null;
        }

        Object o;
        switch (typeEnum) {
            case BYTE:
                o = Byte.valueOf(v);
                break;
            case SHORT:
                o = Short.valueOf(v);
                break;
            case INT:
                o = Integer.valueOf(v);
                break;
            case LONG:
                o = Long.valueOf(v);
                break;
            case FLOAT:
                o = Float.valueOf(v);
                break;
            case DOUBLE:
                o = Double.valueOf(v);
                break;
            case BOOL:
            case ENUM:
                o = v;
                break;
            case STRING_ISO8859_1:
                o = stringISO8859Reversion(v);
                break;
            case BINARY16:
                o = binary16Reversion(v);
                break;
            case STRING:
                o = stringReversion(v);
                break;
            default:
                o = null;
                break;
        }

        if (o == null) {
            return "";
        }

        return o;
    }

    private static String stringISO8859Cast(Object v) {
        String rs = v.toString();
        try {
            String iso = new String(rs.getBytes(CHARSET_ISO88591), CHARSET_UTF8);
            rs = binaryCast(iso);
        } catch (Exception e) {
            log.error("iot数据转换异常", e);
        }
        return rs;
    }

    private static String binary16Cast(Object a) {
        String v = a.toString();
        try {
            StringBuilder sb = new StringBuilder();
            int size = v.length() / 2 + v.length() % 2;
            for (int i = 0; i < size; i++) {
                String sub;
                if (i == size - 1) {
                    sub = v.substring(2 * i, 2 * (i + 1) - v.length() % 2);
                } else {
                    sub = v.substring(2 * i, 2 * (i + 1));
                }
                sb.append(NumericalUtil.BaseConvert(sub, 16, 10));
            }
            v = binaryCast(sb.toString());
        } catch (Exception e) {
            log.error("iot数据转换异常", e);
        }
        return v;
    }

    private static String stringCast(Object v) {
        String rs = v.toString();
        try {
            rs = binaryCast(rs);
        } catch (Exception e) {
            log.error("iot数据转换异常", e);
        }
        return rs;
    }

    private static String stringISO8859Reversion(String v) {
        String rs = "";
        try {
            rs = binaryReversion(new String(v.getBytes(CHARSET_UTF8), CHARSET_ISO88591));
        } catch (Exception e) {
            log.error("iot数据转换异常", e);
        }
        return rs;
    }

    private static String binary16Reversion(String v) {
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < v.length(); ) {
                sb.append(NumericalUtil.int2CodeString((int) v.charAt(i), 16));
            }
        } catch (Exception e) {
            log.error("iot数据转换异常", e);
        }
        return sb.toString();
    }

    private static String stringReversion(String v) {
        String rs = "";
        try {
            rs = binaryReversion(v);
        } catch (Exception e) {
            log.error("iot数据转换异常", e);
        }
        return rs;
    }

    private static String binaryCast(String v) {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < v.length(); ) {
            String sub;
            if (v.charAt(index) == '1') {
                sub = v.substring(index, index + 3);
                index += 3;
            } else {
                sub = v.substring(index, index + 2);
                index += 2;
            }
            sb.append((char) Integer.parseInt(sub));
        }
        return sb.toString();
    }

    private static String binaryReversion(String v) {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < v.length(); ) {
            sb.append((int) v.charAt(0));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String a = "3130322E393739343733343131382E37333231343733";
        System.out.println(a.length());
    }
}
