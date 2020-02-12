import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.io.*;
import java.net.DatagramPacket;

public class UDPserver {
	private Random random;
	private DatagramSocket socket;
	private List<String> listofQuotes = new ArrayList<String>();//create instance for List that quotes will be loaded into
	
	public UDPserver(int port) throws SocketException {//constructor
		random = new Random();
		socket = new DatagramSocket(port);
	}//constructor
	
	public static void  main(String[] args) {//main method
		String quoteFilePath = "quotes.csv";
		int port = 2030;
		try {
			UDPserver server = new UDPserver(port);
			System.out.println("Server is running");
			server.loadQuotesFromFile(quoteFilePath);
			server.keepChecking();
			
		}
		catch(SocketException ex) {}
		catch(IOException ex) {}
		
	}//main method
	private void keepChecking() throws IOException {//creates infinite loop looking for request from a client
		while(true) {
			DatagramPacket request = new DatagramPacket(new byte[1], 1);
			socket.receive(request);//receives DatagramPacket request for quote
			
			String stringQuote=randomQuote();
			byte[] bufferQuote=stringQuote.getBytes();//converts quote from string to byte to send back
			
			InetAddress UDPclientAddress = request.getAddress();
			int UDPclientPort = request.getPort();//gets address and port from UDPclient
			
			DatagramPacket response = new DatagramPacket(bufferQuote,bufferQuote.length,UDPclientAddress,UDPclientPort);
			socket.send(response);//sends back quote
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String requestString="Request Recieved from "+ UDPclientAddress+": "+UDPclientPort+" "+dateFormat.format(date);
			System.out.println(requestString);
			
			
		}
	}
	private void loadQuotesFromFile(String quoteFilePath) throws IOException {//loads quotes from file to a quote list
		BufferedReader reader = new BufferedReader(new FileReader(quoteFilePath));
		String quote;
		
		while ((quote = reader.readLine()) != null) {//adds quote to a listofQuotes
			listofQuotes.add(quote);
		}
		
		reader.close();
	}//loads quotes from file to a quote list
	
	public String randomQuote() {//gets a random quote from the listofQuotes
		int randomNumber = random.nextInt(listofQuotes.size());
		String randomQuote = listofQuotes.get(randomNumber);
		return randomQuote;
	}//gets a random quote from the listofQuotes
	
	
}
