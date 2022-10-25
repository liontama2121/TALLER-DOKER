package edu.is.escuelaing.arep.serverClient;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class HttpClient {
    private String[] ports = {":35001",":35002",":35003"};
    private int nServer = 0;
    private String url="";
    public HttpClient( ) throws IOException {
        System.out.println(url);
    }
    public static String getHostIp(){
        String realIp = null;
        try {
            InetAddress address = InetAddress.getLocalHost();
            if (address.isLoopbackAddress()) {
                address = getInet4Address();
            }
            realIp = address.getHostAddress();
            return address.getHostAddress();
        } catch (Exception e) {
            System.out.println(e);
        }

        return realIp;
    }

    private static InetAddress getInet4Address() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) networkInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            System.out.println(addresses);
            while (addresses.hasMoreElements()) {
                InetAddress ip = (InetAddress) addresses.nextElement();
                System.out.println(ip);
                if (ip instanceof Inet4Address) {
                    return ip;
                }
            }
        }
        return null;
    }
    public String getMessage() throws UnirestException {
        System.out.println(url+ports[nServer]+"/connect");
        HttpResponse<String> apiResponse = Unirest.get(url+ports[nServer]+"/connect").asString();
        return apiResponse.getBody();
    }
    public String postMessage(String message) throws UnirestException {
        System.out.println(url+ports[nServer]+"/connect");
        HttpResponse<String> apiResponse = Unirest.post(url+ports[nServer]+"/connect")
                .body(message)
                .asString();
        return apiResponse.getBody();
    }

    public void changeServer(){
        nServer=(nServer + 1) % ports.length;
    }
    public String getUrl() {

        return url;
    }
    public void setUrl(String url) {

        this.url = url;


    }
}
