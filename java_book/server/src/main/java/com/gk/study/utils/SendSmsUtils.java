package com.gk.study.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@Slf4j
public class SendSmsUtils {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;

    /**
     * 发送验证码
     */
    public void sendCode(String phone, String code) throws Exception {
        //1.配置config
        Config config = new Config()
                .setEndpoint(endpoint)
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        //2.创建Client对象
        Client client = new Client(config);

        //3.创建SendSmsRequest实例
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("中旸新碳数字科技")
                .setTemplateCode("SMS_471475188")
                .setPhoneNumbers(phone)
                .setTemplateParam("{\"code\":"+ code+ "}");

        RuntimeOptions runtime = new RuntimeOptions();

        try{
            SendSmsResponse response = client.sendSmsWithOptions(sendSmsRequest, runtime);
            log.info("发送结果:{}", Common.toJSONString(response));
        } catch (TeaException error){
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error){
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }
}
