package ntust.tsm.main;

import ntust.tsm.acquire.AcquireTicket;
import ntust.tsm.main.R;
import ntust.tsm.verify.SelectVerifyTicket;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TSMActivity extends Activity {
    /** Called when the activity is first created. */
	String TAG = "Main";
	private Button btnAcquireTicket;
	private Button btnVerifyTicket;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
       	btnAcquireTicket.setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			Intent intent = new Intent();
    			intent.setClass(TSMActivity.this, AcquireTicket.class);
    			startActivity(intent);
    		}
    	});
        	
    	btnVerifyTicket.setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    				// TODO Auto-generated method stub			
    				try {
    					Intent intent=new Intent();
    			    	intent.setClass(TSMActivity.this, SelectVerifyTicket.class);       	
    			    	startActivity(intent);
    					//Toast.makeText(Main.this,ntust.main.R.string.btnSubmit , Toast.LENGTH_SHORT).show();

    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    					Toast.makeText(TSMActivity.this, "error:"+e.toString(), Toast.LENGTH_SHORT).show();
    				}
    			}
    	});
	}
	private void findViews() {
		btnAcquireTicket = (Button) findViewById(R.id.btnAcquireTicket);
		btnVerifyTicket = (Button) findViewById(R.id.btnVerifyTicket);
	}
}