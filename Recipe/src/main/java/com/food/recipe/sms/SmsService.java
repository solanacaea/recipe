package com.food.recipe.sms;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import com.food.recipe.register.RegisterMessage;
import com.food.recipe.register.RegisterMessageRepo;
import com.google.common.collect.Maps;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

@Service
public class SmsService {

	@Value("sms.app.id")
	private String appId;
	
	@Value("sms.app.key")
	private String appKey;
	
	@Value("sms.app.key.secret")
	private String appKeySecret;
	
	@Value("sms.server")
	private String smsServer;
	
	@Value("sms.template.register")
	private String tempRegister;
	
	@Value("sms.template.reset")
	private String tempReset;
	
	@Value("sms.template.login")
	private String tempLogin;

	private Map<SmsTemplate, String> templates = Maps.newEnumMap(SmsTemplate.class);
	
	@Autowired
	private RegisterMessageRepo msgRepo;
	
	@PostConstruct
	public void init() {
		templates.put(SmsTemplate.REGISTER, tempRegister);
		templates.put(SmsTemplate.RESET, tempReset);
		templates.put(SmsTemplate.LOGIN, tempLogin);
	}
	
	public String sendMessage(String phone, SmsTemplate template) {
		try {
			Credential cred = new Credential(appKey, appKeySecret);
			
			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setReqMethod("POST");
			httpProfile.setConnTimeout(60);
			httpProfile.setEndpoint(smsServer);

			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setSignMethod("HmacSHA256");
			clientProfile.setHttpProfile(httpProfile);

			SmsClient client = new SmsClient(cred, "",clientProfile);
			SendSmsRequest req = new SendSmsRequest();
			String appid = appId;
			req.setSmsSdkAppid(appid);

			String sign = "技术宅啊宅";
			req.setSign(sign);

			String senderid = "";
			req.setSenderId(senderid);

			String session = RequestContextHolder.getRequestAttributes().getSessionId();
			req.setSessionContext(session);

			String extendcode = "";
			req.setExtendCode(extendcode);

			String templateID = templates.get(template);
			req.setTemplateID(templateID);

			String[] phoneNumbers = {"+86" + phone};
			req.setPhoneNumberSet(phoneNumbers);

			int qrcode = RandomUtils.nextInt(1000, 9999);
			String code = String.valueOf(qrcode);
			String[] templateParams = { code };
			req.setTemplateParamSet(templateParams);

			SendSmsResponse res = client.SendSms(req);
			//       System.out.println(SendSmsResponse.toJsonString(res));
			String requestId = res.getRequestId();
			
			CompletableFuture.runAsync(() -> {
				RegisterMessage rm = RegisterMessage.builder().requestId(requestId)
					.code(code).phone(phone).createdDate(LocalDateTime.now()).build();
				msgRepo.saveMessage(rm);
			});
			
			return "发送成功";
		} catch (TencentCloudSDKException e) {
			ExceptionUtils.getStackTrace(e);
			return "发送失败";
		}
	}
}
