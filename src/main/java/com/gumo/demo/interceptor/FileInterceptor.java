package com.gumo.demo.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;

@Component
class FileInterceptor implements HandlerInterceptor {

    @Value(value = "${file.max.size:10}")
    public Integer fileMaxSize;

    @Value(value = "${file.type:jpg,gif,png,ico,bmp,jpeg}")
    public String fileType;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag= true;
        // 判断是否为文件上传请求
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> files = multipartRequest.getFileMap();
            Iterator<String> iterator = files.keySet().iterator();
            //对多部件请求资源进行遍历
            while (iterator.hasNext()) {
                String formKey = iterator.next();
                MultipartFile multipartFile = multipartRequest.getFile(formKey);
                String filename = multipartFile.getOriginalFilename();
                //判断是否为限制文件类型
                if (! checkFile(filename)) {
                    throw new Exception("不支持的文件类型");
                    //限制文件类型，请求转发到原始请求页面，并携带错误提示信息
//                    request.setAttribute("errormessage", "不支持的文件类型！");
//                    request.getRequestDispatcher("/WEB-INF/jsp/userEdit.jsp")
//                            .forward(request, response);
//                    flag= false;
                }
//            获取上传文件尺寸大小
//            long requestSize = multipartFile.getSize();
//            if (requestSize > fileMaxSize) {
//                //当上传文件大小超过指定大小限制后，模拟抛出MaxUploadSizeExceededException异常
//                throw new MaxUploadSizeExceededException(fileMaxSize);
//            }
            }
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

    /**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    private boolean checkFile(String fileName) {
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        //设置允许上传文件类型
        if (fileType.contains(suffix.trim().toLowerCase())) {
            return true;
        }
        return false;
    }
}