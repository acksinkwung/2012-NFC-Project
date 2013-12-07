package ntust.tsm.main;

import android.content.Context;
import android.content.Intent;
import android.nfc.tech.IsoDep;
import android.os.Bundle;

public class NFCModel {
	private IsoDep isoDep = null;
	private static String result = "";
	public NFCModel() {
		
	}
	public NFCModel(IsoDep isoDep) {
		this.isoDep = isoDep;
	}
	public static String getResult() {
		String _result = result;
		result = null;
		NFCView.receivedMessage = null;
		return _result;
	}
	public static void setResult(String _result) {
		result = _result;
	}
	public Intent createIntent(Context _context,int _layout,int [] _sendByWidgetID,Class<?> _returnClass) {
		Intent intent = new Intent();
		Intent intentReturn = new Intent();
		intentReturn.setClass(_context, _returnClass);
		NFCView.init(_layout,_sendByWidgetID,intentReturn);		
		intent.setClass(_context, NFCView.class);
		return intent;
	}
	public Intent createIntent(Context _context,int _layout,String _sendMessage,Class<?> _returnClass) {
		Intent intent = new Intent();
		Intent intentReturn = new Intent();
		intentReturn.setClass(_context, _returnClass);
		NFCView.init(_layout,_sendMessage,intentReturn);		
		intent.setClass(_context, NFCView.class);
		return intent;
	}
	public void send(String message) throws Exception{    	
    	byte[] buffer;
    	byte[] rdata;   
    	byte [] data;
    	if (NFCView.receivedMessage != "") {
    		data = (message).getBytes();
    	}else{
    		data = ("          "+message).getBytes();
    	}
		int num=0;
		while (num<data.length+1) {
			buffer = new byte[12];
			buffer[0] = 0x00;
			for (int i=0; i<10; i++) {
				try {
					buffer[i+1] = data[num];
				}catch(Exception ex){
					buffer[i+1] = 0x00;
				}
				num++;
			}
			buffer[11] = 0x00;
			rdata = isoDep.transceive(buffer);
		}
		byte[] dataEnd = {0x01};
		rdata = isoDep.transceive(dataEnd);
    	return ;
    }
    public String received() throws Exception{
	    String str="";
	    int flag = 0;
	    byte[] data = {0x00};
	    try {
	        do {
	            byte[] rdata = isoDep.transceive(data);
		    	if (rdata[0] == 0x01) {
		    	    flag = 1;
		    	}else{
		            str+=new String(rdata);
		    	}
		   } while(flag==0);
	    }catch(Exception e){
	    	
	    }
    	return str;
    }
}
