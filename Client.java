import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class Client 
{
    
    private Socket socket = null;
    private BufferedReader input = null;
    private DataInputStream responseInput = null;
    private DataOutputStream output = null;

    public Client(String address, int port) 
    {
        try
        {
            socket = new Socket(address, port);
            
            if(socket.getPort() != port)
            {
                System.out.println("The port number supplied was used, try connecting on port " + socket.getPort());
            }

            System.out.println("Connected");
            
            input = new BufferedReader(new InputStreamReader(System.in));
            responseInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());

            String line = "";

            while(true)
            {
                try
                {
                    if(socket.isClosed())
                    {
                        System.err.println("Server socket was closed, closing connection...");
                        break;
                    }

                    line = input.readLine();

                    if(line == null) continue;

                    output.writeUTF(line);
                    
                    if(line.equals("0 / 0 ="))
                    {
                        System.out.println("Exiting, closing connection...");
                        break;
                    }

                    String response = responseInput.readUTF();
                    
                    System.out.println(response);
                }
                catch(IOException e)
                {
                    
                    System.err.println(e.getStackTrace());
                    break;
                }

            }
        }
        catch(UnknownHostException e)
        {
            System.err.println(e.getStackTrace());
        }
        catch(IOException e)
        {
            System.err.println(e.getStackTrace());
        }

        try
        {
            input.close();
            output.close();
            socket.close();
        }
        catch(IOException e)
        {
            System.err.println(e.getStackTrace());
        }

    }
    
    public static void main(String[] args) {
        Client client = new Client(args[0], Integer.parseInt(args[1]));
    }
        
}
