package com.example;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface CalculatorService {
    @WebMethod
    int sum(int a, int b);

    @WebMethod
    int sub(int a, int b);
}