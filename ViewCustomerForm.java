package thogaKade.view;
import thogaKade.controller.*;
import thogaKade.model.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;
public class ViewCustomerForm extends JFrame{
	private JLabel titleLabel;
	private JButton reloadButton;
	private JTable customerTable;
	
	public ViewCustomerForm(){
		setSize(400,300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
		
		titleLabel=new JLabel("Customer List");
		titleLabel.setFont(new Font("",1,25));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		add("North",titleLabel);
		
		reloadButton=new JButton("Reload");
		reloadButton.setFont(new Font("",1,20));
		reloadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent et){
				try{
					ArrayList<Customer> customerList=CustomerController.getAllCustomers();
					DefaultTableModel dtm=(DefaultTableModel)customerTable.getModel();
					dtm.setRowCount(0);
					for(Customer c1 : customerList){
						Object[] rowData={c1.getId(),c1.getName(),c1.getAddress(),c1.getSalary()};
						dtm.addRow(rowData);
					}
				}catch(ClassNotFoundException e){
					
				}catch(SQLException e){
					
				}
			}
		});
		
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
		buttonPanel.add(reloadButton);
		add("South",buttonPanel);
		
		String[] columnNames={"Customer ID", new String("Name"),new String("Address"),"Salary"};
		DefaultTableModel dtm=new DefaultTableModel(columnNames,0);
		customerTable=new JTable(dtm);
		JScrollPane tablePane=new JScrollPane(customerTable);
		
		add("Center",tablePane);
	} 
}

