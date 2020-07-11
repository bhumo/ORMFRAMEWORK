import java.sql.*;
import java.util.List;
import java.lang.annotation.*;
import com.orm.query.*;
import java.io.*;
public class Test1{

public static void main(String gg[]){
try{
StudentClass sd=new StudentClass();

sd.setRollNumber(2);
sd.setFirstNAme("Annanya");
sd.setLastName("Gupta");
sd.setDateOfBirth(new Date(1998,24,02));
sd.setGender("F");
String path="C:"+File.separator+"ORMFramework";
TMDB tmdb=new TMDB(path,"root","Life@1234","school");
tmdb.begin();
tmdb.save(sd);
List<StudentClass> list;
list=(List<StudentClass>)tmdb.select("StudentClass").query();
System.out.println(list.size());
list=(List<StudentClass>)tmdb.select("StudentClass").orderBy("rollNumber").query();
System.out.println(list.size());
list=(List<StudentClass>)tmdb.select("StudentClass").orderBy("firstNAme").orderBy("dateOfBirth").query();
System.out.println(list.get(1).getRollNumber());
list=(List<StudentClass>)tmdb.select("StudentClass").orderBy("lastName").ascending().orderBy("gender").orderBy("firstNAme").descending().query();
System.out.println(list.get(1).getRollNumber());
list=(List<StudentClass>)tmdb.select("StudentClass").where("gender").eq("f").query();
System.out.println(list.get(1).getRollNumber());
list=(List<StudentClass>)tmdb.select("StudentClass").where("gender").eq("f").orderBy("lastName").descending().query();
System.out.println(list.get(1).getRollNumber());
list=(List<StudentClass>)tmdb.select("StudentClass").where("rollNumber").eq(1).query();
System.out.println(list.get(0).getRollNumber());
list=(List<StudentClass>)tmdb.select("StudentClass").where("rollNumber").gt(1).query();
System.out.println(list.get(0).getRollNumber());
list=(List<StudentClass>)tmdb.select("StudentClass").where("rollNumber").ne(1).query();
System.out.println(list.get(0).getRollNumber());
list=(List<StudentClass>)tmdb.select("StudentClass").where("firstNAme").gt("bhumika").orderBy("lastName").descending().query();
System.out.println(list.get(1).getRollNumber());
list=(List<StudentClass>)tmdb.select("StudentClass").where("firstNAme").lt("bhumika").orderBy("lastName").descending().query();
System.out.println("Size: "+list.size());
list=(List<StudentClass>)tmdb.select("StudentClass").where("firstNAme").le("bhumika").orderBy("lastName").descending().query();
System.out.println("Size: "+list.size());
list=(List<StudentClass>)tmdb.select("StudentClass").where("firstNAme").ge("bhumika").orderBy("lastName").descending().query();
System.out.println("Size: "+list.size());
list=(List<StudentClass>)tmdb.select("StudentClass").where("rollNumber").gt(3).or("rollNumber").eq(1).query();
System.out.println("Size: "+list.size());
list=(List<StudentClass>)tmdb.select("StudentClass").where("rollNumber").in(1,4).query();
System.out.println("Size: "+list.size());
list=(List<StudentClass>)tmdb.select("StudentClass").innerJoin("ResultTb","rollNumber","rollNumber").query();
System.out.println("Size: "+list.size());
list=(List<StudentClass>)tmdb.select("StudentClass").groupBy("lastName").query();
System.out.println("Size: "+list.size());
tmdb.commit();
}catch(Exception e){
System.out.println(e);
}
}//main() ends

}//class ends