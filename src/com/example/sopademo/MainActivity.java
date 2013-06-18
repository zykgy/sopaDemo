package com.example.sopademo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;

public class MainActivity extends Activity {
	
	private EditText phoneSecEditText;
	private TextView resultView;
	private Button queryButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		phoneSecEditText =(EditText)findViewById(R.id.phone_sec);
		resultView =(TextView)findViewById(R.id.result_text);
		queryButton =(Button)findViewById(R.id.query_btn);
		
		queryButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//�ֻ������
				String phoneSec =phoneSecEditText.getText().toString().trim();
				//���ж��û�������ֻ�������Ƿ�Ϸ�
				if ("".equals(phoneSec)||phoneSec.length()<7) {
					//����������ʾ
					phoneSecEditText.setError("��������ֻ���������");
					phoneSecEditText.requestFocus();
					//����ʾ��ѯ�����TextView���
					resultView.setText("");
					return ;
				}
				
				getRemoteInfo(phoneSec);
			}
		});
	}
/***
 * �ֻ��Ŷι����ز�ѯ
 * 
 * @param phoneSec �ֻ��Ŷ�
 */
public void getRemoteInfo(String phoneSec)
{
	//�����ռ�
	String nameSpace ="http://WebXml.com.cn/";
	//���õķ�������
	
	String methodName ="getMobileCodeInfo";
	
	//EndPoint 
	String endPoint ="http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx";
	
	//SOAP Action
	String soapAction ="http://WebXml.com.cn/getMobileCodeInfo";
	
	//ָ��WebService �������ռ�͵��õķ�����
	SoapObject rpc =new SoapObject(nameSpace, methodName);
	
	
	//������Ҫ����WebService �ӿ���Ҫ�������������mobileCode��userId
	rpc.addProperty("mobileCode", phoneSec);
	rpc.addProperty("userId","");
	
	//���ɵ���WebService ������Soap ������Ϣ����ָ��SOAP �İ汾
	SoapSerializationEnvelope envelope =new SoapSerializationEnvelope(SoapEnvelope.VER10);
	
	envelope.bodyOut =rpc;
	
	//�����Ƿ���õ���dotNet������WebService
	envelope.dotNet =true;
	
	//�ȼ���envelope.bodyOut =rpc;
	envelope.setOutputSoapObject(rpc);
	
	HttpTransportSE transport =new HttpTransportSE(endPoint);
	
	try {
		//����WebService 
		transport.call(soapAction, envelope);
	} catch (Exception e) {
		e.printStackTrace();
	}
	//��ȡ���ص�����
	SoapObject object =(SoapObject)envelope.bodyIn;
	
	//��ȡ���صĽ��
	String result =object.getProperty(0).toString();
	
	//��WebService ���صĽ����ʾ��TextView��
	resultView.setText(result);
}
}
