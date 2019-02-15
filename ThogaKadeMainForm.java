package thogaKade.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ThogaKadeMainForm extends JFrame{
	private JButton searchCustomerButton;
	private JButton addCustomerButton;
	private JButton viewCustomerButton;
	private JButton updateCustomerButton;
	private JButton deleteCustomerButton;
	private JButton placeOrderButton;
	private JLabel titleLabel;
	public ThogaKadeMainForm(){
		setSize(400,400);
		setTitle("Search Customer Form");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		titleLabel=new JLabel("Thoga Kade Main Form");
		titleLabel.setFont(new Font("",1,25));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		add("North",titleLabel);
		
		setLayout(new FlowLayout());
		searchCustomerButton=new JButton("Search Customer");
		searchCustomerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ect){
				new SearchCustomerForm().setVisible(true);
			}
		});
		searchCustomerButton.setFont(new Font("",1,20));
		add(searchCustomerButton);
		addCustomerButton =new JButton("Add Custoemr");
		addCustomerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ect){
				new AddCustomerForm().setVisible(true);
			}
		});
		addCustomerButton.setFont(new Font("",1,20));
		add(addCustomerButton);
		
		updateCustomerButton=new JButton("Update Customer");
		updateCustomerButton.setFont(new Font("",1,20));
		updateCustomerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					new UpdateCustomerForm().setVisible(true);
			}
		});
		add(updateCustomerButton);
		
		deleteCustomerButton=new JButton("Delete Customer");
		deleteCustomerButton.setFont(new Font("",1,20));
		deleteCustomerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					new DeleteCustomerForm().setVisible(true);
			}
		});		
		add(deleteCustomerButton);
		
		viewCustomerButton=new JButton("View Customer List");
		viewCustomerButton.setFont(new Font("",1,20));
		viewCustomerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					new ViewCustomerForm().setVisible(true);
			}
		});		
		add(viewCustomerButton);
		
		placeOrderButton=new JButton("Place New Order");
		placeOrderButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					new PlaceNewOrderForm().setVisible(true);
			}
		});
		placeOrderButton.setFont(new Font("",1,20));
		add(placeOrderButton);
	}
}

