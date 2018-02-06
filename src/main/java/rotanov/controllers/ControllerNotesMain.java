package rotanov.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import rotanov.DataConnection;
import rotanov.model.DataModel;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerNotesMain {
    @FXML
    Button buttonCreate;

    @FXML
    TableView<DataModel> tableView;

    @FXML
    TableColumn<DataModel,String> idColumn;

    @FXML
    TableColumn<DataModel,String> noteColumn;

    @FXML
    TableColumn<DataModel,String> dateColumn;

    private ObservableList<DataModel> observableList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){

        //Выборка данных
        new DataConnection(){
            @Override
            public void run() {
                selectTableNotes(observableList);
            }
        }.start();

        //Внесение данных
        idColumn.setCellValueFactory(new PropertyValueFactory<DataModel,String>("id"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<DataModel,String>("text"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<DataModel, String>("date"));

        //Внесение данных в таблицу
        tableView.setItems(observableList);
    }

    //Обработка события при нажатии на кнопку создать
    public void onClickButtonCreateNote(ActionEvent actionEvent){

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("note.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Добавление заметки");
            stage.show();

            //закрытие окна
            Stage mainStage = (Stage) buttonCreate.getScene().getWindow();
            mainStage.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
