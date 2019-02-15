package thogaKade.view;
import thogaKade.controller.*;
import thogaKade.model.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
public class AddCustomerForm extends JFrame{
	private JLabel titleLabel;
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel addressLabel;
	private JLabel salaryLabel;
	
	private JTextField idText;
	private JTextField nameText;
	private JTextField addressText;
	private JTextField salaryText;
	
	private JButton addButton;
	private JButton cancelButton;
	
	public AddCustomerForm(){
		setSize(600,400);
		setTitle("Add Customer Form");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		titleLabel=new JLabel("Add Customer Form");
		titleLabel.setFont(new Font("",1,25));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		add("North",titleLabel);

		JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));			
		addButton=new JButton("Add Customer");
		addButton.setFont(new Font("",1,16));
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String id=idText.getText();
				String name=nameText.getText();
				String address=addressText.getText();
				double salary=Double.parseDouble(salaryText.getText());
				Customer c1=new Customer(id,name,address,salary);
				try{
					int res=CustomerController.addCustomer(c1);
					if(res>0){
						JOptionPane.showMessageDialog(AddCustomerForm.this,"Added Success");
					}
				}catch(SQLException e){
					System.out.println(e); //e.toString()
				}catch(ClassNotFoundException e){
					System.out.println(e); //e.toString()
				} 
			}
		});
		buttonPanel.add(addButton);

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
}

