/***********************************************
 * Program Name: ResultSetTableModel.java
 * Programmer's Name: Daniel Orona
 * Program Description: This program sets up the database connection, reads the data in the database and outputs it to columns.
 */
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;

public class ResultSetTableModel extends AbstractTableModel
{
    private final Connection connection;
    private final Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int numberOfRows;

    private boolean connectedToDatabase = false;

    public ResultSetTableModel(String url, String username, String password, String query)throws SQLException
    {
        //connect to database
        connection = DriverManager.getConnection(url, username, password);

        //create statement to query database
        statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        //update database connection status
        connectedToDatabase = true;

        //set query and execute it

        setQuery(query);
    }//END OF CONSTRUCTOR
    public Class getColumnClass(int column)throws IllegalStateException
    {
        //ensure database connection is available
        if (!connectedToDatabase)
        {
            throw new IllegalStateException("Not Connected to Database");
        }
        //determine java class of column
        try
        {
            String className = metaData.getColumnClassName(column+1);
            return Class.forName(className);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return Object.class;//if problems occur above, assume type object

    }

    //get number of columns in ResultSet
    @Override
    public int getColumnCount()throws IllegalStateException
    {
        if(!connectedToDatabase)
        {
            throw new IllegalStateException("Not Connected to Database");
        }
        try
        {
            return metaData.getColumnCount();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return 0;
    }

    //get name of a particular column in ResultSet
    public String getColumnName(int column)throws IllegalStateException
    {
        //ensure database connection is available
        if(!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");

        //determine column name
        try
        {
            return metaData.getColumnName(column + 1);
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return "";//if problems, return empty string for column name
    }

    //return number of rows in ResultSet
    @Override
    public int getRowCount() throws IllegalStateException
    {
        //ensure database connection is available
        if(!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");

        return numberOfRows;
    }

    //obtain value in particular row and column
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)throws IllegalStateException
    {
        if(!connectedToDatabase)
        {
            throw new IllegalStateException("Not Connected to Database");
        }
        //obtain a value at specified ResultSet row and column
        try
        {
            resultSet.absolute(rowIndex+1);
            return resultSet.getObject(columnIndex+1);
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return "";
    }

    //set new database query string
    public void setQuery(String query)	throws SQLException, IllegalStateException
    {
        if(!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");

        //specify query and execute it
        resultSet = statement.executeQuery(query);

        //obtain metatdata for ResultSet
        metaData =  resultSet.getMetaData();

        //determine number of rows in ResultSet
        resultSet.last();//move to last row
        numberOfRows = resultSet.getRow();

        //notify JTable that model has changed
        fireTableStructureChanged();

    }

    //close Statment and Connection
    public void disconnectedFromDatabase()
    {
        if(connectedToDatabase)
        {
            //close statement and connection
            try
            {
                resultSet.close();
                statement.close();
                connection.close();

            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
            finally //update database connection status
            {
                connectedToDatabase = false;
            }
        }
    }

}//END OF CUSTOMER ORDER LIST GUI CLASS
