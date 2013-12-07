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
		// �B�J�@����k�G�G������NFCŪ���Ҧ��A���ݶǿ�		
		// nfc.createIntent(this,
		// 					NFCŪ���Ҧ����e��,
		//					���ݶǿ骺�T��,
		//					������T����^������);
		// �^��Intent������
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