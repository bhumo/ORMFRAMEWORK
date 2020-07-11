import com.orm.annotations.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.sql.*;
@TableName(name="studenttb")
public class StudentClass{
@PrimaryKey(name="rollNumber")
private int rollNumber;
@ColumnName(name="gender")
private String gender;

@ColumnName(name="firstNAme")
private String firstNAme;

@ColumnName(name="lastName")
String lastName;

@ColumnName(name="dateOfBirth")
Date dateOfBirth;


public StudentClass(){
this.rollNumber=0;
this.firstNAme="";
this.lastName="";
this.dateOfBirth=null;
this.gender="";
}

public void setRollNumber(int rl){
this.rollNumber=rl;
}

public void setGender(String gn) {
this.gender=gn;
}

public void setFirstNAme(String fn){
this.firstNAme=fn;
}


public void setLastName(String ln){
this.lastName=ln;
}


public void setDateOfBirth(Date date){
this.dateOfBirth=date;
}


//@PrimaryKey(name="rollNumber")
public int getRollNumber(){
return this.rollNumber;
}
//@ColumnName(name="firstNAme")
public String getFirstNAme(){
return this.firstNAme;
}

//@ColumnName(name="lastName")
public String getLastName(){
return this.lastName;
}

//@ColumnName(name="dateOfBirth")
public Date getDateOfBirth(){
return this.dateOfBirth;
}

//@ColumnName(name="gender")
public String getGender() {
return this.gender;
}
}