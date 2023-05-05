package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.exception.RequestInvalidParameterException;
import jp.co.axa.apidemo.model.request.EmployeeRequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
@Service
public class RequestParameterServiceImpl implements RequestParameterService{

    public Logger log = LoggerFactory.getLogger(RequestParameterServiceImpl.class);

    @Autowired
    private Validator validator;

    private static final String ID_REGEX = "^[0-9]*$";

    RequestParameterServiceImpl(Validator validator){
        this.validator = validator;
    }

    @Override
    public Boolean isIdValid(String id) {
        if(!Objects.isNull(id) && Pattern.matches(ID_REGEX,id)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Long extractId(String id) {
        try {
            return Long.valueOf(id);
        }catch (NumberFormatException numberFormatException){
            log.info("This employee ID not a long number.");
            log.error(numberFormatException.getMessage());
            throw new RequestInvalidParameterException();
        }
    }

    @Override
    public Boolean isEmployeeRequestModelValid(EmployeeRequestModel employeeRequestModel) {
        Set<ConstraintViolation<EmployeeRequestModel>> violations = validator.validate(employeeRequestModel);
        return violations.isEmpty();
    }
}
