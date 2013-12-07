package ntust.tsm.verify;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class SelectVerifyTicket extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		
		TabHost tabHost = getTabHost();
	
		Resources res = getResources();
		tabHost.addTab(tabHost
				.newTabSpec("選擇驗證票卷")
				.setIndicator("選擇驗證票卷") 
				.setContent(
						new Intent(this,VerifyTicketList .class)
								.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));


    }
}
