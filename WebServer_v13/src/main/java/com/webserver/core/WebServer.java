package com.webserver.core;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * WebServer����
 * @author ta
 *
 */
public class WebServer {
	private ServerSocket server;
	/**
	 * ���췽����������ʼ�������
	 */
	public WebServer() {
		try {
			System.out.println("�������������...");
			server = new ServerSocket(8088);
			System.out.println("������������!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ����˿�ʼ�����ķ���
	 */
	public void start() {
		try {
			while(true) {
				System.out.println("�ȴ��ͻ�������...");
				Socket socket = server.accept();
				System.out.println("һ���ͻ��������ˣ�");
				//����һ���̴߳����ÿͻ�������
				ClientHandler handler 
					= new ClientHandler(socket);
				Thread t = new Thread(handler);
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		WebServer server = new WebServer();
		server.start();
	}
}









