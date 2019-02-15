package thogaKade.controller;
import java.util.*;
import java.sql.*;
import thogaKade.model.*;
import thogaKade.db.*;
public class OrderController{
	public static boolean addNewOrder(Orders orders)throws ClassNotFoundException, SQLException{
		Connection conn=DBConnection.getDBConnection().getConnection();
		String SQL="Insert into Orders Values(?,?,?)";
		PreparedStatement stm=conn.prepareStatement(SQL);
		stm.setObject(1,orders.getId());
		stm.setObject(2,orders.getDate());
		stm.setObject(3,orders.getCustomerId());
		int res=stm.executeUpdate();
		if(res>0){
			boolean isAdded=OrderDetailsController.addOrderDetails(orders.getOrderDetailList());
			if(isAdded){
				boolean isUpdated=ItemController.updateStock(orders.getOrderDetailList());
				if(isUpdated){
					return true;
				}
			}
		}
		return false;
	}
}
