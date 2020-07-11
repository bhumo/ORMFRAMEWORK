package com.orm.exception;

public class ORMException extends Exception{
private String str="";
public ORMException(String message){
super(message);
this.str=message;
}

public String getMessage(){
return this.str;
}
}