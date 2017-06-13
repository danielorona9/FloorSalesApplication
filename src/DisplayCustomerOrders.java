/***********************************************
 * Program Name: DisplayCustomerOrders.java
 * Programmer's Name: Daniel Orona
 * Program Description: This program builds a GUI table with data from the database and puts it in a JScrollPane.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
public class DisplayCustomerOrders extends JFrame{

//database URL, username, and password

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/FloorPurchasingSystem?verifyServerCertificate=false&useSSL=false";
    private static final String USERNAME = "user1";
    private static final String PASSWORD = "Password#1";

    //default query retrieves all data from CustOrders Table
    private static final String QUERY = "SELECT * FROM CustOrder";
    private static ResultSetTableModel tableModel;

    DisplayCustomerOrders()
    {
        //Creates ResultSetTableModel and displays database table
        try
        {
            Box box = Box.createHorizontalBox();
            //create a TableModel for results of query SELECT * FROM CustOrders
            tableModel = new ResultSetTableModel(DATABASE_URL, USERNAME, PASSWORD, QUERY);

            //**************************************************
            //*****************Created Table******************
            //**************************************************
            JTable customerOrderTable = new JTable(tableModel);
            JScrollPane jsp = new JScrollPane(customerOrderTable);
            box.add(jsp);

            getContentPane().add(box);

        }
        catch(SQLException sqlException)
        {
            JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
            tableModel.disconnectedFromDatabase();
            dispose();
        }

    }
}//	END OF DISPLAY CUSTOMER ORDERS CLASS
