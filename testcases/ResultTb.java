import com.orm.annotations.*;
@TableName(name="result")
public class ResultTb{
@ForeignKey(name="studentRollNumber")
private int rollNumber;

@ColumnName(name="marks")
private int marks;

@ColumnName(name="total")
private int total;

public void setRollNumber(int rl){
this.rollNumber=rl;
}

public int getRollNumber(){
return rollNumber;
}


public void setMarks(int mrk){
this.marks=mrk;
}

public int getMarks(){
return this.marks;
}


public void setTotal(int total){
this.total=total;
}

public int setTotal(){
return this.total;
}


}