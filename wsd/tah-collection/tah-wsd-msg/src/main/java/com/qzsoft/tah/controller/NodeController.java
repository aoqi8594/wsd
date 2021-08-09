package com.qzsoft.tah.controller;

import com.qzsoft.tah.service.WsdExportCatBService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@CrossOrigin
@RestController
@RequestMapping("/node/mapping")
public class NodeController {

    @Resource
    private WsdExportCatBService wsdExportCatBService;

//    @PostMapping("/path")
//    public Object getFilePath(@RequestBody String body, HttpServletResponse response) {
//        Map<String, Object> res = new HashMap<>(3);
////        String loginName = JacksonUtil.parseString(body, "loginName");
//        String exportId = JacksonUtil.parseString(body, "exportId");
//        if (ObjectUtils.isEmpty(exportId)) {
//            res.put("code", 30001);
//            res.put("msg", "参数值不正确");
//            return res;
//        }
//        String filepath = wsdExportCatBService.recordExportCat(Long.parseLong(exportId), true);
//        if (ObjectUtils.isEmpty(filepath) || filepath.contains("错误")) {
//            res.put("code", 30002);
//            res.put("msg", "Excel生成失败 " + filepath);
//            return res;
//        }
//        //服务器端
//        try(
//            OutputStream output = response.getOutputStream();
//            FileInputStream input = new FileInputStream(filepath);
//        ){
//            byte[] bts = new byte[8192];
//            int len = -1;
//            while((len=input.read(bts))!=-1){
//                output.write(bts,0,len);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        res.put("code", 0);
//        res.put("msg", "成功");
//        return res;
//    }



    @GetMapping("/path")
    public void getFilePath(Long exportId, HttpServletResponse response) {
//        Integer exportId = JacksonUtil.parseInteger(body, "exportId");
        String path = wsdExportCatBService.recordExportCat(exportId, true);
        try {
            // path是指想要下载的文件的路径
            File file = new File(path);
            // 获取文件名
            String filename = file.getName();
            // 获取文件后缀名
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

            // 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 告知浏览器文件的大小
            response.addHeader("Access-Control-Expose-Headers","Content-Disposition");
            response.addHeader("Content-Length", "" + file.length());
            response.setHeader("Access-Control-Allow-Origin", "*");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
