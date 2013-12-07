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
		// �B�J�@����k�@�G������NFCŪ���Ҧ��A�i�i���J��T��ǿ�
		// nfc.createIntent(this,
		// 					NFCŪ���Ҧ����e��,
		//					���ǰe����r����T��������ID,
		//					������T����^������);
		// �^��Intent������
		startActivity(nfc.createIntent(this,R.layout.wait,widgetId,AppSampleReceived.class));
		// �B�J�@����k�G�G������NFCŪ���Ҧ��A���ݶǿ�		
		// nfc.createIntent(this,
		// 					NFCŪ���Ҧ����e��,
		//					���ݶǿ骺�T��,
		//					������T����^������);
		// �^��Intent������
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