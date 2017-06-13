/***********************************************
 * Program Name: FloorPurchaseGUI.java
 * Programmer's Name: Daniel Orona
 * Program Description: Creates the GUI window with tabs and the events for all three panels/tabs.  one tab is for customer information and calculating.
 * 						The second tab is for generating a information in a clean format.  The third tab is for gathering information from the database.
 */
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import javax.swing.JTabbedPane;

import javax.swing.JTextField;

public class FloorPurchaseGUI extends JFrame
{
    ////////////////////////class variables////////////////////////////////
    //customer application tab
    private JLabel labelCustName, labelAddress, labelFloorType,
            labelWoodFloor, labelFloorMeasure,
            labelWidth, labelLength, labelTotalSQFT, labelTotalCost;
    private JTextField txtCustName, txtaddress, txtWidth, txtLength, txtTotalSQFT,
            txtTotalCost;
    private JRadioButton JRBWoodFloor, JRBCarpet;
    private JButton btnCalc, btnClearMeasurements;

    //purchase summary tab
    private JLabel labelSummaryTitle;
    //reuse some labels and textfields from customer tab.
    private JButton btnGenerateSummary;
    private String output;

    private JLabel labelHistoryTitle;
    private JButton btnRefreshResults;

    //**************************************************
//******************CONSTRUCTOR METHOD**************
//**************************************************
    public FloorPurchaseGUI()
    {
        //title of the application
        super("Floor Purchase System");
        JTabbedPane tab = new JTabbedPane();


//**************************************************
//*************TAB CUSTOMER APPLICATON**************
//**************************************************
        JPanel panelCustApp = new JPanel(new GridLayout(0,2,3,3));

        labelCustName = new JLabel("Customer Name:");
        labelAddress = new JLabel ("Address:");
        labelFloorType = new JLabel ("Floor Type: ");
        labelFloorMeasure = new JLabel ("Fooring Measurements & Cost:");
        labelWidth = new JLabel ("Width:");
        labelLength = new JLabel ("Length:");
        labelTotalSQFT = new JLabel ("Total SQFT:");
        labelTotalCost = new JLabel ("Total Cost:");

        txtCustName = new JTextField(20);
        txtaddress = new JTextField(45);
        txtWidth = new JTextField(4);
        txtLength = new JTextField(4);
        txtTotalSQFT = new JTextField(6);
        txtTotalCost = new JTextField(12);

        JRBWoodFloor = new JRadioButton("Wood Floor");
        JRBCarpet = new JRadioButton("Carpet");

        btnCalc = new JButton("Calculate");
        btnClearMeasurements = new JButton("Clear Measurements");

        //empty spacers
        JLabel empty1 = new JLabel("");
        JLabel empty2 = new JLabel("");
        JLabel empty3 = new JLabel("");
        JLabel empty4 = new JLabel("");
        JLabel empty5 = new JLabel("");
        JLabel empty6 = new JLabel("");
        JLabel empty7 = new JLabel("");
        JLabel empty8 = new JLabel("");



        panelCustApp.add(labelCustName);
        panelCustApp.add(txtCustName);

        panelCustApp.add(labelAddress);
        panelCustApp.add(txtaddress);

        panelCustApp.add(empty1);
        panelCustApp.add(empty2);

        panelCustApp.add(labelFloorType);
        panelCustApp.add(empty3);

        panelCustApp.add(JRBWoodFloor);
        panelCustApp.add(JRBCarpet);

        panelCustApp.add(empty4);
        panelCustApp.add(empty5);

        panelCustApp.add(labelFloorMeasure);
        panelCustApp.add(empty6);

        panelCustApp.add(labelWidth);
        panelCustApp.add(txtWidth);

        panelCustApp.add(labelLength);
        panelCustApp.add(txtLength);

        panelCustApp.add(labelTotalSQFT);
        panelCustApp.add(txtTotalSQFT);

        panelCustApp.add(labelTotalCost);
        panelCustApp.add(txtTotalCost);


        panelCustApp.add(empty7);
        panelCustApp.add(empty8);

        panelCustApp.add(btnCalc);
        panelCustApp.add(btnClearMeasurements);

        ButtonGroup group = new ButtonGroup();
        group.add(JRBCarpet);
        group.add(JRBWoodFloor);


        tab.addTab("Customer Floor Purchase Form", null, panelCustApp, "Panel #1");

//**************************************************
//**************TAB PURCHASE SUMMARY****************
//**************************************************
        JPanel panelSummary = new JPanel(new GridLayout(1,1,3,3));
        btnGenerateSummary = new JButton("Generate Order Summary");
        labelSummaryTitle = new JLabel("Order Summary");
        tab.addTab("Purchase Summary", null, panelSummary, "Panel #2");
        panelSummary.add(btnGenerateSummary);

//**************************************************
//***************TAB ORDER HISTORY******************
//**************************************************
        JPanel panelHistory = new JPanel(new GridLayout());
        labelHistoryTitle = new JLabel("Order History");
        btnRefreshResults = new JButton("View Order History");
        tab.addTab("Order History", null, panelHistory, "Panel #3");
        panelHistory.add(btnRefreshResults);

//**************************************************
//*****************ATTACHED EVENTS******************
//**************************************************
        Events handler = new Events();
        btnCalc.addActionListener(handler);
        btnClearMeasurements.addActionListener(handler);
        JRBWoodFloor.addItemListener(handler);
        JRBCarpet.addItemListener(handler);
        btnGenerateSummary.addActionListener(handler);
        btnRefreshResults.addActionListener(handler);

        getContentPane().add(tab);

    }//end of constructor
    //**************************************************
//******************INNER CLASS EVENT HANDLER*********************
//**************************************************
    private class Events implements ItemListener, ActionListener
    {
        private String floorTypeName;
        private String custName;
        private String address;
        private double woodFloorCost;
        private double carpetCost;
        private double totalSQFT;
        private String strTotalSQFT;
        private double totalCost;
        private String strTotalCost;
        private double width;
        private double length;
        @Override
        public void actionPerformed(ActionEvent e)
        {

            if(e.getSource() == btnCalc)
            {
                if(JRBWoodFloor.isSelected()==false && JRBCarpet.isSelected()==false)
                {
                    JOptionPane.showMessageDialog(null, "Please select a floor type");
                }
                else if(txtWidth.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Please enter a width");
                }
                else if(txtLength.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Please enter a length");
                }
                else
                {
                    length = Double.parseDouble(txtLength.getText());
                    width = Double.parseDouble(txtWidth.getText());

                    totalSQFT = length * width;
                    totalCost = totalSQFT * (woodFloorCost + carpetCost);

                    strTotalSQFT = Double.toString(totalSQFT);
                    strTotalCost = Double.toString(totalCost);

                    txtTotalSQFT.setText(strTotalSQFT);
                    txtTotalCost.setText(strTotalCost);
                }


            }
            else if (e.getSource() == btnClearMeasurements)
            {
                txtLength.setText("");
                txtWidth.setText("");
                txtTotalSQFT.setText("");
                txtTotalCost.setText("");
            }
            else if(e.getSource()==btnGenerateSummary)
            {
                if (txtCustName.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Please enter Customer Name");
                }
                else if (txtaddress.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Please enter an address");
                }
                else if(JRBWoodFloor.isSelected()==false && JRBCarpet.isSelected()==false)
                {
                    JOptionPane.showMessageDialog(null, "Please select a floor type");
                }
                else if(txtWidth.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Please enter a width");
                }
                else if(txtLength.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Please enter a length");
                }
                else if(txtTotalSQFT.getText().isEmpty() && txtTotalCost.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Please click the calculate button in the Customer Floor Purchase Form tab");
                }
                else
                {
                    custName = txtCustName.getText();
                    address = txtaddress.getText();
                    SummaryGUI app = new SummaryGUI(custName,address,floorTypeName, txtTotalSQFT.getText(),txtTotalCost.getText());

                    app.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    app.setVisible(true);
                    app.setSize(600,400);
                }
            }
            else if (e.getSource()==btnRefreshResults)
            {
                DisplayCustomerOrders app = new DisplayCustomerOrders();
                app.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                app.setVisible(true);
                app.setSize(600,400);

            }

        }//END OF ACTION PERFORMED

        @Override
        public void itemStateChanged(ItemEvent e)
        {

            if(JRBWoodFloor.isSelected() == true)
            {
                woodFloorCost = 20.00;
                floorTypeName = "Wood";
            }
            else if(JRBCarpet.isSelected() == true)
            {
                carpetCost = 10.00;
                floorTypeName = "Carpet";
            }
            else if(JRBWoodFloor.isSelected() == false)
            {
                woodFloorCost = 0;
            }
            else if(JRBCarpet.isSelected() == false)
            {
                carpetCost = 0;
            }
        }//END OF ITEM STATE CHANGED
    }//END OF EVENTS HANDLER INNER CLASS
}//end of FloorPurchaseGUI
