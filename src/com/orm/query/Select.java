package com.orm.query;
import java.sql.*;
import java.util.*;
import java.lang.reflect.*;
import com.orm.exception.*;
import com.orm.model.*;
public class Select{

String statement;
Class classPointer;
Connection connection;
boolean from;
boolean where;
boolean groupBy;
boolean having;
boolean distinct;
boolean orderBy;
boolean subQuery;
boolean addSingleQuote;
boolean addRoundBracket;
Table tableObj;
TMDB tmdb;
Map<String,String> columnMapping;


public Select(String tableName,Connection connection,Map<String,String> columnMapping,Class classPointer,Table tableObj ,TMDB tmdb)
{
this.statement="select * from "+tableName.trim();
this.connection=connection;
this.from=true;
this.where=false;
this.groupBy=false;
this.distinct=false;
this.orderBy=false;
this.subQuery=false;
this.columnMapping=columnMapping;
this.classPointer=classPointer;
this.addSingleQuote=false;
this.tableObj=tableObj;
this.addRoundBracket=false;
this.tmdb=tmdb;
}//paramterized constructor ends

public Select eq(Object value)throws ORMException{
if(value==null) throw new ORMException("Value can not be null");
if(this.addSingleQuote)this.statement=this.statement.trim()+"='"+value.toString().trim()+"'";
else this.statement=this.statement.trim()+"="+value.toString().trim();
if (this.addRoundBracket) {
this.statement=this.statement.trim()+")";
this.addRoundBracket=false;
}//if for checking roud bracket ends
this.addSingleQuote=false;
System.out.println(this.statement);
return this;
}//eq() ends

public Select ne(Object value)throws ORMException{
if(value==null) throw new ORMException("Value can not be null");
if(this.addSingleQuote)this.statement=this.statement.trim()+"!='"+value.toString().trim()+"'";
else this.statement=this.statement.trim()+"!="+value.toString().trim();
this.addSingleQuote=false;
if (this.addRoundBracket) {
this.statement=this.statement.trim()+")";
this.addRoundBracket=false;
}//if for checking roud bracket ends
System.out.println(this.statement);
return this;

}//ne() ends

public Select gt(Object value) throws ORMException {
if(value==null) throw new ORMException("Value can not be null");
if(this.addSingleQuote)this.statement=this.statement.trim()+">'"+value.toString().trim()+"'";
else this.statement=this.statement.trim()+">"+value.toString().trim();
this.addSingleQuote=false;
if (this.addRoundBracket) {
this.statement=this.statement.trim()+")";
this.addRoundBracket=false;
}//if for checking roud bracket ends
System.out.println(this.statement);
return this;

}//gt() ends

public Select lt(Object value) throws ORMException {
if(value==null) throw new ORMException("Value can not be null");
if(this.addSingleQuote)this.statement=this.statement.trim()+"<'"+value.toString().trim()+"'";
else this.statement=this.statement.trim()+"<"+value.toString().trim();
if (this.addRoundBracket) {
this.statement=this.statement.trim()+")";
this.addRoundBracket=false;
}//if for checking roud bracket ends
if (this.addRoundBracket) {
this.statement=this.statement.trim()+")";
this.addRoundBracket=false;
}//if for checking roud bracket ends

this.addSingleQuote=false;

System.out.println(this.statement);
return this;

}//lt() ends

public Select le(Object value) throws ORMException {
if(value==null) throw new ORMException("Value can not be null");
if(this.addSingleQuote)this.statement=this.statement.trim()+"<='"+value.toString().trim()+"'";
else this.statement=this.statement.trim()+"<="+value.toString().trim();
if (this.addRoundBracket) {
this.statement=this.statement.trim()+")";
this.addRoundBracket=false;
}//if for checking roud bracket ends
this.addSingleQuote=false;
System.out.println(this.statement);
return this;

}//le() ends


public Select ge(Object value) throws ORMException {
if(value==null) throw new ORMException("Value can not be null");
if(this.addSingleQuote)this.statement=this.statement.trim()+">='"+value.toString().trim()+"'";
else this.statement=this.statement.trim()+">="+value.toString().trim();
if (this.addRoundBracket) {
this.statement=this.statement.trim()+")";
this.addRoundBracket=false;
}//if for checking roud bracket ends
this.addSingleQuote=false;
System.out.println(this.statement);
return this;

}//ge() ends


public Select where(String columnName) throws ORMException{

if(this.groupBy||this.having||this.distinct||this.orderBy)throw new ORMException("where clause can not come after groupBy/having/distinct/orderBy clause");
if(this.where==true && this.subQuery==false) throw new ORMException("Incorrect usage of where clause ");
String value=this.columnMapping.get(columnName);
if(value==null) throw new ORMException(columnName+" does not exists");
Attribute at=this.tableObj.getAttributeByName(value);
if(at==null) throw new ORMException(value +" does not exists in table");

String dt=at.getDataType();
if((dt.compareTo("VARCHAR")==0) || (dt.compareTo("CHAR")==0) || (dt.compareTo("DATE")==0) || (dt.compareTo("TIMESTAMP")==0) ||(dt.compareTo("DATETIME")==0))
{
this.addSingleQuote=true;
}
this.statement=this.statement.trim()+" where "+value.trim();
this.where=true;
System.out.println(this.statement);
return this;

}//where function ends

public Select groupBy(String columnName) throws ORMException{
if(this.having||this.distinct||this.orderBy)throw new ORMException("where clause can not come after groupBy/having/distinct/orderBy clause");
String value=this.columnMapping.get(columnName.trim());
if(value==null) throw new ORMException(columnName+" does not exists");
this.statement=this.statement+" GROUP BY "+value.trim();
this.groupBy=true;
System.out.println(this.statement);
return this;

}//groupBy() ends

public Select in(Object ...values) throws ORMException{
if(this.where==false) throw new ORMException("Invalid Syntax: IN Operator must come after 'WHERE'");
if(values==null) throw new ORMException("Values must be passed");
this.statement=this.statement+" IN (";
for(Object value :values){
if(this.addSingleQuote)this.statement=this.statement+"'"+value.toString().trim()+"',";
this.statement=this.statement+value.toString().trim()+",";
}//for ends
this.statement=this.statement.substring(0,this.statement.length()-1);
this.statement=this.statement+")";
this.addSingleQuote=false;
System.out.println(this.statement);
return this;
}//in() ends




public Select or(String columnName) throws ORMException{

if(this.groupBy||this.having||this.distinct||this.orderBy)throw new ORMException("where clause can not come after groupBy/having/distinct/orderBy clause");
if(!this.where) throw new ORMException("Invalid syntax:OR must come after Where clause");

String value=this.columnMapping.get(columnName);
if(value==null) throw new ORMException(columnName+" does not exists");
String arr[]=this.statement.split(" ");
/*here we have to consider two possibilites
1. there is no subquery
2. there is a subquery
	if subquery is present we can have to put "(" round bracket  on second where clause
*/
boolean foundWhere=false;
int indexOfWhere=0;
String newStatement="";
for(String x:arr){
if(x.compareTo("where")==0){
if(!this.subQuery){
arr[indexOfWhere]="where (";
foundWhere=true;
newStatement=newStatement+arr[indexOfWhere]+" ";
break;
}//if of subQuery exists ends
}//if ends
indexOfWhere++;
newStatement=newStatement+x+" ";
}//for ends

for(int i=indexOfWhere+1;i<arr.length;i++)newStatement=newStatement+arr[i]+" ";
newStatement=newStatement.substring(0,newStatement.length()-1);
System.out.println(newStatement);
Attribute at=this.tableObj.getAttributeByName(value);
if(at==null) throw new ORMException(value +" does not exists in table");

String dt=at.getDataType();
if((dt.compareTo("VARCHAR")==0) || (dt.compareTo("CHAR")==0) || (dt.compareTo("DATE")==0) || (dt.compareTo("TIMESTAMP")==0) ||(dt.compareTo("DATETIME")==0))
{
this.addSingleQuote=true;
}
this.statement=newStatement+" OR "+value.trim();
System.out.println(this.statement);
this.addRoundBracket=true;
return this;




}//or()ends


public Select and(String columnName) throws ORMException{

if(!this.where) throw new ORMException("Invalid syntax:AND must come after Where clause");

String value=this.columnMapping.get(columnName);
if(value==null) throw new ORMException(columnName+" does not exists");
String arr[]=this.statement.split(" ");
/*here we have to consider two possibilites
1. there is no subquery
2. there is a subquery
	if subquery is present we can have to put "(" round bracket  on second where clause
*/
boolean foundWhere=false;
int indexOfWhere=0;
String newStatement="";
for(String x:arr){
if(x.compareTo("where")==0){
if(!this.subQuery){
arr[indexOfWhere]="where (";
foundWhere=true;
newStatement=newStatement+arr[indexOfWhere]+" ";
break;
}//if of subQuery exists ends
}//if ends
indexOfWhere++;
newStatement=newStatement+x+" ";
}//for ends

for(int i=indexOfWhere+1;i<arr.length;i++)newStatement=newStatement+arr[i]+" ";
newStatement=newStatement.substring(0,newStatement.length()-1);
System.out.println(newStatement);
Attribute at=this.tableObj.getAttributeByName(value);
if(at==null) throw new ORMException(value +" does not exists in table");

String dt=at.getDataType();
if((dt.compareTo("VARCHAR")==0) || (dt.compareTo("CHAR")==0) || (dt.compareTo("DATE")==0) || (dt.compareTo("TIMESTAMP")==0) ||(dt.compareTo("DATETIME")==0))
{
this.addSingleQuote=true;
}
this.statement=newStatement+" AND "+value.trim();
System.out.println(this.statement);
this.addRoundBracket=true;
return this;




}//and()ends







public Select orderBy(String columnName)throws ORMException{

try{

//check if the given column exists or not
String value=this.columnMapping.get(columnName);
if(value==null) throw new ORMException(columnName+" does not exists");
if(orderBy==true){
this.statement=this.statement.trim()+","+value.trim();
System.out.println("Query "+this.statement);
return this;
}
this.statement=this.statement.trim()+" order by "+value.trim();
System.out.println("Query "+this.statement);
this.orderBy=true;
return this;
}catch(Exception e){
throw new ORMException(e.getMessage());
}

}//orderBy() ends



public Select ascending() throws ORMException{
if(!this.orderBy) throw new ORMException("Use right syntax for 'asc' (Must be used after ORDER BY statement).");
this.statement=this.statement.trim()+" asc";
return this;
}//asc() ends


public Select descending() throws ORMException{
if(!this.orderBy) throw new ORMException("Use right syntax for 'desc' (Must be used after ORDER BY statement).");
this.statement=this.statement.trim()+" desc";
return this;

}//des() ends

public Select on(String columnNameX,String columnNameY){
this.statement=this.statement+" ON X." + columnNameX +"=Y."+columnNameY;
System.out.println(this.statement);
return this;
}


public Select innerJoin(String tableName,String columnNameX,String columnNameY)throws ORMException{
if(tableName==null) throw new ORMException("Null can not be passed as argument to innerJoin()");
TableMapBean tmb=this.tmdb.getTableMapping(tableName);
Table tableObj2=tmb.getTableObject();
//System.out.println(tableObj2.getName());
Map<String,String> columnMapping2=tmb.getColumnMapping();
String value2=columnMapping2.get(columnNameY);
if(value2==null) throw new ORMException(columnNameY+" does not exists");
String value=this.columnMapping.get(columnNameY);
if(value==null)  throw new ORMException(columnNameX+" does not exists");
this.statement=this.statement+" X INNER JOIN "+ tableObj2.getName().trim()+" Y";
System.out.println(this.statement);
return this.on(value,value2);


}//innerJoin ends


public Select leftJoin(String tableName,String columnNameX,String columnNameY)throws ORMException{
if(tableName==null) throw new ORMException("Null can not be passed as argument to innerJoin()");
TableMapBean tmb=this.tmdb.getTableMapping(tableName);
Table tableObj2=tmb.getTableObject();
//System.out.println(tableObj2.getName());
Map<String,String> columnMapping2=tmb.getColumnMapping();
String value2=columnMapping2.get(columnNameY);
if(value2==null) throw new ORMException(columnNameY+" does not exists");
String value=this.columnMapping.get(columnNameY);
if(value==null)  throw new ORMException(columnNameX+" does not exists");
this.statement=this.statement+" X LEFT JOIN "+ tableObj2.getName().trim()+" Y";
System.out.println(this.statement);
return this.on(value,value2);


}//leftJoin ends

public Select rightJoin(String tableName,String columnNameX,String columnNameY)throws ORMException{
if(tableName==null) throw new ORMException("Null can not be passed as argument to innerJoin()");
TableMapBean tmb=this.tmdb.getTableMapping(tableName);
Table tableObj2=tmb.getTableObject();
//System.out.println(tableObj2.getName());
Map<String,String> columnMapping2=tmb.getColumnMapping();
String value2=columnMapping2.get(columnNameY);
if(value2==null) throw new ORMException(columnNameY+" does not exists");
String value=this.columnMapping.get(columnNameY);
if(value==null)  throw new ORMException(columnNameX+" does not exists");
this.statement=this.statement+" X RIGHT JOIN "+ tableObj2.getName().trim()+" Y";
System.out.println(this.statement);
return this.on(value,value2);


}//rightJoin ends

public Select selfJoin(String columnNameX,String columnNameY)throws ORMException{

String value2=columnMapping.get(columnNameY);
if(value2==null) throw new ORMException(columnNameY+" does not exists");
String value=this.columnMapping.get(columnNameY);
if(value==null)  throw new ORMException(columnNameX+" does not exists");
this.statement=this.statement+" X LEFT JOIN "+ tableObj.getName().trim()+" Y";
System.out.println(this.statement);
return this.on(value,value2);


}//leftJoin ends




public Object query()throws ORMException{

if(this.connection==null) throw new ORMException("Can not establish connection");
if(this.columnMapping==null) throw new ORMException("Column Mapping not provided");

try{
Statement statement=this.connection.createStatement();
ResultSet rs=statement.executeQuery(this.statement);
List<Object> li=new ArrayList<>();
Field[] fields=this.classPointer.getDeclaredFields();
while(rs.next()){
Object classObject=this.classPointer.newInstance();

for(Field field:fields){
//System.out.println(field.getName());
Object value=rs.getObject(this.columnMapping.get(field.getName()));
//System.out.println(rs.getObject(this.columnMapping.get(field.getName())));
field.setAccessible(true);
field.set(classObject,value);
}//for ends
li.add(classObject);
}//while ends
return li;
}catch(Exception e){
throw new ORMException(e.getMessage());
}
//we will have to set the values of the fields of the given class 
//and return a list 



}//query() ends

}//Select class ends

