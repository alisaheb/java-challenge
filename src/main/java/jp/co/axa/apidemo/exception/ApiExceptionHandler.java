package jp.co.axa.apidemo.exception;

import jp.co.axa.apidemo.enums.ErrorCodeEnum;
import jp.co.axa.apidemo.model.response.ApiHeaderModel;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiHeaderModel apiHeaderModel = new ApiHeaderModel();
        apiHeaderModel.setErrorCode(ErrorCodeEnum.REQUEST_PARAMETER_ERROR_CODE.getErrorCode());
        apiHeaderModel.setErrorMessage(ErrorCodeEnum.REQUEST_PARAMETER_ERROR_CODE.getErrorMessage());
        return new ResponseEntity<>(apiHeaderModel, HttpStatus.BAD_REQUEST);
    }
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiHeaderModel apiHeaderModel = new ApiHeaderModel();
        apiHeaderModel.setErrorCode(ErrorCodeEnum.REQUEST_PARAMETER_ERROR_CODE.getErrorCode());
        apiHeaderModel.setErrorMessage(ErrorCodeEnum.REQUEST_PARAMETER_ERROR_CODE.getErrorMessage());
        return new ResponseEntity<>(apiHeaderModel, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> apiEntityNotFoundExceptionHandler(){
        ApiHeaderModel apiHeaderModel = new ApiHeaderModel();
        apiHeaderModel.setErrorCode(ErrorCodeEnum.REQUEST_ENTITY_NOT_FOUND.getErrorCode());
        apiHeaderModel.setErrorMessage(ErrorCodeEnum.REQUEST_ENTITY_NOT_FOUND.getErrorMessage());
        return new ResponseEntity<>(apiHeaderModel, HttpStatus.NOT_FOUND);
    }
}
