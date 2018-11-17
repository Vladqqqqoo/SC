package com.vladqqqqoo.view.controllers;

import com.vladqqqqoo.view.MainView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class WorkspaceC {
    private MainView mainView;

    public void onDBOpen(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage dbChooseStage = new Stage();

                URL location = getClass().getResource("../samples/openingDB.fxml");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(location);
                Parent root = null;
                try {
                    root = loader.load(location.openStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ((OpeningDBC)loader.getController()).setMainView(WorkspaceC.this.mainView);
                ((OpeningDBC)loader.getController()).setSelfStage(dbChooseStage);

                dbChooseStage.setScene(new Scene(root));

                dbChooseStage.initModality(Modality.APPLICATION_MODAL);
                dbChooseStage.show();
            }
        });
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }
}
