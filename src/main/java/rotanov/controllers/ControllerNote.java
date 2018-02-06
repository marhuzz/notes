package rotanov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rotanov.DataConnection;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class ControllerNote {
    @FXML
    private Button buttonCreate;

    @FXML
    private TextArea textArea;

    //Обработка события при нажатии на кнопку
    public void onClickButtonNote(ActionEvent event) throws Exception{
        String text = textArea.getText();
        final int maxLength = 100;

        // Ограничение по макс. количеству символов и защита от дурака
        if (text.length() <= 100 && text.length() != 0 && !text.isEmpty() && !text.equals("Введите до 100 символов!")){
            // Текст заметки
            final String note = text;

            // Текущая дата
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat myDate = new SimpleDateFormat("dd.MM.yyyy   HH:mm", Locale.ENGLISH);
            final String date = myDate.format(calendar.getTime());

            // Добавление в БД
            new DataConnection(){
                @Override
                public void run() {
                    try {
                        insert(note, date);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            // Закрытие текущего окна
            Stage stage = (Stage) buttonCreate.getScene().getWindow();
            stage.close();

            // Что бы данные успели записаться в базу
            Thread.sleep(1000L);

            // Открытие основного окна
            Stage mainStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main_notes.fxml"));
            mainStage.setTitle("Список заметок");
            mainStage.setScene(new Scene(root));
            mainStage.show();
        }
        else{
            textArea.setText("Введите до 100 символов!");
        }

    }
}
