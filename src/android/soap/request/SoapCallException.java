package com.hpe.hybridsitescope.soap.request;


/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Date: 11.04.11
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
public class SoapCallException extends Exception {

    public SoapCallException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SoapCallException(String s) {
        super(s);
    }
}
