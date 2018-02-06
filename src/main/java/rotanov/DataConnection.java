package rotanov;

import javafx.collections.ObservableList;
import rotanov.model.DataModel;
import java.sql.*;

public class DataConnection extends Thread {
    private final String URL = "jdbc:mysql://localhost/db_notes?useSSL=false";
    private final String USERNAME = "root";
    private final String PASSWORD = "****";

    //Выборка данных
    public void selectTableNotes(ObservableList<DataModel> observableList){

        //Подключение к БД
        try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            Statement statement = connection.createStatement()) {

            // Выборка данных
            ResultSet resultSet = statement.executeQuery("SELECT * FROM db_notes.notes;");
            //Внесение данных в коллекцию
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String note = resultSet.getString("note");
                String date = resultSet.getString("data");

                observableList.add(new DataModel(id,date,note));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Внесение данных
    public void insert(String note,String date){
        //Соединение с базой и внесение данных в неё
        try (Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO db_notes.notes (note, data) VALUES (?, ?);")){
            statement.setString(1,note);
            statement.setString(2,date);
            statement.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
