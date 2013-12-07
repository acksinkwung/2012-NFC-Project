package CommunicationNFC.CommunicationNFC;

import CommunicationNFC.CommunicationNFC.R;
import android.app.Activity;
import android.app.PendingIntent;
import android.nfc.NfcAdapter;
import android.os.Bundle;

public class AppSampleSend extends Activity {
	private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NFCModel nfc = new NFCModel();
		int [] widgetId = {R.id.editURLText};
		// 步驟一之方法一：切換至NFC讀取模式，可進行輸入資訊後傳輸
		// nfc.createIntent(this,
		// 					NFC讀取模式的畫面,
		//					欲傳送的文字方塊訊息之物件ID,
		//					當接收到訊息返回的頁面);
		// 回傳Intent的物件
		startActivity(nfc.createIntent(this,R.layout.wait,widgetId,AppSampleReceived.class));
		// 步驟一之方法二：切換至NFC讀取模式，等待傳輸		
		// nfc.createIntent(this,
		// 					NFC讀取模式的畫面,
		//					等待傳輸的訊息,
		//					當接收到訊息返回的頁面);
		// 回傳Intent的物件
		//startActivity(nfc.createIntent(this,R.layout.wait,"Message",AppSampleReceived.class));
		finish();
    }
    @Override
    public void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }
    @Override
    public void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(this);
    }
}