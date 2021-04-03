package com.bridgelabz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.bridgelabz.Contacts;

public class AddressBookDBIO {

    private static AddressBookDBIO bookDBobj;

    private synchronized Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_service ?useSSL=false";
        String userName = "root";
        String password = "mamu";
        Connection connection;
        System.out.println("Connecting to database: " + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection successful!!" + connection);
        return connection;
    }

    public static AddressBookDBIO getInstance() {
        if (bookDBobj == null)
            bookDBobj = new AddressBookDBIO();
        return bookDBobj;
    }

    public List<Contacts> readData() {
        String query = "SELECT * FROM address_book;";
        List<Contacts> record = new ArrayList<Contacts>();
        try(Connection connection = this.getConnection();) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                Long zipCode = resultSet.getLong("zip");
                String phoneNo = resultSet.getString("phoneNo");
                String email = resultSet.getString("email");
                record.add(new Contacts(firstName, lastName, address,city, state,
                        zipCode, phoneNo, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return record;
    }
}
