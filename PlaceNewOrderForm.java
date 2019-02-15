package thogaKade.view;
import thogaKade.controller.*;
import thogaKade.model.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.*;
import javax.swing.table.*;
import javax.swing.*;
import java.awt.event.*;
public class PlaceNewOrderForm extends javax.swing.JFrame {
	private double grandTotal;
    /**
     * Creates new form PlaceOrderForm
     */
    public PlaceNewOrderForm() {
        initComponents();
        loadCustomerIds();
        setSystemDate();
        loadItemCodes();
    }
    private void loadItemCodes(){
		try{ArrayList<String> itemCodes=ItemController.getAllItemCodes();
			for (int i = 0; i < itemCodes.size(); i++){
				itemCodeComboBox.addItem(itemCodes.get(i));
			}
		}catch(SQLException e){
			
		}catch(ClassNotFoundException e){
			
		}
	}
    private void setSystemDate(){
		Date d1=new Date(); //java.util.Date ->get Current date and time
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String formatDate=df.format(d1);
		orderDateText.setText(formatDate);
	}
	private void loadCustomerIds(){
		try{
			ArrayList<Customer>	custList=CustomerController.getAllCustomers();
			for (int i = 0; i < custList.size(); i++){
				Customer customer=(Customer)custList.get(i);
				customerIdComboBox.addItem(customer.getId());
			}
		}catch(SQLException e){
			System.out.println(e);
		}catch(ClassNotFoundException e){
			System.out.println(e);
		}
	}
	
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        customerIdComboBox = new javax.swing.JComboBox();
        customerIdComboBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent evt){
				String custId=(String)customerIdComboBox.getSelectedItem();
				try{
					Customer customer=CustomerController.searchCustomer(custId);
					customerNameText.setText(customer.getName());
				}catch(SQLException e){
					
				}catch(ClassNotFoundException e){
					
				}
			}
		});
        customerNameText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        orderIdText = new javax.swing.JTextField();
        orderDateText = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        itemCodeComboBox = new javax.swing.JComboBox();
		itemCodeComboBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent evt){
				String code=(String)itemCodeComboBox.getSelectedItem();
				try{
					Item item=ItemController.searchItem(code);
					itemDescriptionText.setText(item.getDescription());
					qtyOnHandText.setText(""+item.getQtyOnHand());
					itemPriceText.setText(""+item.getUnitPrice());
					qtyText.requestFocus();
				}catch(SQLException e){
					
				}catch(ClassNotFoundException e){
					
				}
			}	
		});	
        jLabel7 = new javax.swing.JLabel();
        itemDescriptionText = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        qtyText = new javax.swing.JTextField();
        qtyText.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ect){
				itemPriceText.requestFocus();
				itemPriceText.selectAll();
			}
		});
        jLabel9 = new javax.swing.JLabel();
        itemPriceText = new javax.swing.JTextField();
        itemPriceText.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				DefaultTableModel dtm=(DefaultTableModel)orderDetailTable.getModel();
				String code=(String)itemCodeComboBox.getSelectedItem();
				String description=itemDescriptionText.getText();
				int orderQty=Integer.parseInt(qtyText.getText());
				double unitPrice=Double.parseDouble(itemPriceText.getText());
				
				for(int i=0;i<dtm.getRowCount();i++){
					String itemCode=(String)dtm.getValueAt(i,0);
					if(itemCode.equals(code)){
						int qty=(int)dtm.getValueAt(i,2);
						double amountTemp=(double)dtm.getValueAt(i,4);
						orderQty+=qty;
						grandTotal-=amountTemp;
						totalAmountText.setText(""+grandTotal);
						dtm.removeRow(i);
					}
				}
				double amount=orderQty*unitPrice;
				grandTotal+=amount;
				totalAmountText.setText(""+grandTotal);
				Object[] rowData={code, description,orderQty,unitPrice,amount};
				dtm.addRow(rowData);
			}
		});
        jLabel10 = new javax.swing.JLabel();
        qtyOnHandText = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderDetailTable = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        totalAmountText = new javax.swing.JTextField();
        removeItemButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String orderId=orderIdText.getText();
				String customerId=(String)customerIdComboBox.getSelectedItem(); 
				String orderDate=orderDateText.getText();
				
				DefaultTableModel dtm=(DefaultTableModel)orderDetailTable.getModel();
				ArrayList<OrderDetail>orderDetailList=new ArrayList<>();
				for(int i=0;i<dtm.getRowCount();i++){
					String itemCode=(String)dtm.getValueAt(i,0);
					int qty=(int)dtm.getValueAt(i,2);
					double unitPrice=(double)dtm.getValueAt(i,3);
					OrderDetail od=new OrderDetail(orderId,itemCode,qty,unitPrice);	
					orderDetailList.add(od);
				}
				Orders order=new Orders(orderId,orderDate,customerId,orderDetailList);
				try{
					boolean isAdded=OrderController.addNewOrder(order);
					if(isAdded){
						JOptionPane.showMessageDialog(PlaceNewOrderForm.this,"Added Succes");
					}
				}catch(SQLException e){
					System.out.println(e);
				}catch(ClassNotFoundException e){
					System.out.println(e);
				}
			}
		});
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Droid Sans", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Place New Order");

        jLabel2.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        jLabel2.setText("Customer Id");

        jLabel3.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        jLabel3.setText("Customer Name");

        customerIdComboBox.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        customerIdComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                customerIdComboBoxItemStateChanged(evt);
            }
        });

        customerNameText.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        jLabel4.setText("Order ID");

        jLabel5.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        jLabel5.setText("Order Date");

        orderIdText.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N

        orderDateText.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        jLabel6.setText("Item Code");

        itemCodeComboBox.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        jLabel7.setText("Description");

        itemDescriptionText.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        jLabel8.setText("Qty");

        qtyText.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        jLabel9.setText("Unit Price");

        itemPriceText.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        jLabel10.setText("Qty On Hand");

        qtyOnHandText.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N

        orderDetailTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Code", "Description", "Qty", "Unit Price", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(orderDetailTable);

        jLabel11.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        jLabel11.setText("Total Amount");

        totalAmountText.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N

        removeItemButton.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        removeItemButton.setText("Remove Item");
		removeItemButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				DefaultTableModel dtm=(DefaultTableModel)orderDetailTable.getModel();
				double amount=(double)dtm.getValueAt(orderDetailTable.getSelectedRow(),4);
				dtm.removeRow(orderDetailTable.getSelectedRow());
				grandTotal-=amount;
				totalAmountText.setText(""+grandTotal);
			}
		});

        saveButton.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        saveButton.setText("Save & Print");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalAmountText, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(customerNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(customerIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(96, 96, 96)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(orderIdText, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                    .addComponent(orderDateText)))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(itemCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(itemDescriptionText, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(qtyText, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(itemPriceText))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(61, 61, 61)
                                        .addComponent(jLabel8)
                                        .addGap(51, 51, 51)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                    .addComponent(qtyOnHandText))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(removeItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(customerIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(orderIdText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(customerNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(orderDateText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(itemCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(itemDescriptionText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(qtyText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(itemPriceText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(qtyOnHandText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(totalAmountText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(removeItemButton)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void customerIdComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_customerIdComboBoxItemStateChanged

    }//GEN-LAST:event_customerIdComboBoxItemStateChanged


    private javax.swing.JComboBox customerIdComboBox;
    private javax.swing.JTextField customerNameText;
    private javax.swing.JComboBox itemCodeComboBox;
    private javax.swing.JTextField itemDescriptionText;
    private javax.swing.JTextField itemPriceText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField orderDateText;
    private javax.swing.JTable orderDetailTable;
    private javax.swing.JTextField orderIdText;
    private javax.swing.JTextField qtyOnHandText;
    private javax.swing.JTextField qtyText;
    private javax.swing.JButton removeItemButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField totalAmountText;
}

