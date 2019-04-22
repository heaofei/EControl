package com.mxkj.econtrol.utils.gateway;

import android.util.Log;

import com.fingercrystal.iot.gateway.protocol.controlmessage.GetGatewayInfoControlMessage;
import com.fingercrystal.iot.gateway.protocol.updatamessage.GetGatewayInfoControlResponseMessage;
import com.fingercrystal.iot.util.ByteUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Base64;

/**
 * Created by lidongxing on 2018/2/27 0027.
 */

public class GatewaySearcher {
    private static final GatewaySearcher ourInstance = new GatewaySearcher();
    private DatagramSocket datagramSocket;

    public static GatewaySearcher getInstance() {
        return ourInstance;
    }

    private Thread searchThread;
    private IGatewaySearchListener searchListener;

    public void startSearch(IGatewaySearchListener searchListener) {
        this.searchListener = searchListener;

        searchThread = new Thread(new Runnable() {
            @Override
            public void run() {
                doSearch();
            }
        });
        searchThread.start();
    }

    public void stopSearch() {
        Log.i("tag","============停止搜索网关=");
        if(searchThread!=null && searchThread.isAlive()) {
            searchThread.interrupt();
        }
    }

    private GatewaySearcher() {
    }

    /**
     * 流程：1. 先发送udp 获取 ip 跟 sn ，2.然后拿到ip后建立socket连接， 发送sn，来获取password
     */
    private void doSearch() {
        Log.i("tag","============开始搜索网关=");
        try {
            byte[] sendBuf = "GETIP\r\n".getBytes();
            // 发送
            datagramSocket = new DatagramSocket();
            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, broadcastAddress, 9090);
            datagramSocket.send(sendPacket);

            // 接收
            byte[] receiveBuf = new byte[64];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length);

            while(true){ // 使用while才能接收多个后，建立多次socket发送sn获取pasword
                datagramSocket.receive(receivePacket);
                String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("================data is " + data);
                String[] temp = data.split("\r\n");
                String ip = temp[0].substring(3);
                String sn = temp[1].substring(3);
                System.out.println("================ip is " + ip);
                System.out.println("================sn is " + sn);


                //创建一个客户端socket
                Socket socket = new Socket(ip, 8001);
                //向服务器端传递信息
                OutputStream out = socket.getOutputStream();

                GetGatewayInfoControlMessage controlMessage = new GetGatewayInfoControlMessage(ByteUtil.hexStringToReverseBytes(sn));
                byte[] bytes = controlMessage.getData();
                System.out.println("====================write data " + ByteUtil.bytesToHex(bytes, bytes.length));
                out.write(bytes);
                out.flush();
                //关闭输出流
                socket.shutdownOutput();

                //获取服务器端传递的数据
                InputStream in = socket.getInputStream();
                //接受客户端的响应
                bytes = new byte[512];
                int len = in.read(bytes);
                if(len>0) {
                    GetGatewayInfoControlResponseMessage responseMessage = new GetGatewayInfoControlResponseMessage(bytes);
                    System.out.println("============id is " + responseMessage.getId());
                    System.out.println("============password is " + responseMessage.getPassword());
                    DeviceInfo deviceInfo = new DeviceInfo(sn,responseMessage.getId(), responseMessage.getPassword());
                    searchListener.onDeviceFound(deviceInfo);
                }
                //关闭资源
                in.close();
                out.close();
                socket.close();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
