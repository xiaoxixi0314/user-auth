package com.github.xiaoxixi.auth.utils;

import com.github.xiaoxixi.auth.contants.Constants;
import com.github.xiaoxixi.auth.exception.BizException;
import com.github.xiaoxixi.auth.exception.ParamsException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class Md5Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Md5Utils.class);

    private Md5Utils(){}

    public static String encrypt(String source) throws BizException {
        if (StringUtils.isEmpty(source)) {
            throw new ParamsException("encrypt source can't be empty");
        }
        String md5Str = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            byte[] bytes = md5.digest(source.getBytes(Constants.CHARSET_UTF_8));
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            md5Str = stringBuffer.toString();
        } catch (Exception ex) {
            LOGGER.error("encrypt source string to md5 error:", ex);
            throw new BizException("encrypt system error");
        }
        return md5Str;
    }
}
