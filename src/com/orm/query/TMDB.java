package com.orm.query;
import com.orm.exception.*;
import com.orm.model.*;
import com.orm.annotations.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import java.sql.*;
import java.io.*;
import com.orm.exception.*;
import com.orm.model.*;
public class TMDB{


Map<String,Table> tableMap;
Connection connection;
String connectionString;
String userName;
String password;
String host;
String portNumber;
String dataBase;
String path;
Map<String,TableMapBean> tableMapping;

public TMDB(String path,String userName,String password,String database){
this.tableMapping=new HashMap<>();
this.dataBase=database;
this.host="localhost";
this.path=path;
this.userName=userName;
this.password=password;
this.portNumber="3306";
this.loadDataStructure();
this.loadTableClassDS();
}


public TMDB(String path,String userName,String password,String database,String host,String portNumber){
this.tableMapping=new HashMap<>();
this.dataBase=database;
this.host=host;
this.path=path;
this.userName=userName;
this.password=password;
this.portNumber=portNumber;
this.loadDataStructure();
this.loadTableClassDS();
}

private void loadDataStructure(){
try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://"+host+":"+portNumber+"/"+dataBase,this.userName,this.password);
this.connection=con;
this.connectionString="jdbc:mysql://"+host+":"+portNumber+"/"+dataBase;
DatabaseMetaData dbmd=con.getMetaData();
ResultSet rs = dbmd.getCatalogs();

tableMap=new HashMap<>();
String tableName="";
ResultSet rs2=null;
String db=null;
Table table=null;
Attribute attribute=null;
if(rs!=null){
while(rs.next()){
db=rs.getString(1).trim();
if(db.compareTo("school")==0){
//here we have the db.
//now we need to extract the table

System.out.println("Catalog:"+rs.getString(1).trim());
break;
}
}
}
String x=null;
String colName=null;
if(rs==null) System.out.println("rs null");
if(db!=null){
rs=dbmd.getTables(db,null,null,null);
while(rs.next()){
table=new Table();
tableName=rs.getString(3);
table.setName(tableName.trim());
table.setType(rs.getString(4));
table.setSelfReferencingColumn(rs.getString(9));
table.setRefGeneration(rs.getString(10));	
rs2=dbmd.getPrimaryKeys(db,null,tableName);
while(rs2.next()){
//System.out.println(""+rs2.getString(3));
//System.out.println("Name: "+rs2.getString(4));
//System.out.println(rs2.getString(6));
table.setPrimaryKey(rs2.getString(4).trim());

}
rs2=dbmd.getImportedKeys(db,null,tableName);
while(rs2.next()){
//System.out.println("PKTABLE_CAT: "+rs2.getString(1));
//System.out.println("PKTable_name"+rs2.getString(3));
//System.out.println("PKCol_name"+rs2.getString(4));
//System.out.println("FK_CAT:"+rs2.getString(5));
//System.out.println("FKTABLE: "+rs2.getString(7));
table.setForeignKey(rs2.getString(8));
}
rs2=dbmd.getExportedKeys(db,null,tableName);
rs2=dbmd.getColumns(db,null,tableName,null);
while(rs2.next()){
//System.out.println("******Column:**********");
attribute=new Attribute();
colName=rs2.getString(4).trim();
attribute.setName(colName);
attribute.setDataType(rs2.getString(6));
//System.out.println("type_name: "+rs2.getString(6));
attribute.setColumnSize(rs2.getInt(7));
attribute.setBufferLength(rs2.getInt(8));
attribute.setDecimalDigit(rs2.getInt(9));
//System.out.println("num-prec-radix:"+rs2.getString(10));
//System.out.println("NULLABLE: "+rs2.getString(11));
attribute.setColumnDefault(rs2.getString(13));
//System.out.println("SQL_DATA_TYPE : "+rs2.getString(14));
//System.out.println(" SQL_DATETIME_SUB: "+rs2.getString(15));
attribute.setCharOctetLength(rs2.getInt(16));
attribute.setOrdinalPosition(rs2.getInt(17));
x = rs2.getString(18).trim();
if(x.equals("YES")){
attribute.setNullable(true);
}
else{
attribute.setNullable(false);
}

x=rs2.getString(23).trim();
if(x.equals("YES")){
attribute.setAutoIncrement(true);
}
else{
attribute.setAutoIncrement(false);
}
x=rs2.getString(24).trim();
if(x.equals("YES")){
attribute.setGeneratedColumn(true);
}
else{
attribute.setGeneratedColumn(false);
}

table.addAttribute(colName,attribute);

}//while
System.out.println(table.getAttributeCount());
//System.out.println(table.createInsertStatement());
//System.out.println(table.createSelectStatement(null));
cmd
tableMap.put(tableName,table);
}//iterate over db

}//if db is not null

}catch(Exception e){
System.out.println(e);
}

}//load ds() ends




