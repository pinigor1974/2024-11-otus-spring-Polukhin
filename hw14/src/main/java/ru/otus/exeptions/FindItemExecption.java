package ru.otus.exeptions;

public class FindItemExecption extends RuntimeException{
    public FindItemExecption(String message, Throwable cause) {
        super(message, cause);
    }
    public FindItemExecption(String mesage){
        super(mesage);
    }

}
