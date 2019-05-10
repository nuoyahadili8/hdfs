package com.terdata.thrift.controller;

import com.terdata.thrift.client.JazzClient;
import com.terdata.thrift.vo.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jazz")
public class JazzClientController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JazzClient jazzClient;

    @RequestMapping(value = "/exists", method = RequestMethod.GET)
    public R hello(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            logger.info("开始处理业务....");
            jazzClient.open();
            String isExists = jazzClient.getJazzService().helloString("a");
            retMap.put("result", isExists);
            logger.info("处理结果, 返回={}", retMap);
            return R.ok().put("data",retMap);
        } catch (Exception e) {
            logger.error("处理失败, 返回={}", retMap, e);
            return R.ok().put("data","no hello");
        } finally {
            jazzClient.close();
        }
    }
}
