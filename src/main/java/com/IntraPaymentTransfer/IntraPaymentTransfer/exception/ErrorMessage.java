package com.IntraPaymentTransfer.IntraPaymentTransfer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement
public class ErrorMessage {

    private int code;
    private HttpStatus status;
    private String error;
    private String message;

}
