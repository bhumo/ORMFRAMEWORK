package com.orm.model;
public class Attribute{
String name;
String dataType;
int columnSize;
int bufferLength;
int decimalDigit;
String columnDefault;
int ordinalPosition;
boolean isNullable;
boolean isAutoIncrement;
boolean isGeneratedColumn;
int charOctetLength; //max bytes for column
public Attribute(){
name="";
dataType="";
columnSize=0;
bufferLength=0;
decimalDigit=0;
columnDefault=null;
charOctetLength=0;
ordinalPosition=1;
isNullable=false;
isAutoIncrement=false;
isGeneratedColumn=false;
}

public void setName(String name){
this.name=name;
}

public String getName() {
return this.name;
}

public void setDataType(String dataType) {
this.dataType=dataType;
}

public String getDataType(){
return this.dataType;
}

public void setColumnSize(int columnSize){
this.columnSize=columnSize;
}

public int getColumnSize(){
return this.columnSize;
}

public void setBufferLength(int bufferLength){
this.bufferLength=bufferLength;
}

public int getBufferLength(){
return this.bufferLength;
}

public void setDecimalDigit(int dd){
this.decimalDigit=dd;
}

public int getDecimalDigit(){
return this.decimalDigit;
}

public void setColumnDefault(String columnDefault){
this.columnDefault=columnDefault;
}

public String getColumnDefault(){
return this.columnDefault;
}

public void setCharOctetLength(int len){
this.charOctetLength=len;
}

public int getCharOctetLength(){
return this.charOctetLength;
}

public void setOrdinalPosition(int pos){
this.ordinalPosition=pos;
}

public int getOrginalPosition(){
return this.ordinalPosition;
}


public void setNullable(boolean isNullable){
this.isNullable=isNullable;
}

public boolean isAttributeNullable(){
return this.isNullable;
}

public void setAutoIncrement(boolean autoIncrement){
this.isAutoIncrement=autoIncrement;
}

public boolean isAttributeAutoIncrement(){
return this.isAutoIncrement;
}

public void setGeneratedColumn(boolean isGenerated){
this.isGeneratedColumn=isGenerated;
}

public boolean isAttributeGenerated(){
return this.isGeneratedColumn;
}


}//class ends