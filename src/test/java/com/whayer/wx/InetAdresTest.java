package com.whayer.wx;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class InetAdresTest {
	public static void main(String[] args) throws UnknownHostException {
		InetAddress address = InetAddress.getLocalHost();
		
		System.out.println("计算机名: " + address.getHostName());
		System.out.println("IP: " + address.getHostAddress());
		byte[] bytes = address.getAddress();
		System.out.println("字节数组形式的IP: " + Arrays.toString(bytes));
		
		InetAddress address2 =InetAddress.getByName("duyu-PC");
		
		InetAddress address3 =InetAddress.getByName("192.168.0.101");
		
	}
}
