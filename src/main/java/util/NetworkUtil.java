package util;

import system.moviedatabase.Movie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class NetworkUtil {
    private final Socket socket;
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    public NetworkUtil(String s, int port) throws IOException {
        System.out.println("In networkUtil of client");
        this.socket = new Socket(s, port);
        System.out.println("Connecting socket through string and port");
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public NetworkUtil(Socket s) throws IOException {
        System.out.println("In networkUtil of server");
        this.socket = s;
        System.out.println("Connecting socket through socket");
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public Object read() throws IOException, ClassNotFoundException {
        System.out.println("In read of NetworkUtil");
        return ois.readUnshared();
    }

    public void write(Object o) throws IOException {
        System.out.println("In write of NetworkUtil");
        if(o instanceof Movie){
            System.out.println("Object is a movie");
            ((Movie)o).showMovieDetails();
        }
        else if(o instanceof String)System.out.println((String) o);
        else if(o instanceof LoginDTO){
            System.out.println("Object is a loginDTO");
            LoginDTO loginDTO = (LoginDTO)o;
            System.out.println(loginDTO.getProductionCompanyName());
            if(loginDTO.getMovieList()!=null){
                System.out.println("In write of NetworkUtil");
                for(Movie movie : loginDTO.getMovieList())movie.showMovieDetails();
            }
            else System.out.println("MovieList is null in networkUtil write method");
        }
        oos.writeUnshared(o);
    }

    public void closeConnection() throws IOException {
        System.out.println("In closeConnection of NetworkUtil");
        ois.close();
        oos.close();
    }
}
