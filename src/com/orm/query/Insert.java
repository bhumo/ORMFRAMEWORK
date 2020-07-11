package com.orm.query;
import java.sql.*;
import java.util.*;
import com.orm.interfaces.query.*;
import com.orm.exception.*;
import com.orm.model.*;
import com.orm.annotations.*;
public class Insert implements Query{
private Table tableObj;
private String statement=null;
private Map<String,String> columnData;
public Insert(Map<String,String> columnData,Table tableObj){
this.columnData=columnData;
this.tableObj=tableObj;
this.validate();
this.createInsertStatement();
}
public void validate(){
boolean isValid=false;
try{
isValid=InsertValidator.isValidStatement(columnData,tableObj);
}catch(Exception e){
System.out.println(e);
}

if(!isValid) {
System.out.println("The insert statement is not valid");
return;
}


}//validate() ends

public void createInsertStatement(){
boolean addToString=false;
boolean addSingleQuote=false;
Attribute atr=null;
String columnPart="insert into "+tableObj.getName()+"(";
String valuePart=" VALUES(";
String s="";
String dt="";
for(String key:this.columnData.keySet()){
addToString=false;
addSingleQuote=false;
atr=tableObj.getAttributeByName(key);
s=this.columnData.get(key);
if(atr.isAttributeAutoIncrement()==false) addToString=true;

if(addToString){
dt=atr.getDataType();
if((dt.compareTo("VARCHAR")==0) || (dt.compareTo("CHAR")==0) || (dt.compareTo("DATE")==0) || (dt.compareTo("TIMESTAMP")==0) ||(dt.compareTo("DATETIME")==0)){
addSingleQuote=true;
valuePart=valuePart+"'"; //adding single quote in double quote for char type data
}

columnPart=columnPart+key+",";
if(addSingleQuote) valuePart=valuePart+s+"',";
else valuePart=valuePart+s+",";	
}


}//for ends

columnPart=columnPart.substring(0,columnPart.length()-1);
valuePart=valuePart.substring(0,valuePart.length()-1);
columnPart=columnPart+")";
valuePart=valuePart+")";
this.statement=columnPart+valuePart;
}


public String getStatement(){
return this.statement;
}

}//insert class  ends