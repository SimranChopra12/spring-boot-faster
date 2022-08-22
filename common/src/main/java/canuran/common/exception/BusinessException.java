package canuran.common.exception;

import canuran.common.ResultMessage;

/**
 * 业务运行时异常对象，方便统一处理，支持链式调用。
 *
 * @author canuran
 */
public class BusinessException extends RuntimeException implements ResultException {

    // 异常类型、结果编码
    private int code = 0;

    // 异常可带出结果数据
    private Object data;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(ResultMessage message) {
        this(message.getMessage());
        this.code = message.getCode();
        this.data = message.getData();
    }

    public BusinessException(ResultMessage message, Throwable cause) {
        this(message.getMessage(), cause);
        this.code = message.getCode();
        this.data = message.getData();
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

}
