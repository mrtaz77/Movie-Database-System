package server;

import system.moviedatabase.Movie;
import util.NetworkUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server implements Runnable{
    private static final String INPUT_FILE_NAME = "movies.txt";


    private ServerSocket serverSocket;
    private Thread t;
    public List<Movie>movieList;


    Server()  {
        System.out.println("In server");
        t = new Thread(this);
        t.start();
        movieList=Movie.getMovieList();
        System.out.println(movieList.size());
        try {
            serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Got a client "+clientSocket);
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    @Override
    public void run(){
        System.out.println("In run of read thread server");
        try (BufferedReader fr = new BufferedReader(new FileReader(INPUT_FILE_NAME))) {
            while (true) {
                /* loading moviefile */
                String line;
                try {
                    line = fr.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (line == null) break;
                String[] out = line.split(",");
                new Movie(out[0], Integer.parseInt(out[1], 10), out[2], out[3], out[4], Integer.parseInt(out[5], 10), out[6], Long.parseLong(out[7], 10), Long.parseLong(out[8], 10));
            }
            movieList=Movie.getMovieList();
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("File not found");
        }
    }

    public void serve(Socket clientSocket)throws IOException{
        System.out.println("Serving client "+clientSocket);
        NetworkUtil networkUtil=new NetworkUtil(clientSocket);
        new ReadThreadServer(networkUtil,movieList);
    }

    public static void main(String[] args) {
        System.out.println("In main of server");
        new Server();
    }
}
