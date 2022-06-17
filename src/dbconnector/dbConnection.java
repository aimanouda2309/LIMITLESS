
package dbconnector;

import java.sql.Connection;

import java.sql.Array;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import main.Maze;
import util.PatientPerMaterial;
import util.Payment;
import util.User;
public class dbConnection {

	public static final String url = "jdbc:mysql://bxxbekzzmudnqe34wwoz-mysql.services.clever-cloud.com:3306/bxxbekzzmudnqe34wwoz";
    public static final String user = "uodtteuxdpbuuxst";
    public static final String pass = "8J63UiQwmqfPuYlSPcfr";
    
    private static Connection conn;

    /*
    This Class is Responsible for DB Functions
     */
    public static Connection getConnection() {
    	
        if(conn==null)
            try {
                Class.forName("com.mysql.jdbc.Driver");
//              conn = DriverManager.getConnection(url, user, pass);
                conn = DriverManager.getConnection(url +  "?user="+user+"&password="+pass+"&characterEncoding=utf8&useSSL=false&useUnicode=true");
            } catch ( Exception throwables) {
                throwables.printStackTrace();
            }
        
        return conn;
    }
    public static String sign_up(String username, String password, String firstname, String lastname, String email){
    	
    	  String result = "Database Connection Successful\n";
    	  Statement st = null;
    	  String res;
    	 // System.out.println(username + " "+ password + " "+firstname + " "+lastname + " "+email + " " );
          try {
              st = getConnection().createStatement();
              ResultSet rs = st.executeQuery("select distinct * from users WHERE username='"+username+"'");
              ResultSetMetaData rsmd = rs.getMetaData();
          if (rs.next()) {
              res = "Account is Already in our system";
          }
          else{
              st.executeUpdate("INSERT INTO users (UserName, FirstName, LastName, Email, Password,numofgenerate)  VALUES('"+username+"', '"+firstname+"','"+lastname+"','"+email+"','"+password+"','"+0+"')");

              res = "Account Registred Successfully, You can login now...";
          }
          st.close();

          return res;
          } catch (Exception throwables) {
              throwables.printStackTrace();
              return "something wrong";
          }
    }
    public static ArrayList<PatientPerMaterial> SaveExcelPatient() {
    	try {
    		ArrayList<PatientPerMaterial> arr=new ArrayList<>();
            Statement st = getConnection().createStatement();
            ResultSet rs;
            
                	rs = st.executeQuery("select * from patient");
            while (rs.next()) {
                    
            	PatientPerMaterial patient = new PatientPerMaterial(rs.getString(1), rs.getString(2),rs.getString(3), rs.getFloat(4), rs.getString(5));
                    //User.currentUser = usr;
                    arr.add(patient);
                    
            }
            st.close();
            return arr;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Payment> SaveExcel() {
    	try {
    		ArrayList<Payment> arr=new ArrayList<>();
            Statement st = getConnection().createStatement();
            ResultSet rs;
            
                	rs = st.executeQuery("select * from payments");
            while (rs.next()) {
                    
                Payment payment = new Payment(rs.getString(1), rs.getString(2),rs.getString(3), rs.getTimestamp(4), rs.getString(5),rs.getFloat(6),rs.getFloat(7),rs.getFloat(8),rs.getFloat(9),Float.toString(rs.getFloat(9)-rs.getFloat(8)),rs.getString(10),rs.getString(11));
                    //User.currentUser = usr;
                    arr.add(payment);
                    
            }
            st.close();
            return arr;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Payment> TakePayments(LocalDateTime fromdate, String ID,String EmployeeID) {
    	try {
    		ArrayList<Payment> arr=new ArrayList<>();
            Statement st = getConnection().createStatement();
            ResultSet rs;
            
            if(ID!=null) {
            	if(EmployeeID!=null)
            	rs = st.executeQuery("select * from payments WHERE ID='"+ID+"' AND DATE>='"+fromdate+"' AND Teacher='"+EmployeeID+"'");
            	else
                	rs = st.executeQuery("select * from payments WHERE ID='"+ID+"' AND DATE>='"+fromdate+"'");
            }
            else {
            	if(EmployeeID!=null)
                	rs = st.executeQuery("select * from payments WHERE DATE>='"+fromdate+"' AND Teacher='"+EmployeeID+"'");
                else
                	rs = st.executeQuery("select * from payments WHERE DATE>='"+fromdate+"'");	
            }
            while (rs.next()) {
                    
                Payment payment = new Payment(rs.getString(1), rs.getString(2),rs.getString(3), rs.getTimestamp(4), rs.getString(5),rs.getFloat(6),rs.getFloat(7),rs.getFloat(8),rs.getFloat(9),Float.toString(rs.getFloat(9)-rs.getFloat(8)),rs.getString(10),rs.getString(11));
                    //User.currentUser = usr;
                    arr.add(payment);
                    
            }
            st.close();
            return arr;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Payment> TakePayments(String ID,String EmployeeID) {
    	try {
    		ArrayList<Payment> arr=new ArrayList<>();
            Statement st = getConnection().createStatement();
            ResultSet rs;
            if(ID!=null) {
            	if(EmployeeID!=null)
            		rs = st.executeQuery("select * from payments WHERE ID='"+ID+"' AND Teacher='"+EmployeeID+"'");
            	else
                	rs = st.executeQuery("select * from payments WHERE ID='"+ID+"'");      	
            }
            else {
            	if(EmployeeID!=null)
                	rs = st.executeQuery("select * from payments WHERE Teacher='"+EmployeeID+"'");
                else
                    rs = st.executeQuery("select * from payments");	
            }
            while (rs.next()) {
                Payment payment = new Payment(rs.getString(1), rs.getString(2),rs.getString(3), rs.getTimestamp(4), rs.getString(5),rs.getFloat(6),rs.getFloat(7),rs.getFloat(8),rs.getFloat(9),Float.toString(rs.getFloat(9)-rs.getFloat(8)),rs.getString(10),rs.getString(11));
                    //User.currentUser = usr;
                    arr.add(payment);
                    
            }
            st.close();
            return arr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Payment> TakePayments() {
    	try {
    		ArrayList<Payment> arr=new ArrayList<>();
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from payments");
      
            while (rs.next()) {
                    Payment payment = new Payment(rs.getString(1), rs.getString(2),rs.getString(3), rs.getTimestamp(4), rs.getString(5),rs.getFloat(6),rs.getFloat(7),rs.getFloat(8),rs.getFloat(9),Float.toString(rs.getFloat(9)-rs.getFloat(8)),rs.getString(10),rs.getString(11));
                    //User.currentUser = usr;
                    arr.add(payment);
                    
            }
            st.close();
            return arr;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean DeletePayment(Payment payment) {
   	 Statement st = null;
        try {
        	  st = getConnection().createStatement();
              st.executeUpdate("DELETE FROM payments WHERE ID='"+payment.getID()+"' AND DATE='"+payment.getDate()+"' AND FirstName='"+payment.getFirstName()+"' AND LastName='"+payment.getLastName()+"' AND Material='"+payment.getMaterials()+"' AND Teacher='"+payment.getTeacher()+"' AND Type='"+payment.getType()+"'");
              st.close();
              return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
   }
    public static String SavePrice(String ID, String Material,String FirstName,String LastName,float hours,float minutes,float price,float paid,String Teacher, String type,LocalDate date) {
    	 Statement st = null;
    	// System.out.println( ID+ Material+" "+ FirstName+" "+ LastName+" "+ hours+" "+ minutes+" "+ price+" "+ paid);
         try {
             st = getConnection().createStatement();
             ResultSet rs = st.executeQuery("select * from payments WHERE ID='"+ID+"' AND DATE='"+date+"' AND FirstName='"+FirstName+"' AND LastName='"+LastName+"' AND Material='"+Material+"' AND Hours='"+hours+"' AND Minutes='"+minutes+"' AND Price='"+price+"' AND Pay='"+paid+"' AND Teacher='"+Teacher+"' AND Type='"+type+"'");
             ResultSetMetaData rsmd = rs.getMetaData();
             if (rs.next()) {
             return "Details is Already in our system";
             }
             st.executeUpdate("INSERT INTO payments (ID, FirstName, LastName, DATE, Material, Teacher, Hours, Minutes, Price, Pay, Type)  VALUES('"+ID+"', '"+FirstName+"','"+LastName+"','"+date+"','"+Material+"','"+Teacher+"','"+hours+"','"+minutes+"','"+price+"','"+paid+"','"+type+"')");
             st.close();
             return "successful";
         } catch (Exception throwables) {
             throwables.printStackTrace();
             return "check your connection";
         }
    }
    public static boolean addmotordiagnosisimg(){
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("INSERT INTO GenerateMaze (ID)  VALUES('"+6+"')");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public static boolean validatePassword(String username, String password){
        try {

            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select distinct * from users WHERE UserName='"+username+"' AND Password='"+password+"'");
      
            if (rs.next()) {
                    //User usr = new User(rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5),rs.getInt(6));
                    //User.currentUser = usr;
                  
                    return true;
            }
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean SaveMaterial(PatientPerMaterial patient){
        try {

            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from patient WHERE ID='"+patient.getID()+"' AND Material='"+patient.getMaterial()+"'");
      
            if (rs.next()) 
            	return false;
            st.executeUpdate("INSERT INTO patient (ID, FirstName, LastName, Price, Material)  VALUES('"+patient.getID()+"', '"+patient.getFirstName()+"','"+patient.getLastName()+"','"+patient.getPrice()+"','"+patient.getMaterial()+"')");
                    //User usr = new User(rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5),rs.getInt(6));
                    //User.currentUser = usr;      
            st.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean DeleteMaterial(PatientPerMaterial patient) {
      	 Statement st = null;
           try {
               st = getConnection().createStatement();
               st.executeUpdate("DELETE FROM patient WHERE ID='"+patient.getID()+"' AND Material='"+patient.getMaterial()+"' AND FirstName='"+patient.getFirstName()+"' AND LastName='"+patient.getLastName()+"'");
               st.close();
               return true;
           } catch (Exception throwables) {
               throwables.printStackTrace();
               return false;
           }
      }
    
    public static float getPrice(String ID, String material) {
    	//System.out.println(ID+material);
    	float price=-1;
    	try {
        Statement st = getConnection().createStatement();
        ResultSet rs;     
			rs = st.executeQuery("select Price from patient WHERE ID='"+ID+"' AND Material='"+material+"'");
			if(rs.next()) {
				price=rs.getFloat(1);	
				st.close();
				return price;
			}           
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return price;
		}
    	return price;
    }
    public static ArrayList<String> getmaterials() {
    	ArrayList<String> arr=new ArrayList<>();
    	try {
        Statement st = getConnection().createStatement();
        ResultSet rs;     
			rs = st.executeQuery("select Subject from materials");
			while(rs.next()) 
				arr.add(rs.getString(1));
			st.close();
            return arr;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    public static ArrayList<PatientPerMaterial> TakeMaterialPerPatient(String ID) {
    	try {
    		ArrayList<PatientPerMaterial> arr=new ArrayList<>();
            Statement st = getConnection().createStatement();
            ResultSet rs;
            if(ID.equals(""))
            rs = st.executeQuery("select * from patient");
            else
                rs = st.executeQuery("select * from patient WHERE ID='"+ID+"'");	
            while (rs.next()) {
            		PatientPerMaterial material = new PatientPerMaterial(rs.getString(1), rs.getString(2),rs.getString(3), rs.getFloat(4),rs.getString(5));
                    //User.currentUser = usr;
                    arr.add(material);
                    
            }
            st.close();
            return arr;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
   
}