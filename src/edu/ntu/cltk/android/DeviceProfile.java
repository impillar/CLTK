package edu.ntu.cltk.android;

import android.content.Context;
import android.telephony.TelephonyManager;
/**
 * Acquire the permission of READ_PHONE_STATE
 * @author pillar
 *
 */
public class DeviceProfile {

	private String mIMEI = null;
	private String mIMSI = null;
	private String mPhoneNumber = null;
	private Context mContext = null;
	private TelephonyManager mManager = null;
	
	public DeviceProfile(Context context){
		this.mContext = context;
	}
	
	public String getmIMEI() {
		return mIMEI;
	}

	public void setmIMEI(String mIMEI) {
		this.mIMEI = mIMEI;
	}

	public String getmIMSI() {
		return mIMSI;
	}

	public void setmIMSI(String mIMSI) {
		this.mIMSI = mIMSI;
	}

	public String getmPhoneNumber() {
		return mPhoneNumber;
	}

	public void setmPhoneNumber(String mPhoneNumber) {
		this.mPhoneNumber = mPhoneNumber;
	}

	public void loadTelephonyService(){
		mManager = (TelephonyManager)mContext.getSystemService(mContext.TELEPHONY_SERVICE);
		this.mIMEI = mManager.getDeviceId();
		this.mIMSI = mManager.getSubscriberId();
		this.mPhoneNumber = mManager.getLine1Number();
	}
	
	public static String getIMEICard(Context context){
		TelephonyManager manager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
		return manager.getDeviceId();
	}
	
	public static String getIMSICard(Context context){
		TelephonyManager manager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
		return manager.getSubscriberId();
	}
	
	public static String getPhoneNumber(Context context){
		TelephonyManager manager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
		return manager.getLine1Number();
	}
}
