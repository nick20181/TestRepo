package blackburn.college.dungeonsandseminars;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class WorkerThread extends Thread {
    private String task = null;
    private Socket socket;
    private InputStreamReader input = null;
    private PrintStream output = null;
    private BufferedReader br = null;
    private String ip = "10.100.128.49";
    private int port = 20202;

    private String serverResponse; //what the main app must bring back from the server

    public WorkerThread(String task) {
        this.task = task;
    }

    public void connectToServer() {
        try {
            this.socket = new Socket(this.ip, this.port);
            this.input = new InputStreamReader(this.socket.getInputStream());
            this.output = new PrintStream(this.socket.getOutputStream());
            this.br = new BufferedReader(this.input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeSocket() {
        try {
            this.br.close();
            this.output.close();
            this.input.close();
            this.socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void closeConnection(){
        try{
            System.out.println("Closeing connection");
            output.println(this.task);
        } catch (Exception e) {
            System.out.println("Error during clsoe Connection");
            e.printStackTrace();
        }
    }

    public void getPartyList() {
        try {
            System.out.println("Asking for party");
            output.println("PartyList");
            while (!this.br.ready()) {
                System.out.println("Waiting for party list");
                Thread.sleep(1000);
            }
            this.serverResponse = this.br.readLine();
        } catch (Exception e) {
            System.out.println("Error during Party List refresh");
            e.printStackTrace();
        }
    }

    public void joinParty(){
        try {
            System.out.println("Asking to join party");
            output.println(this.task);
            while (!this.br.ready()) {
                System.out.println("Waiting for response");
                Thread.sleep(1000);
            }
            this.serverResponse = this.br.readLine();
        } catch (Exception e) {
            System.out.println("Error during party join");
            e.printStackTrace();
        }
    }

    public void getPlayerList() {
        try {
            System.out.println("Asking for players");
            output.println(this.task);
            while (!this.br.ready()) {
                System.out.println("Waiting for player list");
                Thread.sleep(1000);
            }
            this.serverResponse = this.br.readLine();
        } catch (Exception e) {
            System.out.println("Error during Player List refresh");
            e.printStackTrace();
        }
    }

    public void createParty(){
        try{
            output.println(this.task);
            while(!this.br.ready()) {
                System.out.println("waiting for confirmation of party creation");
                Thread.sleep(1000);
            }
            this.serverResponse = this.br.readLine();
        } catch (Exception e) {
            System.out.println("Error Creating party");
            e.printStackTrace();
        }
    }

    public void keepAlive(){
        try{
            output.println(this.task);
            while(!this.br.ready()) {
                System.out.println("waiting for confirmation of party creation");
                Thread.sleep(1000);
            }
            this.serverResponse = this.br.readLine();
        } catch(Exception e){
            System.out.println("Error Creating party");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //make socket
        connectToServer();
        //check command
        if (this.task.equals("PartyList")) {
            getPartyList();
        } else if (this.task.split(":")[0].equals("Join")){
            joinParty();
        } else if (this.task.split(":")[0].equals("PlayerList")) {
            getPlayerList();
        } else if(this.task.split(":")[0].toLowerCase().equals("createparty")) {
            createParty();
        } else if(this.task.split(":")[0].toLowerCase().equals("keepalive")){
            keepAlive();
        } else if(this.task.split(":")[0].toLowerCase().equals("close")){
            closeConnection();
        }

        //call a close socket method
        closeSocket();
    }



    public String getServerResponse() {
        return serverResponse;
    }
}
