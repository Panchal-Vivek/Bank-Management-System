package jsp.bankmanagement.exception;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jsp.bankmanagement.dto.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleAccountNotFound(
            AccountNotFoundException ex) {

        ResponseStructure<String> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setData(null);

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(MinimumBalanceException.class)
    public ResponseEntity<ResponseStructure<String>> handleMinimumBalance(
            MinimumBalanceException ex) {

        ResponseStructure<String> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setMessage(ex.getMessage());
        res.setData(null);

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ResponseStructure<String>> invalidAmount(
    		InvalidAmountException ex) {

        ResponseStructure<String> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setMessage(ex.getMessage());
        res.setData(null);

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ResponseStructure<String>> handleInsufficientBalance(
            InsufficientBalanceException ex) {

        ResponseStructure<String> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setMessage(ex.getMessage());
        res.setData(null);

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(DuplicateBankException.class)
    public ResponseEntity<ResponseStructure<String>> handleDuplicateBank(
            DuplicateBankException ex) {

        ResponseStructure<String> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.CONFLICT.value());
        res.setMessage(ex.getMessage());
        res.setData(null);

        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseStructure<String>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        ResponseStructure<String> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.CONFLICT.value());
        res.setMessage("Duplicate or conflicting data. Please check IFSC and contact values.");
        res.setData(null);

        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }
}