package thogaKade.view;

import thogaKade.model.*;
import thogaKade.controller.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
public class DeleteCustomerForm extends JFrame{
	private JLabel titleLabel;
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel addressLabel;
	private JLabel salaryLabel;
	
	private JTextField idText;
	private JTextField nameText;
	private JTextField addressText;
	private JTextField salaryText;
	
	private JButton deleteButton;
	private JButton cancelButton;
	
	public DeleteCustomerForm(){
		setSize(600,400);
		setTitle("Delete Customer Form");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		titleLabel=new JLabel("Delete Customer Form");
		titleLabel.setFont(new Font("",1,25));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		add("North",titleLabel);

		JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));			
		deleteButton =new JButton("Delete Customer");
		deleteButton.setFont(new Font("",1,16));
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String id=idText.getText();
				try{
					int res=CustomerController.deleteCustomer(id);
					if(res>0){
						JOptionPane.showMessageDialog(DeleteCustomerForm.this,"Deleted..");
					}else{
						JOptionPane.showMessageDialog(DeleteCustomerForm.this,"Delete fail..");
					}
				}catch(SQLException e){
					System.out.println(e);
				}catch(ClassNotFoundException e){
					System.out.println(e);
				}
				
			}
		});
		buttonPanel.add(deleteButton);

		cancelButton=new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ect){
				dispose();
			}
		});
		cancelButton.setFont(new Font("",1,16));
		buttonPanel.add(cancelButton);		
		
		add("South",buttonPanel);
		
		//
		JPanel labelPanel=new JPanel(new GridLayout(4,1));

		idLabel=new JLabel("Customer ID");
		idLabel.setFont(new Font("",1,16));
		labelPanel.add(idLabel);
		nameLabel=new JLabel("Name");
		nameLabel.setFont(new Font("",1,16));
		labelPanel.add(nameLabel);
		addressLabel=new JLabel("Address");
		addressLabel.setFont(new Font("",1,16));
		labelPanel.add(addressLabel);				
		salaryLabel=new JLabel("Salary");
		salaryLabel.setFont(new Font("",1,16));
		labelPanel.add(salaryLabel);
		add("West",labelPanel);
		
		JPanel textPanel=new JPanel(new GridLayout(4,1));
		idText=new JTextField(8);
		idText.setFont(new Font("",1,16));
		JPanel idTextPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		idTextPanel.add(idText); 
		idText.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				searchButtonAction(evt);
			}
		});
		textPanel.add(idTextPanel);
		
		nameText=new JTextField(25);
		nameText.setFont(new Font("",1,16));
		JPanel nameTextPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		nameTextPanel.add(nameText);
		textPanel.add(nameTextPanel);		

		addressText=new JTextField(30);
		addressText.setFont(new Font("",1,16));
		JPanel addressTextPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));		
		addressTextPanel.add(addressText);
		textPanel.add(addressTextPanel);		

		salaryText=new JTextField(12);
		salaryText.setFont(new Font("",1,16));
		JPanel salaryTextPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));		
		salaryTextPanel.add(salaryText);
		textPanel.add(salaryTextPanel);		
		
		add("East",textPanel);
		
		setResizable(false);		
		pack();
	}	
	private void searchButtonAction(ActionEvent evt){
		String id=idText.getText();
		try{
			Customer c1=CustomerController.searchCustomer(id);
			if(c1!=null){
				nameText.setText(c1.getName());
				addressText.setText(c1.getAddress());
				salaryText.setText(Double.toString(c1.getSalary()));
			}else{
				JOptionPane.showMessageDialog(null,"Customer not found...");
			}
		}catch(SQLException e){
			System.out.println(e);
		}catch(ClassNotFoundException e){
			System.out.println(e);
		}
	}
}

