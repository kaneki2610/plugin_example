package kaneki.com.fluttervnpt;

public class CustomModel {
	public interface OnCustomStateListener {
		void onDataChange();
	}

	private static CustomModel mInstance;
	private OnCustomStateListener mListener;
	private String onData = "";
	private String onEvent = "";

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

	public void changeData(String value, String event) {
		if(mListener != null) {
			onData = value;
			onEvent = event;
			notifyDataChange();
		}
	}

	public Map<String, String> getData() {
		Map<String, String > map = new HashMap<>();
		map.put("value", onData);
		map.put("event", onEvent);
		return map;
	}

	private void notifyDataChange() {
		mListener.onDataChange();
	}
}
