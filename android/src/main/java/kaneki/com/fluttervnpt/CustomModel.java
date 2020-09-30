package kaneki.com.fluttervnpt;

import java.util.HashMap;
import java.util.Map;

public class CustomModel {
	public interface OnCustomStateListener {
		void onDataChange();
	}

	private static CustomModel mInstance;
	private OnCustomStateListener mListener;
	private String onData = "";

	private CustomModel() {}

	public static CustomModel getInstance() {
		if(mInstance == null) {
			mInstance = new CustomModel();
		}
		return mInstance;
	}

	public void setListener(OnCustomStateListener listener) {
		mListener = listener;
	}

	public void changeData(String value) {
		if(mListener != null) {
			onData = value;
			notifyDataChange();
		}
	}

	public String getData() {
		return onData;
	}

	private void notifyDataChange() {
		mListener.onDataChange();
	}
}
