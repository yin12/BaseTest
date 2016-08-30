package com.example.basetest.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字与字母间转化
 */
public class PinyinUtil {

    /**
     * 根据汉字获取对应的拼音
     *
     * @param str
     * @return
     */
    public static String getPinyin(String str) {
        // 黑马 -> HEIMA
        // 设置输出配置
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        // 设置大写
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        // 设置不需要音调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        StringBuilder sb = new StringBuilder();

        // 获取字符数组
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            // 如果是空格, 跳过当前的循环
            if (Character.isWhitespace(c)) {
                continue;
            }

            if (c > 128 || c < -127) {
                // 可能是汉字
                try {
                    // 根据字符获取对应的拼音. 黑 -> HEI , 单 -> DAN , SHAN
                    String[] s = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (s != null) {
                        sb.append(s[0]);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
//					e.printStackTrace();
                    return sb.toString();
                }
            } else {
                // *&$^*@654654LHKHJ
                // 不需要转换, 直接添加
                sb.append(c);
            }
        }

        return sb.toString();
    }


    public static String getNameNum(String name) {
        if (name != null && name.length() != 0) {
            int len = name.length();
            char[] nums = new char[len];
            for (int i = 0; i < len; i++) {
                String tmp = name.substring(i);
                nums[i] = getOneNumFromAlpha(tmp.charAt(0));
            }
            return new String(nums);
        }
        return "";
    }

    public static char getOneNumFromAlpha(char firstAlpha) {
        switch (firstAlpha) {
            case 'A':
            case 'B':
            case 'C':
                return '2';
            case 'D':
            case 'E':
            case 'F':
                return '3';
            case 'G':
            case 'H':
            case 'I':
                return '4';
            case 'J':
            case 'K':
            case 'L':
                return '5';
            case 'M':
            case 'N':
            case 'O':
                return '6';
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
                return '7';
            case 'T':
            case 'U':
            case 'V':
                return '8';
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
                return '9';
            default:
                return '0';
        }
    }
}

