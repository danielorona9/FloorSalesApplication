/***********************************************
 * Program Name: SummaryGui.java
 * Programmer's Name: Daniel Orona
 * Program Description: This program displays all the information inputed for the customer and floor calculations and displayed in a formatted display.
 * 						There are two events one to submit or insert data into the database and the other to edit or close the window.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.sql.*;

public class SummaryGUI extends JFrame
{
////////////////////////class variables////////////////////////////////

    //purchase summary tab
    private JLabel labelSummaryTitle, labelFloorArea;
    //reuse some labels and textfields from customer tab.
    private JButton btnSubmitOrder,btnEditOrder;
    private String output;

    //Order History tab
    private JLabel labelHistoryTitle;
    private JButton btnRefreshResults;

    //variables
    String custName;
    String custAddress;
    String floorType ;
    String floorArea;
    String floorCost;
    Connection conn = null;
    //**************************************************
//******************CONSTRUCTOR METHOD**************
//**************************************************
    public SummaryGUI(String custName, String custAddress, String floorType, String floorArea, String floorCost)
    {

//title of the application
        super("Floor Purchase System");

        this.custName = custName;
        this.custAddress = custAddress;
        this.floorType = floorType;
        this.floorArea = floorArea;
        this.floorCost = floorCost;
//**************************************************
//**************TAB PURCHASE SUMMARY****************
//**************************************************
        Box box = Box.createHorizontalBox();

        labelSummaryTitle = new JLabel("Order Summary");
        btnSubmitOrder = new JButton("Submit Order");
        btnEditOrder = new JButton("Edit Order");

        output = "Customer Name: " + this.custName + "\n\n";
        output+= "Address: " + this.custAddress + "\n\n";
        output+= "Floor Type: " + this.floorType + "\n\n";
        output+= "Floor Area: " + this.floorArea + "\n\n";
        output+= "Total Cost: " + floorCost + "\n\n ";
        JScrollPane jsp = new JScrollPane(new JTextArea(output));
        box.add(jsp);
        box.add(btnSubmitOrder);
        box.add(btnEditOrder);

//**************************************************
//*****************ATTACHED EVENTS******************
//**************************************************
        Events handler = new Events();
        btnSubmitOrder.addActionListener(handler);
        btnEditOrder.addActionListener(handler);
//btnRefreshResults.addActionListener(handler);
        try{
            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/FloorPurchasingSystem?verifyServerCertificate=false&useSSL=false","user1","Password#1");
            //TODO complete connection credentials
        }catch(SQLException e1){
            e1.printStackTrace();
        }//end of database connection
        getContentPane().add(box);

    }//end of constructor
    //**************************************************
//******************INNER CLASS EVENT HANDLER*********************
//**************************************************
    private class Events implements ActionListener
    {
        //variables
        private double dFloorArea = Double.parseDouble(floorArea);
        private double dFloorCost = Double.parseDouble(floorCost);
        @Override
        public void actionPerformed(ActionEvent e)
        {

            if(e.getSource()==btnSubmitOrder)
            {
                PreparedStatement staticQuery;
                //TODO complete  insert statement
                String query = "INSERT INTO CustOrder (CustomerName, CustomerAddress, FlooringType, FloorArea, FloorCost) VALUES(?,?,?,?,?)";
                try{
                    staticQuery = conn.prepareStatement(query);
                    staticQuery.setString(1, custName);
                    staticQuery.setString(2, custAddress);
                    staticQuery.setString(3, floorType);
                    staticQuery.setDouble(4, dFloorArea);
                    staticQuery.setDouble(5, dFloorCost);

                    staticQuery.execute();

                    JOptionPane.showMessageDialog(null, "Customer Order has been added");
                }catch(SQLException e1)
                {
                    System.out.println("Connection Failed! Check output");
                    System.out.println(e1.getMessage());
                    return;
                }//end of database insert
                System.out.println("clicked button submit order");
//		//TODO add database insert logic
            }//end of submit
            else if(e.getSource()==btnEditOrder)
            {
                setVisible(false);
                dispose();
            }


        }//END OF ACTION PERFORMED
    }//END OF EVENTS HANDLER INNER CLASS
}//end of SummartGUI

