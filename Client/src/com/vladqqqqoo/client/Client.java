package com.vladqqqqoo.client;

import com.vladqqqqoo.model.Packet;
import com.vladqqqqoo.model.querys.FinishQuery;
import com.vladqqqqoo.model.querys.IsDBExistQuery;
import com.vladqqqqoo.model.statuses.Status;
import com.vladqqqqoo.view.MainView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Thread objectThread;

    private MainView mainView;

    public Client(MainView mainView, String host, int port) {
        this.mainView = mainView;

        Socket socket = null;
        try {
            socket = new Socket(host, port);

            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectOutputStream.flush();
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.objectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Client.this.objectThread.isInterrupted()) {
                    Object objectFromServer = null;

                    try {
                        // Читаем объект из потока
                        objectFromServer = Client.this.objectInputStream.readObject();

                        if (objectFromServer instanceof IsDBExistQuery) {
                            // Проверяем, есть ли такая база на сервере
                            if (((IsDBExistQuery) objectFromServer).getStatus() == Status.Success) {
                                System.out.println("Success");
                            } else {
                                System.out.println("Fail");
                            }
                        }
                        else if (objectFromServer instanceof FinishQuery) {
                            Client.this.objectThread.interrupt();
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

    public void sendToServer(Packet packet) {
        try {
            this.objectOutputStream.writeObject(packet);
            this.objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
