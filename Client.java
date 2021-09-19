import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class Client 
{
    
    private Socket socket = null;
    private BufferedReader input = null;
    private DataOutputStream output = null;

    public Client(String address, int port) 
    {
        try
        {
            socket = new Socket(address, port);
            
            System.out.println("Connected");
            
            input = new BufferedReader(new InputStreamReader(System.in));

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

        while(!line.equals("Over"))
        {
            try
            {
                line = input.readLine();

                System.out.print("Echo: " + line);
            }
            catch(IOException e)
            {
                System.err.println(e);
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
        System.out.println(args[0] + " " + args[1]);
        Client client = new Client(args[0], Integer.parseInt(args[1]));
    }
        
}
