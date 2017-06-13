/***********************************************
 * Program Name: FloorPurchaseGUITest.java
 * Programmer's Name: Daniel Orona
 * Program Description: creates the instance of the application and sets the size
 */
import javax.swing.JFrame;


public class FloorPurchaseGUITest {

    public static void main(String[] args) {

        FloorPurchaseGUI app = new FloorPurchaseGUI();

        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        app.setSize(600,400);


    }

}