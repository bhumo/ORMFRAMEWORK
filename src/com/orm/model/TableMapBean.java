package com.orm.model;
import java.util.*;

public class TableMapBean{

private Table tableObj;
private String SQLTableName;
private Class tableClassPointer;
private String classPackage;
private Map<String,String> columnMapping;

public TableMapBean(){
classPackage=null;
tableObj=null;
tableClassPointer=null;
columnMapping=null;
}

public void setTableObject(Table tableObj){
this.tableObj=tableObj;
}

public String getSqlTableName(){
return this.tableObj.getName();
}
public Table getTableObject(){
return this.tableObj;
}
public void setClassPackage(String classPackage){
this.classPackage=classPackage;
}
public void setTableClassPointer(Class tabelClassPointer){
this.tableClassPointer=tableClassPointer;
}

public Class getTableClassPointer(){
if(this.tableClassPointer==null && this.classPackage!=null){
try{
this.tableClassPointer=Class.forName(this.classPackage);
}catch(Exception e){
System.out.println(e);
}
}
return this.tableClassPointer;
}


public void setColumnMapping(Map<String,String> columnMapping){
this.columnMapping=columnMapping;
}


public Map<String,String> getColumnMapping(){
return this.columnMapping;
}

}