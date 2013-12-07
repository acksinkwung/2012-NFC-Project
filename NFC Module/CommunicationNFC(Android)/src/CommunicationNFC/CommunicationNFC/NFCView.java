package CommunicationNFC.CommunicationNFC;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.EditText;

public class NFCView extends Activity {
	static String receivedMessage = null;
	static String sendMessage = null;
	private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    private static int layout;
    private static int [] sendByWidgetID;
    private static Intent returnIntent;
    private static char split = ',';
    public static void init(int _layout,int [] _sendByWidgetID,Intent _returnIntent) {
    	init(_layout,_sendByWidgetID,_returnIntent,',');
    }
    public static void init(int _layout,String _sendMessage,Intent _returnIntent) {
    	layout = _layout;
    	returnIntent = _returnIntent;
    	sendMessage = _sendMessage;
    	receivedMessage = null;
    }
    public static void init(int _layout,int [] _sendByWidgetID,Intent _returnIntent,char _split) {
    	layout = _layout;
    	sendByWidgetID = _sendByWidgetID;
    	returnIntent = _returnIntent;
    	sendMessage = null;
    	split = _split;
    	receivedMessage = null;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        setContentView(layout);
        if (receivedMessage!=null && receivedMessage.replace(" ","").replace("¡@","")!="") {
        	NFCModel.setResult(receivedMessage); 
        	startActivity(returnIntent);            
        	finish();
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }
    @Override
    public void onNewIntent(Intent intent) {
    	if (NFCView.sendMessage == null) {
    		NFCView.sendMessage = "";
    		for (int n=0; n<sendByWidgetID.length; n++) {
    			EditText editURLText = (EditText) findViewById(sendByWidgetID[n]);
    			NFCView.sendMessage += editURLText.getText().toString() + split;
    		}
    		NFCView.sendMessage = NFCView.sendMessage.substring(0, NFCView.sendMessage.length()-1);
    	}
    	intent.setClass(NFCView.this,NFCControl.class);
    	startActivity(intent);
    	finish();
    }    
    @Override
    public void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(this);
    }
}