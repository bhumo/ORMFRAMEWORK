package com.orm.query;
import com.orm.interfaces.query.*;
import com.orm.exception.*;
import com.orm.model.*;
import com.orm.annotations.*;

import java.sql.*;
import java.util.*;
public class Update{
private Table tableObj;
private String statement=null;
private Map<String,String> columnData;
private Connection connection;


public Update(Map<String,String> columnData,Table tableObj,Connection connection){
this.columnData=columnData;
this.tableObj=tableObj;
this.connection=connection;
this.validate();
}
public void validate(){
boolean isValid=false;
try{
isValid=UpdateValidator.isValidStatement(columnData,tableObj,connection);
}catch(Exception e){
System.out.println(e);
}

if(!isValid) {
this.statement=null;
System.out.println("The Upate statement is not valid");
}
else{
this.createUpdateStatement();
}
return;
}//validate() ends

public void createUpdateStatement() {
try{
String updateString="update "+this.tableObj.getName().trim()+" set ";
String pk = tableObj.getPrimaryKeyAttributeName();
Attribute atr;
boolean addSingleQuote=false;
String dt="";
String s="";
for(String key:columnData.keySet()){
atr=tableObj.getAttributeByName(key);
dt=atr.getDataType();
addSingleQuote=false;
s=this.columnData.get(key);
s=s.trim();
if((atr.isAttributeAutoIncrement()==false) && (key.compareTo(pk)!=0)){

updateString=updateString+ key+ " = ";
if((dt.compareTo("VARCHAR")==0) || (dt.compareTo("CHAR")==0) || (dt.compareTo("DATE")==0) || (dt.compareTo("TIMESTAMP")==0) ||(dt.compareTo("DATETIME")==0)){
addSingleQuote=true;
updateString=updateString+"'"; //adding single quote in double quote for char type data
}// if of datatype comparision ends
updateString=updateString+s;
if(addSingleQuote) updateString=updateString+"'";
updateString=updateString+",";
}//if block ends for autonincrement and key is not primaryKey.

}//for loop ends
String pkVal=this.columnData.get(pk);

pkVal=pkVal.trim();

pk=pk.trim();
updateString=updateString.substring(0,updateString.length()-1);
updateString=updateString+" where "+pk+"="+pkVal;
System.out.println("******************");
System.out.println("UPS:"+updateString);
this.statement=updateString;

}catch(Exception e){

System.out.println(e.getMessage());

}

}//createUpdateStatement() ends

public String getStatement(){
return this.statement;
}

}//update class ends