package com.vladqqqqoo.view;

import com.vladqqqqoo.client.Client;
import com.vladqqqqoo.model.querys.FinishQuery;
import com.vladqqqqoo.view.controllers.WorkspaceC;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;


public class MainView extends Application {
    private Stage mainStage;

    private Client client;

    private WorkspaceC workspaceC;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainStage = primaryStage;

        URL location = getClass().getResource("samples/workspace.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(location);
        Parent root = loader.load(location.openStream());

        this.client = new Client(this, "localhost", 8989);

        this.workspaceC = loader.getController();

        this.workspaceC.setMainView(this);
        // Вызов функции дизейбла всех элементов управления на форме
        //this.workspaceC.initDisable();

        // Обработка события нажатия на крест
        this.mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // Отправляем на сервер команду преращения работы
                MainView.this.client.sendToServer(new FinishQuery());
            }
        });

        this.mainStage.setTitle("Workspace");
        this.mainStage.setScene(new Scene(root));
        this.mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Client getClient() {
        return client;
    }
}
