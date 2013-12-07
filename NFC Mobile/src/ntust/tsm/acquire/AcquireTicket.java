package ntust.tsm.acquire;

import ntust.tsm.main.NFCModel;
import ntust.tsm.main.R;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AcquireTicket extends Activity {
	private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*       	DatabaseOperation mDbHelper = new DatabaseOperation(this);
    		mDbHelper.open();
    		mDbHelper.insert("11111115","201201051200","201201081800","7-Eleven");
    		mDbHelper.close();
*/
        NFCModel nfc = new NFCModel();
		// 步驟一之方法二：切換至NFC讀取模式，等待傳輸		
		// nfc.createIntent(this,
		// 					NFC讀取模式的畫面,
		//					等待傳輸的訊息,
		//					當接收到訊息返回的頁面);
		// 回傳Intent的物件
		startActivity(nfc.createIntent(this,R.layout.acquire_ticket,"Message",AcquireTicketList.class));
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