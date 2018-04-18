package com.scrawl.iot.web.exception;

import com.google.common.base.Joiner;
import com.scrawl.iot.web.helper.ApplicationContextHelper;
import com.scrawl.iot.web.resolver.ReloadableMessageSource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -5368015604984251379L;

    private String errorCode;
    private Object[] args;

    public BizException(String errorCode) {
        this(errorCode, (Object[]) null);
    }

    public BizException(String errorCode, Object... args) {
        super(getMessage(errorCode, args));
        this.errorCode = errorCode;
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }

    private static ReloadableMessageSource messageSource;

    public static String getMessage(String errorCode, Object... args) {
        if (messageSource == null) {
            messageSource = ApplicationContextHelper.getBean(ReloadableMessageSource.class);
        }
        String message = messageSource.getMessage(errorCode, args);
        if (StringUtils.isBlank(message)) {
            message = Joiner.on(" ").join("☺", "", args);
        }
        return message;
    }
}
