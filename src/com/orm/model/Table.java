package com.orm.model;
import java.sql.*;
import java.util.*;
public class Table {
String catalog=null;
String schema=null;
String type;
String name;
String primaryKey;
String foreignKey;
String self_referencing_column = null;
String ref_generation=null;
Map<String,Attribute> attributeMap;
public Table(){
catalog=null;
schema=null;
primaryKey=null;
foreignKey=null;
name="";
type="";
self_referencing_column=null;
ref_generation=null;
attributeMap=new HashMap<>();
}


public void setPrimaryKey(String colName){
this.primaryKey=colName;
}

public String getPrimaryKeyAttributeName(){
return this.primaryKey;
}

public void setForeignKey(String colName){
this.foreignKey=colName;

}

public String getForeignKeyAttributeName(){
return this.foreignKey;
}



public void setName(String name){
this.name=name;
}

public String getName(){
return this.name;
}
public void setType(String type){
this.type=type;
}

public String getType(){
return this.type;
}

public void setCatalog(String catalog){
this.catalog=catalog;
}

public String getCatalog(){
return this.catalog;
}


public void setSelfReferencingColumn(String column){
this.self_referencing_column=column;
}

public String getSelfReferencingColumn(){
return this.self_referencing_column;
}

public void setRefGeneration(String ref_gen){
this.ref_generation=ref_gen;
}

public String getRefGeneration(){
return this.ref_generation;
}


public void addAttribute(String key,Attribute attribute){
attributeMap.put(key,attribute);
}

public int getAttributeCount(){
return attributeMap.size();
}

public Attribute getAttributeByName(String key){
if(attributeMap.containsKey(key)==false) return null;
return attributeMap.get(key);
}

public Map<String,Attribute> getAttributeMap(){

return this.attributeMap;
}
public String createInsertStatement(){
String insert="insert into "+this.name+" VALUES(";

for(Attribute at:this.attributeMap.values()){
if(at.isAttributeAutoIncrement()==false){
insert=insert+"?"+",";
}
}
insert=insert.substring(0,insert.length()-1);
insert=insert+")";
return insert;
}

public String createSelectStatement(String[] requiredColumns){
String select="";
if(requiredColumns==null){
select="select * from "+this.name;
return select;
}

String x=requiredColumns[0];
select="select "+x;
for(int i=0;i<requiredColumns.length;i++){
x=requiredColumns[i];
x=x.trim();
select=select+","+"x";
}
select = select+" from "+this.name;

return select;
}

}//class ends