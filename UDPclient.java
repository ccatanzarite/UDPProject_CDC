import java.io.*;
import java.net.*;
import java.util.Scanner;
public class UDPclient {

	public static void main(String[] args) {
		String ipAddress = args[0];
		int port = Integer.parseInt(args[1]);
		//get Ip and port number from java UDPclient <ipAddress> <port>
		
		try {//try block
		InetAddress address = InetAddress.getByName(ipAddress);
		DatagramSocket socket = new DatagramSocket();
		String command;
			while(true){//infinite loop looking for commands
				
				Scanner input= new Scanner(System.in);
				System.out.print("Enter a command: ");
				command = input.nextLine();
				
				if(command.equals("<REQUESTQUOTE>")) {//checks to see if the command is a request
					DatagramPacket requestquote= new DatagramPacket(new byte[1],1,address,port);
					socket.send(requestquote);
					
					byte[] buffer = new byte [512];//set buffer byte that the quote will come in as byte
					DatagramPacket responseServer = new DatagramPacket(buffer,buffer.length);
					socket.receive(responseServer);
					
					String quote= new String(buffer,0,responseServer.getLength());//creates quote from the byte recieved from server
					System.out.println(quote);
					
				}
				else if(command.equals("<END>")) {//checks to see if command is end
					System.out.println("Client closed");
					socket.close();
					break;
				}//checks to see if command is end
				
				
			}//while loop
			
		
		}
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//main method

}
