package thogaKade.controller;
import thogaKade.db.*;
import thogaKade.model.*;
import java.sql.*;
import java.util.*;
public class CustomerController{
	public static int addCustomer(Customer c1)throws ClassNotFoundException,SQLException {
		String SQL="Insert into Customer values(?,?,?,?)";
		Connection conn=DBConnection.getDBConnection().getConnection();
		PreparedStatement stm=conn.prepareStatement(SQL);
		stm.setObject(1,c1.getId());
		stm.setObject(2,c1.getName());
		stm.setObject(3,c1.getAddress());
		stm.setObject(4,c1.getSalary());
		int res=stm.executeUpdate();
		return res;
	}
	public static int updateCustomer(Customer c1)throws ClassNotFoundException,SQLException {
		String SQL="Update Customer set name=?, address=?, salary=? where id=?";
		
		Connection conn=DBConnection.getDBConnection().getConnection();
		PreparedStatement stm=conn.prepareStatement(SQL);
		stm.setObject(1,c1.getName()); 
		stm.setObject(2,c1.getAddress()); 
		stm.setObject(3,c1.getSalary()); 
		stm.setObject(4,c1.getId()); 
		
		int res=stm.executeUpdate();
		return res;
	}	
	public static int deleteCustomer(String id)throws ClassNotFoundException,SQLException {
		String SQL="Delete From Customer where id='"+id+"'";
		Connection conn=DBConnection.getDBConnection().getConnection();
		Statement stm=conn.createStatement();
		int res=stm.executeUpdate(SQL);
		return res;
	}		
	public static Customer searchCustomer(String id)throws SQLException,ClassNotFoundException{
		String SQL="Select * From Customer where id='"+id+"'";	
		Connection conn=DBConnection.getDBConnection().getConnection();
		Statement stm=conn.createStatement();
		ResultSet rst=stm.executeQuery(SQL);
		if(rst.next()){
			Customer c1=new Customer(rst.getString("id"),rst.getString("name"),rst.getString("address"),rst.getDouble("salary"));
			return c1;
		}else{
			return null;
		}
	}
	public static ArrayList<Customer> getAllCustomers()throws ClassNotFoundException,SQLException{
		String SQL="Select * From Customer";
		Connection conn=DBConnection.getDBConnection().getConnection();
		Statement stm=conn.createStatement();
		ResultSet rst=stm.executeQuery(SQL);
		ArrayList<Customer>customerList=new ArrayList<>();
		while(rst.next()){
			Customer c1=new Customer(rst.getString("id"),rst.getString("name"),rst.getString("address"),rst.getDouble("salary"));
			customerList.add(c1);
		}
		return customerList;
	}
}

