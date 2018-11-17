package com.vladqqqqoo.server;

import com.vladqqqqoo.dbconnector.MyDBConnector;
import com.vladqqqqoo.model.querys.FinishQuery;
import com.vladqqqqoo.model.querys.IsDBExistQuery;
import com.vladqqqqoo.model.statuses.Status;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

public class ThreadServer {
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Thread objectThread;

    private MyDBConnector dbConnector;

    private String currentDB;

    public ThreadServer(Socket socket) {
        System.out.println("Client[" + socket.getInetAddress() + ":" + socket.getPort() + "] connected...");
        try {
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectOutputStream.flush();
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Используем анонимный класс
        this.objectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Пока поток не прерван
                while (!ThreadServer.this.objectThread.isInterrupted()) {
                    Object objectFromClient = null;

                    try {
                        // Читаем объект из потока
                        objectFromClient = ThreadServer.this.objectInputStream.readObject();

                        if (objectFromClient instanceof IsDBExistQuery) {
                            // Проверяем, есть ли такая база на сервере
                            // Проверяем, есть ли нужная пользователю БД на сервере
                            ThreadServer.this.dbConnector = new MyDBConnector(((IsDBExistQuery) objectFromClient).getDatabase(), "root", "root");
                            try {
                                ThreadServer.this.dbConnector.connect();
                            } catch (SQLException e) {
                                // Попадаем сюда в том случае, если пользователь ввёл имя несуществующей на нашем сервере базы
                                e.printStackTrace();
                                System.out.println("Такой базы данных нет !");
                                ThreadServer.this.dbConnector = null;
                                try {
                                    ((IsDBExistQuery) objectFromClient).setStatus(Status.Fail);
                                    ThreadServer.this.objectOutputStream.writeObject(objectFromClient);
                                    ThreadServer.this.objectOutputStream.flush();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                continue;
                            }
                            try {
                                ThreadServer.this.currentDB = ((IsDBExistQuery) objectFromClient).getDatabase();

                                ((IsDBExistQuery) objectFromClient).setStatus(Status.Success);
                                ThreadServer.this.objectOutputStream.writeObject(objectFromClient);
                                ThreadServer.this.objectOutputStream.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (objectFromClient instanceof FinishQuery) {
                            ThreadServer.this.objectOutputStream.writeObject(objectFromClient);

                            ThreadServer.this.objectThread.interrupt();

                            // Это неправильно при работе с несколькими клиентами !!!!!!!!!!!!!!!!!
                            System.exit(1);
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        this.objectThread.start();
    }
}
