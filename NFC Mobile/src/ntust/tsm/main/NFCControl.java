package ntust.tsm.main;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;

public class NFCControl extends Activity{
    private Tag tag;
	private IsoDep isoDep;
	public NFCControl() {
	
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveIntent(getIntent());
 
    }
	private void resolveIntent(Intent intent_this) {
	       tag = (Tag) getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
	       isoDep = IsoDep.get(tag);
	       NFCModel nfc = new NFCModel(isoDep);
	       try {
	    	   	// 建立NFC連線
				isoDep.connect();					
				NFCView.receivedMessage = nfc.received();
				nfc.send(NFCView.sendMessage);
	        	isoDep.close();	        	
	        	Intent intent = new Intent();
				intent.setClass(this, NFCView.class);
	        	startActivity(intent);
	        	finish();
	    	} catch (Exception e) {

			}
	}


}