private void recursivePrint(File[] arr,String path,String basePath){
for(File f:arr){

if(f.isFile()){
if(f.getName().endsWith(".class"))
{
//System.out.println(f.getName());
String packageName="";

if(path.compareTo(basePath)!=0){
packageName=path;
int indexOf=basePath.length();
System.out.println(packageName.substring(indexOf+1,path.length()));
packageName = packageName.substring(indexOf+1,path.length());
packageName=packageName.replace(File.separator,".");
packageName=packageName+".";
//System.out.println("Pakage Name: "+packageName);
//System.out.println("Base Path:"+basePath);
//System.out.println(path.compareTo(basePath));
}
String className=f.getName();
try{
Class classPointer=Class.forName(packageName+className.substring(0,className.length()-6));
if(classPointer.isAnnotationPresent(TableName.class)){
System.out.println(f.getName());
System.out.println("Found Table Name annotation");
//now we will add this to the table mapping
TableName tn = (TableName)classPointer.getAnnotation(TableName.class);
Table tableObj=tableMap.get(tn.name());
if(tableObj==null){
System.out.println(tn.name()+"not found");
return;
}


Map<String,String> columnMapping=new HashMap<>();
Field[] fields=classPointer.getDeclaredFields();
for(Field field:fields){
field.setAccessible(true);
if(field.isAnnotationPresent(ColumnName.class)){

ColumnName cn=field.getAnnotation(ColumnName.class);
String cnName=cn.name().trim();
Attribute attribute=tableObj.getAttributeByName(cnName);
if(attribute==null){
System.out.println("column not found");
return;
}


columnMapping.put(field.getName(),cnName);

}//if of column name annotation present ends


if(field.isAnnotationPresent(PrimaryKey.class)){
PrimaryKey  cn=field.getAnnotation(PrimaryKey.class);
String cnName=cn.name().trim();
Attribute attribute=tableObj.getAttributeByName(cnName);
if(attribute==null){
System.out.println("column not found");
return;
}
columnMapping.put(field.getName(),cnName);
}//if annotaion primary presents end

if(field.isAnnotationPresent(ForeignKey.class)){
ForeignKey  cn=field.getAnnotation(ForeignKey.class);
String cnName=cn.name().trim();
Attribute attribute=tableObj.getAttributeByName(cnName);
if(attribute==null){
System.out.println("column not found");
return;
}

columnMapping.put(field.getName(),cnName);
}//if annotaion foreign presents end



}//for of fields loops ends






TableMapBean tmb=new TableMapBean();
tmb.setTableClassPointer(classPointer);
tmb.setTableObject(tableObj);
tmb.setClassPackage(packageName+className.substring(0,className.length()-6));
tmb.setColumnMapping(columnMapping);
tableMapping.put(className.substring(0,className.length()-6),tmb);
//mapped class name to the tmb bean
}
}catch(Exception e){
System.out.println(e);

}//catch block ends
}//inner (if ) file ends with ".class"
}
else{
if(f.isDirectory()) 
{ String newpath="";

newpath=path+File.separator+f.getName();
//System.out.println("[" + f.getName() + "]"); 
//System.out.println("new path:"+newpath);
			
// recursion for sub-directories 
recursivePrint(f.listFiles(),newpath,basePath); 
} //if f is directory() ends 


}//else block ends

}//for loop ends

}//recursivePrint() ends



private void loadTableClassDS(){
//we have to traverse the folder and check for table annotation
//if found tabe annotation then add it to the tableMapping ---->ds.
File maindir=new File(this.path);
if(maindir.exists() && maindir.isDirectory()){
File arr[]=maindir.listFiles();
recursivePrint(arr,path,path);
}//if ends
}




public void begin(){
try{
this.connection.setAutoCommit(false);
}catch(Exception e){
System.out.println(e);
}

}//begin() method

