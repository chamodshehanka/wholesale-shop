package thogaKade.controller;

import thogaKade.db.DBConnection;
import thogaKade.model.Item;
import thogaKade.model.OrderDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemController {
    public static ArrayList<String>getAllItemCodes() throws ClassNotFoundException, SQLException{
        Connection connection=DBConnection.getDBConnection().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery("Select * from Item");
        ArrayList <String>itemCodes=new ArrayList<>();
        while(rst.next()){
            itemCodes.add(rst.getString("code"));
        }
        return itemCodes;
    }
    
    public static Item searchItem(String code)throws ClassNotFoundException,SQLException{
        Connection connection=DBConnection.getDBConnection().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery("Select * from Item where code='"+code+"'");
        if(rst.next()){
            Item item=new Item(code, rst.getString("description"),rst.getDouble("unitPrice"),rst.getInt("qtyOnHand"));
            return item;
        }
        return null;
    }
    public static boolean updateStock(ArrayList<OrderDetail> orderDetailList)throws ClassNotFoundException, SQLException{
		for(OrderDetail ob: orderDetailList){
			boolean isUpdated=ItemController.updateStock(ob);
			if(!isUpdated){
				return false;
			}
		}
		return true;
	}
	public static boolean updateStock(OrderDetail orderDetail)throws ClassNotFoundException, SQLException{
        Connection connection=DBConnection.getDBConnection().getConnection();
		String SQL="Update Item set qtyOnHand=qtyOnHand-? where code=?";
        PreparedStatement stm=connection.prepareStatement(SQL);
        stm.setObject(1, orderDetail.getQty());
        stm.setObject(2, orderDetail.getItemCode());
		return stm.executeUpdate()>0;		
	}	
}
