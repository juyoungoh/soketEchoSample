import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Ŭ���̾�Ʈ Ŭ����
 * @author 201806081
 *
 */
public class Client {
	
	public static final Logger log = Logger.getGlobal();
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String ip;
	private int port;	

	public Client(String ip, int port) {
		super();		
		this.ip = ip;
		this.port = port;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public void connectSocket() {			
		try {
			System.out.println(ip + " Server ���� ��");
			this.socket = new Socket(this.getIp(), this.getPort());
			System.out.println("Server ���� ����");			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			this.close();
		} catch (IOException e) {
			e.printStackTrace();
			this.close();
		} finally {
			
		}
	}
	
	public void bufferWriterSetting() {
		try {
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			this.close();
		} finally {
			
		}		
	}
	
	public void sendMessage() {
		System.out.print("������ ������ �޽����� �Է��� �ּ��� : ");
		this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String msg;
		try {
			msg = this.bufferedReader.readLine();
			this.bufferedWriter.write(msg + "\n");
			this.bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
			this.close();
		} finally {
			
		}
	}
	
	public void receiveMessage() {
		try {
			this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			System.out.print("������ ���� �޽��� : " + this.bufferedReader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
			this.close();
		} finally {
			
		}		
	}
	
	public void close() {
		try {
			if (this.bufferedReader != null) {
				this.bufferedReader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			if (this.bufferedWriter != null) {
				this.bufferedWriter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			if (this.socket != null) {
				this.socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		String ip = "127.0.0.1";
		int port = 6000;
		
		//ip, port ��ȣ �ʱ�ȭ
		Client client = new Client(ip, port);
			
		//socket ����
		client.connectSocket();
			
		//bufferwrite ����
		client.bufferWriterSetting();			
			
		//������  �޽��� ������
		client.sendMessage();
			
		//������ ���� �޽��� �ޱ�
		client.receiveMessage();
						
		//object close
		client.close();		
	}
}
