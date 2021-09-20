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
            
            System.out.println("Connected");
            
            input = new BufferedReader(new InputStreamReader(System.in));
            responseInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());

        }
        catch(UnknownHostException e)
        {
            System.err.println(e);
        }
        catch(IOException e)
        {
            System.err.println(e);
        }

        String line = "";

        while(!line.equals("0 / 0 ="))
        {
            try
            {
                line = input.readLine();
                
                output.writeUTF(line);

                String response = responseInput.readUTF();

                System.out.println(response);
            }
            catch(IOException e)
            {
                System.err.println(e);
                break;
            }

        }

        try
        {
            input.close();
            output.close();
            socket.close();
        }
        catch(IOException e)
        {
            System.err.println(e);
        }

    }
    
    public static void main(String[] args) {
        Client client = new Client(args[0], Integer.parseInt(args[1]));
    }
        
}
