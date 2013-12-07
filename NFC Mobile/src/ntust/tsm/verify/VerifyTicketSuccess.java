package ntust.tsm.verify;

import java.net.URLDecoder;

import ntust.tsm.acquire.AcquireTicket;
import ntust.tsm.main.DatabaseOperation;
import ntust.tsm.main.Global;
import ntust.tsm.main.NFCModel;
import ntust.tsm.main.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class VerifyTicketSuccess extends Activity {
    String tid = "";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_ticket_success);
        ImageView background = (ImageView)findViewById(R.id.acquire_ticket_success_background);
        String message = URLDecoder.decode(NFCModel.getResult());
    	DatabaseOperation mDbHelper = new DatabaseOperation(this);
		mDbHelper.open();
		mDbHelper.delete( Global.verifyTicketID );

        
    }
}
