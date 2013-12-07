package ntust.tsm.verify;

import java.net.URLEncoder;

import ntust.tsm.acquire.AcquireTicket;
import ntust.tsm.main.DatabaseOperation;
import ntust.tsm.main.Global;
import ntust.tsm.main.NFCModel;
import ntust.tsm.main.R;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class VerifyTicket extends Activity {
	private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String tid = Global.verifyTicketID;
        NFCModel nfc = new NFCModel();
		// �B�J�@����k�G�G������NFCŪ���Ҧ��A���ݶǿ�		
		// nfc.createIntent(this,
		// 					NFCŪ���Ҧ����e��,
		//					���ݶǿ骺�T��,
		//					������T����^������);
		// �^��Intent������
    	DatabaseOperation mDbHelper = new DatabaseOperation(this);
		mDbHelper.open();
		Cursor cursor = mDbHelper.getQuery("tid",new String[]{tid});
		cursor.moveToNext();
		
		startActivity(nfc.createIntent(this,R.layout.verify_ticket,
				URLEncoder.encode("[{\"tid\":\""+cursor.getString(cursor.getColumnIndex("tid"))+"\","+
				"\"movie\":\""+cursor.getString(cursor.getColumnIndex("movie"))+"\","+
				"\"theater\":\""+cursor.getString(cursor.getColumnIndex("theater"))+"\","+
				"\"date\":\""+cursor.getString(cursor.getColumnIndex("date"))+"\","+
				"\"time\":\""+cursor.getString(cursor.getColumnIndex("time"))+"\"}]")
						,VerifyTicketSuccess.class));
		mDbHelper.close();
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