package com.vladqqqqoo.model.querys;

import com.vladqqqqoo.model.Packet;
import com.vladqqqqoo.model.statuses.Status;

public class IsDBExistQuery extends Packet {
    private String database;
    private Status  status;

    public IsDBExistQuery(String database, Status status) {
        super("isDBQuery");
        this.database = database;
        this.status = status;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
