package de.fhtrier.gdig.demos.jumpnrun.server.network;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetworkHelper {

	public static InterfaceAddress getInterfaceByIp(String ip) {

		try {
			Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces();

			while (en.hasMoreElements()) {
				NetworkInterface ni = en.nextElement();

				for (InterfaceAddress address : ni.getInterfaceAddresses()) {
					if (address.getAddress().getHostAddress().contains(ip)) {
						return address;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<InterfaceAddress> getInterfaces() {
		List<InterfaceAddress> result = new ArrayList<InterfaceAddress>();

		try {
			Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces();

			while (en.hasMoreElements()) {
				NetworkInterface ni = en.nextElement();

				for (InterfaceAddress address : ni.getInterfaceAddresses()) {
					if (!address.getAddress().getHostAddress().contains(":"))
						result.add(address);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
