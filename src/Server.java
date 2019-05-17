import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


class SocketChild {
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String msg;
	
	public SocketChild() {
		super();	
	}

	public SocketChild(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		super();
		this.socket = socket;
		this.bufferedReader = bufferedReader;
		this.bufferedWriter = bufferedWriter;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public BufferedReader getBufferedReader() {
		return bufferedReader;
	}

	public void setBufferedReader(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	public BufferedWriter getBufferedWriter() {
		return bufferedWriter;
	}

	public void setBufferedWriter(BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void serverSocketAccept(ServerSocket serverSocket) {
		try {
			this.setSocket(serverSocket.accept());
			System.out.println("Client가 연결되었습니다." + this.getSocket());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void receiveMessage() {
		try {
			this.setBufferedReader(new BufferedReader(new InputStreamReader(this.getSocket().getInputStream())));
			String msg = this.getBufferedReader().readLine();
			this.setMsg(msg);
			System.out.println("클라이언트가 보낸 메시지:" + this.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}
	
	public void sendMessage() {
		try {
			this.setBufferedWriter(new BufferedWriter(new OutputStreamWriter(this.getSocket().getOutputStream())));
			this.bufferedWriter.write(this.getMsg() + "\n");
			this.bufferedWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}
	
}

public class Server {
	
	private ServerSocket serverSocket;
	private int port;
	List<SocketChild> clients = new ArrayList<>();	
	
	public Server() {	
	}

	public Server(int port) {		
		this.port = port;		
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public void connectServerSocket() {					
		try {
			System.out.println("Server 연결중");
			this.serverSocket = new ServerSocket(this.port);
			System.out.println("Server 준비완료");
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	
	public void messagePrint(String msg) {
		 System.out.println("Client 에서 보낸 메시지 : " + msg);
	}	

	public static void main(String[] args) {
		
		try {			
			int port = 8989;
			
			//port 초기화
			Server server = new Server(port);
			
			//서버 socket 연결
			server.connectServerSocket();
			
			while(true) {
				SocketChild child = new SocketChild();
				
				//클라이언트 소켓 accept 대기
				child.serverSocketAccept(server.getServerSocket());
				
				if (child.getSocket() != null) {
					server.clients.add(child);
					System.out.println("채팅방수 : " + server.clients.size());
				}				
			
				child.receiveMessage();				
						
				child.sendMessage();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
	}

}
