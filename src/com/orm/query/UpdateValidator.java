package com.orm.query;
import java.sql.*;
import  java.util.*;
import com.orm.exception.*;
import com.orm.model.*;
import com.orm.annotations.*;
public class UpdateValidator{

public static void checkPrimaryKey(Map<String,String> columnData,Table tableObj,Connection con) throws ORMException{
try{
String pk=tableObj.getPrimaryKeyAttributeName();
String pkVal=columnData.get(pk);
if(pkVal.compareTo("0")==0){
//throw new ORMException("pk not found");
}
if(pkVal==null ){
throw new ORMException("primary key can not be null");
}

String tbName=tableObj.getName();
tbName=tbName.trim();
String dt="";

Attribute atr=tableObj.getAttributeByName(pk);

String query="select * from "+tbName+" where "+pk+"=";
if(atr==null) throw new ORMException(pk+" does not exsits in table "+tbName);
dt=atr.getDataType();
if((dt.compareTo("VARCHAR")==0) || (dt.compareTo("CHAR")==0) || (dt.compareTo("DATE")==0) || (dt.compareTo("TIMESTAMP")==0) ||(dt.compareTo("DATETIME")==0)){
query=query+"'";//adding single quote in double quote
query=query+pkVal+"'";
}//if block ends
else{
query=query+pkVal;
}//else block ends

Statement s=con.createStatement();
ResultSet rs=s.executeQuery(query);
if(!rs.next()){
throw new ORMException("pk not found");
}

}catch(Exception e){
throw new ORMException(e.getMessage());
}
}

public static void mapColumns(Map<String,String> columnData,Table tableObj) throws ORMException{

for(String key:columnData.keySet()){

Attribute atr=tableObj.getAttributeByName(key);
if(atr==null) {
throw new ORMException("Exception: No columns/attribute found with name "+key+" in table "+tableObj.getName());
}
}//for ends

}//mapColumns ends


public static boolean isValidStatement(Map<String,String> columnData,Table tableObj,Connection con) throws ORMException
{

int columnSize=columnData.size();
int columnSizeTable=tableObj.getAttributeCount();

if(columnSize==0){
throw new ORMException("Exception: No columns assosiated with table"+tableObj.getName()+"in the class");

}

try{
mapColumns(columnData,tableObj);
}catch(Exception e){

throw new ORMException(e.getMessage());
}
if(columnSize>columnSizeTable){
//if no of columns in the columnSize is more that means the user have 
//incuded a particular column which may not be the part the part of the 
//table.

for (String key:columnData.keySet()){
Attribute atr=tableObj.getAttributeByName(key);
if(atr==null){
throw new ORMException(key+" not an attribute/column of "+tableObj.getName());
}

}//for ends

}//if columnSize>columnSizeTable

if(columnSize<=columnSizeTable){

checkPrimaryKey(columnData,tableObj,con);
Attribute atr;
Map<String,Attribute> attributeMap = tableObj.getAttributeMap();
String primaryKey = tableObj.getPrimaryKeyAttributeName();
//System.out.println(primaryKey);
atr=null;
boolean printException=false;
//if no columns are left in columnData and and still some columns are left in table
//then check if those are nullable or not...

for(String key:attributeMap.keySet()){
//we will not check if a certain attribute that is already present in table
//but not in columnsData is not a problem because user may not want to update all the columns 
//or user may update just one column.
if(columnData.containsKey(key)==false)continue;

//let's check if value of the attribute is null but the table does not allow null values.

String s=columnData.get(key);
s=s.trim();

if(s.compareTo("null")==0) throw new ORMException(atr.getName()+" does not allow null value");
if(s.compareTo("")==0) throw new ORMException(atr.getName()+" does not allow value with spaces as null constraint is applied");
//we have checked for null constarint.
atr=attributeMap.get(key);
if((atr.isAttributeAutoIncrement())&&(atr.getName().compareTo(primaryKey)!=0)){
printException=true;
if(s.compareTo("")==0) printException=false;
if(s.compareTo("0")==0) printException=false;
if(printException) throw new ORMException(atr.getName()+" is auto increment attribute");

}

//we have checked for autoincrement constraint.


}//for loops ends(iterate over attributeMap)



}//if same size/count ends.


return true;
}//isValidStatement ends




}//update class ends