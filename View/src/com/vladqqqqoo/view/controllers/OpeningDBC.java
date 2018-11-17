package com.vladqqqqoo.view.controllers;

import com.vladqqqqoo.model.querys.IsDBExistQuery;
import com.vladqqqqoo.model.statuses.Status;
import com.vladqqqqoo.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OpeningDBC {
    private MainView mainView;
    private Stage selfStage;

    @FXML
    private TextField dbNameTI;

    public void openDBOA(ActionEvent actionEvent) {
        this.mainView.getClient().sendToServer(new IsDBExistQuery(this.dbNameTI.getText(), Status.Request));
        this.selfStage.close();
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public void setSelfStage(Stage selfStage) {
        this.selfStage = selfStage;
    }
}
