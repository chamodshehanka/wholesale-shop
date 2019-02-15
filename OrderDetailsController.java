package thogaKade.controller;
import java.util.*;
import java.sql.*;
import thogaKade.model.*;
import thogaKade.db.*;
public class OrderDetailsController{
	public static boolean addOrderDetails(ArrayList<OrderDetail> orderDetailList)throws ClassNotFoundException, SQLException{
		for(OrderDetail ob : orderDetailList){
			boolean isUpdated=OrderDetailsController.addOrderDetails(ob);
			if(!isUpdated){
				return false;
			}
		}
		return true;
	}
	public static boolean addOrderDetails(OrderDetail orderDetail)throws ClassNotFoundException, SQLException{
		Connection conn=DBConnection.getDBConnection().getConnection();
		String SQL="Insert into OrderDetail Values(?,?,?,?)";
		PreparedStatement stm=conn.prepareStatement(SQL);
		stm.setObject(1,orderDetail.getOrderId());
		stm.setObject(2,orderDetail.getItemCode());
		stm.setObject(3,orderDetail.getQty());
		stm.setObject(4,orderDetail.getUnitPrice());
		return stm.executeUpdate()>0;
	}
}
