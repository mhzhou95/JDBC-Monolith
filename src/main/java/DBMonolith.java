import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBMonolith {
    public void loadMariaDBDriver(){
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("Driver Loaded");
        }
        catch(Exception e){
            throw new Error (e);
        }
    }
    public Connection dbConnection(String dbVendor, String dbName){
        try{
            Connection connection = DriverManager.getConnection("jdbc:"+dbVendor+"://localhost/"+dbName,"root","");
            System.out.println("Database Connected");
            return connection;
        }
        catch(Exception e){
            throw new Error(e);
        }
    }
    public Statement createStatement(Connection connection){
        try {
            Statement statement =  connection.createStatement();
            return statement;
        }
        catch(Exception e){
            throw new Error(e);
        }
    }
    public ResultSet executeStatement(Statement statement, String sqlQuery){
        try{
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            return resultSet;
        }
        catch(Exception e){
            throw new Error(e);
        }
    }
    public static void main(String[] args) throws SQLException,ClassNotFoundException {

        DBMonolith dbMonolith = new DBMonolith();

        // first load JDBC driver
        dbMonolith.loadMariaDBDriver();

        // second connect to the database
        Connection dbConnection=dbMonolith.dbConnection("mariadb","college");

        // create DBStatement
        Statement dbStatement = dbMonolith.createStatement(dbConnection);

        // execute SQL query through statement
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("Select firstName, lastName, id From Student;");
        ResultSet resultSet = dbMonolith.executeStatement(dbStatement, sqlQuery.toString());

        // prints out every row from query
        while(resultSet.next()){
            System.out.println(
                    resultSet.getString(1)+"\t"+
                            resultSet.getString(2)+"\t"+
                            resultSet.getString(3)
            );
        }
    }
}