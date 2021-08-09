/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qzsoft.tah.udp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author liyong
 */
@Component
@Sharable
@Slf4j
public class FreshShieldHandler extends
        SimpleChannelInboundHandler<DatagramPacket> {

    Logger logger = LoggerFactory.getLogger(FreshShieldHandler.class);

    SimpleDateFormat sdf = new SimpleDateFormat("20yy-MM-dd HH:mm:ss");
    Date date = new Date();
    JSONObject json = new JSONObject();
    StringBuilder sb = new StringBuilder();
    StringBuilder time = new StringBuilder();
    StringBuilder dev = new StringBuilder();
    int devn;
    StringBuilder out = new StringBuilder();
    String zjauth;
    byte sum;
    int location;
    int sensor;
    int datastat;
    long longitude;
    long latitude;
    int tempr;
    int hum;
    int speed;
    int stat;
    int power;
    int event;
//1--正常回执报文，2--下发鉴权报文，3--参数下发报文
    int sendtype;
    int sendflag;

    JSONObject jresponse = new JSONObject();
    JSONObject param = new JSONObject();

    public FreshShieldHandler() throws UnsupportedEncodingException {
        this.zjauth = new String("zjzltest".getBytes(), "ascii");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, DatagramPacket packet) throws Exception {
        logger.info("收到数据");
        // 读取收到的数据
        ByteBuf buf = packet.copy().content();
        //  byte[] mes = new byte[buf.readableBytes()];
        byte[] req = new byte[32];
        // buf.readBytes(mes);
        int count = buf.readableBytes() / 32;
        sendflag = 1;
        for (int mc = 0; mc < count; mc++) {
            buf.readBytes(req, 0, 32);
            log.info("獲取到的值={}", Arrays.toString(req));
            //  req = Arrays.copyOfRange(mes, mc * 32, (mc+1) * 32);
            //包头验证
            if ((req[0] & 0xFF) == 0xFA && (req[1] & 0xFF) == 0xFB && (req[2] & 0xFF) == 0xFC) {
                sum = 0;
                for (int i = 0; i < 31; i++) {
                    sum += req[i];
                }
                //校验和不通过
                if (sum != req[31]) {
                    System.out.print("校验和错误： ");
                    for (int i = 0; i < 32; i++) {
                        System.out.print(Integer.toHexString(i & 0xFF));
                    }
                    System.out.println();
                    sendflag = 0;
                    continue;
                }

                byte[] devnum = new byte[3];
                devnum = Arrays.copyOfRange(req, 4, 7);
                devn = 0;devn += devnum[2] & 0xFF;
                devn += (devnum[1] & 0xFF) << 8;
                devn += (devnum[0] & 0xFF) << 16;
                dev.setLength(0);
                dev.append(devn);

                //同步握手报文
                if (req[3] == 0x01) {

                    byte[] ccid = new byte[20];
                    ccid = Arrays.copyOfRange(req, 7, 27);
                    System.out.println("同步握手报文，设定回执" + req[27] + " 设备编号： " + dev.toString() + "\tccid： " + new String(ccid, "ascii"));

                    //上传注册报文
                    json.clear();
                    json.put("shebeibianhao", dev.toString());
                    json.put("ccid", new String(ccid, "ascii"));
                    HttpClientPost("http://www.ccsc58.com/container/register.php", json.toString());

                    //设定回执为1 正常回执报文
                    if (req[27] == 0x01) {
                        sendtype = 1;

                    } else {
                        sendtype = 2;

                    }

                } else if (req[3] == 0x02) {

                    //req[7] 数据类型标志 req[8]-req[11] 经度 req[12]-req[15] 纬度 req[16-17] 温度 req[18-19] 湿度 req[20-21] 速度 req[22]标志位 req[23] 电池 req[24] 事件 req[25-30] 时间
                    location = (req[7] & 0xC0) >> 6;
                    sensor = (req[7] & 0x30) >> 4;
                    datastat = req[7] & 0x0F;

                    longitude = 0;
                    latitude = 0;
                    for (int i = 0; i < 4; i++) {
                        longitude += (req[8 + i] & 0xFF) << (8 * (4 - i - 1));
                    }
                    for (int i = 0; i < 4; i++) {
                        latitude += (req[12 + i] & 0xFF) << (8 * (4 - i - 1));
                    }
                    tempr = 0;
                    tempr = ((req[16] & 0xFF) << 8) + (req[17] & 0xFF);
                    hum = 0;
                    hum = ((req[18] & 0xFF) << 8) + (req[19] & 0xFF);
                    speed = 0;
                    speed = ((req[20] & 0xFF) << 8) + (req[21] & 0xFF);
                    stat = req[22] & 0xFF;
                    power = req[23] & 0xFF;
                    event = req[24] & 0xFF;

                    out.setLength(0);
                    out.append("设备编号： ");
                    out.append(dev.toString());
                    out.append(" 定位方式： ");
                    switch (location) {
                        case 0:
                            out.append("无效定位");
                            break;
                        case 1:
                            out.append("GPS定位");
                            break;
                        case 2:
                            out.append("GPRS定位");
                            break;
                        case 3:
                            out.append("编号定位");
                            break;
                        default:
                            out.append("定位错误");
                            break;
                    }
                    out.append(" 传感器组合： ");
                    switch (sensor) {
                        case 0:
                            out.append("THS");
                            break;
                        case 1:
                            out.append("TTH");
                            break;
                        case 2:
                            out.append("TTS");
                            break;
                        case 3:
                            out.append("TTT");
                            break;
                        default:
                            out.append("编号组合错误");
                            break;
                    }
                    out.append(" 数据状态有效位： ");
                    out.append(datastat);
                    System.out.print(out.toString() + " 经度： " + longitude + " 纬度： " + latitude
                            + " 温度： " + tempr + " 湿度： " + hum + " 速度： " + speed + " 状态： " + stat + " 电量： "
                            + power + " 事件： " + event + " 时间： ");
                    time.setLength(0);
                    time.append("20");
                    time.append(String.format("%2s", Integer.toString((req[25] & 0xFF))).replace(" ", "0"));
                    time.append("-");
                    time.append(String.format("%2s", Integer.toString((req[26] & 0xFF))).replace(" ", "0"));
                    time.append("-");
                    time.append(String.format("%2s", Integer.toString((req[27] & 0xFF))).replace(" ", "0"));
                    time.append(" ");
                    time.append(String.format("%2s", Integer.toString((req[28] & 0xFF))).replace(" ", "0"));
                    time.append(":");
                    time.append(String.format("%2s", Integer.toString((req[29] & 0xFF))).replace(" ", "0"));
                    time.append(":");
                    time.append(String.format("%2s", Integer.toString((req[30] & 0xFF))).replace(" ", "0"));

                    System.out.print(time.toString());

                    System.out.println();

                    //上传数据
                    json.clear();
                    json.put("shebeibianhao", dev.toString());
                    json.put("servicetime", time.toString());
                    json.put("jingdu", longitude);
                    json.put("weidu", latitude);
                    json.put("temperature01", tempr);
                    json.put("humidity", hum);
                    json.put("power", power);
                    json.put("event", event);
                    HttpClientPost("http://www.ccsc58.com/LAIOTAPI/new_add_data.php", json.toString());
                    param = jresponse.getJSONObject("data");

                    if (param.containsKey("baojingwendu_shangxian")) {
                        sendtype = 3;
                        //SUCCESS,PARA,S1,S2,S3,S4,S5,S6,S7,S8,S9,S10,S11,S12,S13,S14,S15,S16,SumCheck;
                        sb.setLength(0);
                        sb.append("SUCCESS,PARA,");
                        //1	S1  预留
                        sb.append(",");
                        //2	S2	0-40字节	打印头	汉字UTF-8，若为空则采用默认打印名称
                        sb.append(",");
                        //3	S3	19字节	远程下发开始采集时间	标准时间格式
                        sb.append(",");
                        //4	S4	1-5字节	飞行模式延时时间（分钟）	=0，关闭飞行模式，=x，延时x分钟后开启飞行模式
                        sb.append("0,");
                        //5	S5	1-5字节	报警温度上限	整数 返回数据未乘10倍变比，数据乘10
                        sb.append(param.getIntValue("baojingwendu_shangxian") * 10);
                        sb.append(",");
                        //6	S6	1字节	报警开关	0或者1
                        if ((param.getString("baojingwendu_shangxian_baojing").equalsIgnoreCase("0"))
                                && (param.getString("baojingwendu_xiaxian_baojing").equalsIgnoreCase("0"))) {
                            sb.append("0");
                        } else {
                            sb.append("1");
                        }
                        sb.append(",");
                        //7	S7	1-5字节	报警温度下限	整数
                        sb.append(param.getIntValue("baojingwendu_xiaxian") * 10);
                        sb.append(",");
                        //8	S8,S9,S10		预留
                        sb.append(",,,");
                        //9	S11	1-5字节	采集间隔时间，分钟	=0，关闭采集
                        sb.append(param.getString("caiji_jiange_minute"));
                        sb.append(",");
                        //10	S12	1-5字节	发送间隔时间，分钟	=0，关闭发送
                        sb.append(param.getString("fasong_jiange_minute"));
                        sb.append(",");
                        //11	S13	1-5字节	低电量存储下限	0-100%，不能小于15%
                        if (param.getIntValue("dianliang_xiaxian") < 15) {
                            sb.append("15");
                        } else {
                            sb.append(param.getString("dianliang_xiaxian"));
                        }
                        sb.append(",");
                        //12	S14，S15		预留
                        sb.append(",,");
                        //13	S16	19字节	时间
                        date.setTime(System.currentTimeMillis());
                        sb.append(sdf.format(date));
                        sb.append(",");
                    //14	SumCheck	1字节	和校验	最后一个“，”前的所有字节的校验和 于发送函数添加
                    } else {
                        sendtype = 1;
                    }

                   if (event == 8) {
                        //设置报文
                        System.out.println(dev.toString() + " 参数确认");
                    }
                

                } else if (req[3] == 0x09) {
                    byte[] a = new byte[3];
                    a = Arrays.copyOfRange(req, 7, 22);
                    String auth = new String(a, "ascii");
                    auth = auth.replaceAll("\0", "");
                    System.out.println("鉴权报文，设备编号： " + dev.toString() + " 鉴权编码： " + auth);

                    if (auth.equalsIgnoreCase(zjauth)) {
                        sendtype = 1;
                    }
                }
            } else {
                sendflag = 0;
                System.out.print("包头错误： ");

            }

            for (int i = 0; i < 32; i++) {
                System.out.print(String.format("%2s", Integer.toHexString(req[i] & 0xFF)).replace(" ", "0") + " ");
            }
            System.out.println();
        }
        SendFunc(chc, packet);
    }

    public void SendFunc(ChannelHandlerContext chc, DatagramPacket packet) throws InterruptedException {
        if (sendflag == 1) {
            if (sendtype == 1) {
                sb.setLength(0);
                sb.append("SUCCESS,TIME,");
                date.setTime(System.currentTimeMillis());
                sb.append(sdf.format(date));
                sb.append(",");
                sum = 0;
                for (int i = 0; i < sb.length(); i++) {
                    sum += sb.charAt(i);
                }
                sb.append(Integer.toHexString(sum & 0xFF));
                sb.append(";");
                System.out.println("回执报文： " + sb.toString());
                chc.writeAndFlush(new DatagramPacket(
                        Unpooled.copiedBuffer(sb.toString().getBytes()),
                        packet.sender())).sync();
            } else if (sendtype == 2) {
                //SUCCESS,PASSWORD,123456789ABcdef,TIME,2020-01-08 01:55:05,da;
                sb.setLength(0);
                sb.append("SUCCESS,PASSWORD,");
                //鉴权码
                sb.append(zjauth);
                sb.append(",TIME,");
                date.setTime(System.currentTimeMillis());
                sb.append(sdf.format(date));
                sb.append(",");
                sum = 0;
                for (int i = 0; i < sb.length(); i++) {
                    sum += sb.charAt(i);
                }
                sb.append(Integer.toHexString(sum & 0xFF));
                sb.append(";");
                System.out.println("回执报文： " + sb.toString());
                chc.writeAndFlush(new DatagramPacket(
                        Unpooled.copiedBuffer(sb.toString().getBytes()),
                        packet.sender())).sync();
            } else if (sendtype == 3) {
 
                sum = 0;
                for (int i = 0; i < sb.length(); i++) {
                    sum += sb.charAt(i);
                }
                sb.append(Integer.toHexString(sum & 0xFF));
                sb.append(";");
                System.out.println(dev.toString() + " 下发参数回执报文： " + sb.toString());
                chc.writeAndFlush(new DatagramPacket(
                        Unpooled.copiedBuffer(sb.toString().getBytes()),
                        packet.sender())).sync();

            }
        }
    }

    public void HttpClientPost(String url, String json) throws Exception {
        // 获取默认的请求客户端
        CloseableHttpClient client = HttpClients.createDefault();
        // 通过HttpPost来发送post请求
        HttpPost httpPost = new HttpPost(url);
        /*
		 * post带参数开始
         */
        // 第三步：构造list集合，往里面丢数据
        StringEntity requestEntity = new StringEntity(json, "utf-8");
        requestEntity.setContentEncoding("UTF-8");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(requestEntity);

        /*
		 * post带参数结束
         */
        // HttpEntity
        // 是一个中间的桥梁，在httpClient里面，是连接我们的请求与响应的一个中间桥梁，所有的请求参数都是通过HttpEntity携带过去的
        // 通过client来执行请求，获取一个响应结果
        CloseableHttpResponse response = client.execute(httpPost);

        //HttpEntity entity = response.getEntity();
        // String str = EntityUtils.toString(entity, "UTF-8");
        jresponse.clear();
        jresponse = JSON.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
        System.out.println("返回： " + response.getStatusLine().getStatusCode());
        // 关闭
        response.close();
        client.close();
    }

}
