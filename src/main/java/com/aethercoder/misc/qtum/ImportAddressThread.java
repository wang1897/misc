package com.aethercoder.misc.qtum;


import com.aethercoder.basic.utils.BeanUtils;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 导入地址到节点中
 */
public class ImportAddressThread implements Runnable {

    private List<String> addressList;
    private QtumUtil qtumUtil;
    private Integer number;
    private List resultList;
    private CountDownLatch count;

    /**
     * 导入地址线程
     * @param qtumUtil 工具类
     * @param addressList 地址
     */
    public ImportAddressThread(QtumUtil qtumUtil, List<String> addressList, Integer number, List resultList, CountDownLatch count){
        this.addressList = addressList;
        this.qtumUtil = qtumUtil;
        this.number = number;
        this.resultList = resultList;
        this.count = count;
    }

    /**
     * 逻辑实现
     */
    @Override
    public void run(){
        if (resultList == null){

            qtumUtil.callQtumService("importaddress", addressList, number);

            count.countDown();
        }
        else{
            List<Object> paramList = new ArrayList<>();
            paramList.add(0);
            paramList.add(100000000);
            paramList.add(addressList);

            resultList.add((List)qtumUtil.callQtumService("listunspent", paramList, number));
            count.countDown();
        }
    }
}
