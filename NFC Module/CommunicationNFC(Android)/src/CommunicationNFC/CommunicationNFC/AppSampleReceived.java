package CommunicationNFC.CommunicationNFC;

import CommunicationNFC.CommunicationNFC.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AppSampleReceived extends Activity {
	static String message = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait);
        TextView messageText = (TextView)findViewById(R.id.viewMessageText);
        // �B�J�G�G���oNFC�Ҧ^�Ǫ����G
        message = NFCModel.getResult();
        messageText.setText("�^�ǰT��:"+message);
        
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
}