public TableMapBean getTableMapping(String tableName){

return this.tableMapping.get(tableName);
}
public void save(Object classObject){
try{

Class classPointer=classObject.getClass();
System.out.println("SAVE():"+classPointer.getName());
Annotation[] annotations=classPointer.getAnnotations();
Map<String,String> columnData=new HashMap<>();



for(Annotation anno : annotations){
if(TableName.class.isInstance(anno)){
System.out.println(anno);
String annotationString=anno.toString();
String name=annotationString.substring(annotationString.indexOf("=")+2,annotationString.length()-2);
System.out.println("table name: "+name);
if(tableMap.containsKey(name)==false){
System.out.println("Table not found");
return;
}
Table tableObj=tableMap.get(name);
System.out.println(tableObj.getAttributeCount());

Map<String,String> columnMapping=new HashMap<>();

Field[] fields=classPointer.getDeclaredFields();
for(Field field:fields){
field.setAccessible(true);
if(field.isAnnotationPresent(ColumnName.class)){

ColumnName cn=field.getAnnotation(ColumnName.class);
String cnName=cn.name().trim();
Attribute attribute=tableObj.getAttributeByName(cnName);
if(attribute==null){
System.out.println("column not found");
return;
}
String s=field.get(classObject).toString();
columnData.put(cnName,s);
columnMapping.put(field.getName(),cnName);

}//if of column name annotation present ends


if(field.isAnnotationPresent(PrimaryKey.class)){
PrimaryKey  cn=field.getAnnotation(PrimaryKey.class);
String cnName=cn.name().trim();
Attribute attribute=tableObj.getAttributeByName(cnName);
if(attribute==null){
System.out.println("column not found");
return;
}

Object valueField=field.get(classObject);
String s=valueField.toString();
columnData.put(cnName,s);
columnMapping.put(field.getName(),cnName);
}//if annotaion primary presents end

if(field.isAnnotationPresent(ForeignKey.class)){
ForeignKey cn=field.getAnnotation(ForeignKey.class);
String cnName=cn.name().trim();
Attribute attribute=tableObj.getAttributeByName(cnName);
if(attribute==null){
System.out.println("column not found");
return;
}

Object valueField=field.get(classObject);
String s=valueField.toString();
columnData.put(cnName,s);
columnMapping.put(field.getName(),cnName);
}//if annotaion foreign presents end



}//for of fields loops ends
if(this.connection==null) {
Class.forName("com.mysql.cj.jdbc.Driver");
this.connection=DriverManager.getConnection(this.connectionString,this.userName,this.password);
}
String statement=null;
boolean updateOp=false;
Update update = new Update(columnData,tableObj,this.connection);
statement=update.getStatement();
if(statement==null) updateOp=false;
else updateOp=true;
if(!updateOp){
Insert insertObject=new Insert(columnData,tableObj);

statement=insertObject.getStatement();
System.out.println("Statement");
System.out.println(statement);
}
if(statement==null){
System.out.println("statement is null");
return;
}
Statement statementObj=connection.createStatement();
statementObj.executeUpdate(statement);

}//if tablename ends

}//for  loop ends

}catch(Exception e){

System.out.println(e);
}


}//save() method ends

public void commit(){

try{
this.connection.commit();
}catch(Exception e){
System.out.println(e);
try{
this.connection.rollback();
}catch(Exception e1){
System.out.println(e1);
}//inner catch block
}//outer catch block

}//commit() ends


public Select select(String tableName) throws ORMException{
try{
tableName=tableName.trim();
System.out.println("select function()");
//System.out.println("tableMapping object:"+tableMapping);
if(this.tableMapping.containsKey(tableName)==false){
throw new ORMException(tableName+" does not exists");
}
TableMapBean tmb=(TableMapBean)this.tableMapping.get(tableName);
if(this.connection==null){
Class.forName("com.mysql.cj.jdbc.Driver");
this.connection=DriverManager.getConnection(this.connectionString,this.userName,this.password);
}

Select select =new Select(tmb.getSqlTableName(),this.connection,tmb.getColumnMapping(),tmb.getTableClassPointer(),tmb.getTableObject(),this);
return select;
}catch(Exception e){
throw new ORMException(e.getMessage());
}	
}//select method ends



}//class definition ends