import java.net.Socket;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.socket.assignment.Calculator;
import com.socket.assignment.ICalculator;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class Server 
{
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream output = null;
    
    public Server(int port)
    {
        try
        {
            server = ConnectToPort(port);
            
            if(server == null) throw new NullPointerException("Failed to find open port.");
            System.out.println("Server Started");
            System.out.println("Waiting for client to connect...");
            
            socket = server.accept();
            System.out.println("Connected by client on " + socket.getLocalAddress());
            
            // Getting input from the client socket.
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());

            String line = "";

            // Must check that there are bytes able to read otherwise EOF exception will be thrown.
            while(in.available() > 0)
            {
                try
                {
                    line = in.readUTF();

                    

                    System.out.println("Received question " + '\"' + line + '\"');
                    
                    if(line.equals("0 / 0 =")) break;
                    
                    String answ = ProcessOperation(line);
                    
                    System.out.println("Send back answer " + '\"' + answ + '\"');

                    output.writeUTF("Answer from server: " + answ);
                }
                catch(IOException e)
                {
                    System.err.println(line);
                    e.printStackTrace();
                }
                
            }

            System.out.println("Closing connection");

            socket.close();
            in.close();
            //server.close();
        }
        catch(NullPointerException e)
        {
            System.err.println(e);
        }
        catch(Exception e)
        {
            System.err.println(e);
        }

    }

    private static ServerSocket ConnectToPort(int port)
    {
        ServerSocket serverSocket = null;
        int portAttempting = port;
        int attempts = 0;
        Random random = new Random();

        while(attempts < 5 && serverSocket == null)
        {
            try
            {
                serverSocket = new ServerSocket(portAttempting);
                
            }
            catch(Exception e)
            {
                System.err.println(e);
                // (Max - min + 1) + min
                portAttempting = random.nextInt(((port + 1000) - port + 1)) + port;        
            }

            attempts++;
        }

        return serverSocket;
    }

    public static String ProcessOperation(String line)
    {
        Pattern p = Pattern.compile("^([\\d]+)\\s*([*/+-]{1})\\s*([\\d]+)\\s*[=]{1}\\s*$");
        Matcher m = p.matcher(line);
        
        if(!m.matches()) return "Input error. Re-type the math question again.";

        Integer x = 0, y = 0;
        // Operators are guarenteed to be 1 Character based on the regex.
        Character operator = m.group(2).charAt(0); 
        x = Integer.parseInt(m.group(1));
        y = Integer.parseInt(m.group(3));

        // Little overhead since its such a small program \(o-o)/
        ICalculator calculator = new Calculator();

        float result  = 0;

        switch(operator)
        {
            case '+':
                result = calculator.Add(x, y);
                break;
            case '-':
                result = calculator.Subtract(x, y);
                break;
            case '*':
                result = calculator.Multiply(x, y);
                break;
            case '/':
                try
                {
                    result = calculator.Divide(x, y);
                }
                catch(ArithmeticException e)
                {
                    System.err.println(e);
                    return "Input error. Cannot divide by zero!";
                }
                break;
            default:
                return "Something went horribly wrong!";
        }

        return String.valueOf(result);
    }

    public static void main(String[] args) {
        Server server = new Server(Integer.parseInt(args[0]));
    }
}