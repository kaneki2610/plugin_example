package kaneki.com.fluttervnpt;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.plugin.common.JSONMethodCodec;

/**
 * MvbarcodescanPlugin
 */
public class FluttervnptPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware, PluginRegistry.ActivityResultListener,
		CustomModel.OnCustomStateListener {

	private static final String channelName = "fluttervnpt";
	private static final String eventChannelName = "locationStatusStream";

	private static final int REQUEST_CODE_FOR_START_ACTIVITY = 2999;

	private MethodChannel channel;
	private static EventChannel eventChannel;
	private static Activity activity;
	private Result pendingResult;

	private static Handler handler = new Handler(Looper.getMainLooper());
	private static int i = 0;
	private static Runnable callback;

	@Override
	public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
		channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), channelName, JSONMethodCodec.INSTANCE);
		channel.setMethodCallHandler(this);




		/*eventChannel = new EventChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), eventChannelName);
		onStreamHandler(eventChannel);*/
	}


	public static void registerWith(Registrar registrar) {
		activity = registrar.activity();
		final MethodChannel channel = new MethodChannel(registrar.messenger(), channelName, JSONMethodCodec.INSTANCE);
		channel.setMethodCallHandler(new FluttervnptPlugin());

		/*eventChannel = new EventChannel(registrar.messenger(), eventChannelName);
		onStreamHandler(eventChannel);*/
	}

/*	public static void onStreamHandler(EventChannel eventChannel) {
		eventChannel.setStreamHandler(
				new EventChannel.StreamHandler() {
					@Override
					public void onListen(Object args, final EventChannel.EventSink events) {
						handler.postDelayed(buildCallBack(events), 1000);
					}

					@Override
					public void onCancel(Object args) {
					}
				}
		);
	}*/

	public static Runnable buildCallBack(final EventChannel.EventSink events) {
		if (callback == null) {
			callback = new Runnable() {
				@Override
				public void run() {
					events.success(String.valueOf(i++));
					handler.postDelayed(callback, 1000);
				}
			};
		}
		return callback;
	}

	@Override
	public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
		if (call.method.equals("startActivity")) {
			this.pendingResult = result;
			String type = call.argument("type");
			if (type == null || (type != null && type.isEmpty())) {
				result.error("ERROR", "type can not null", null);
			} else {
				CustomModel.getInstance().setListener(this);
				Intent intent = new Intent(activity, SecondActivity.class);
				intent.putExtra("type", type);
				//activity.startActivityForResult(intent, REQUEST_CODE_FOR_START_ACTIVITY);
				activity.startActivity(intent);
			}
		} else {
			result.notImplemented();
		}
	}

	@Override
	public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
		channel.setMethodCallHandler(null);
	}

	@Override
	public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
		activity = activityPluginBinding.getActivity();
		activityPluginBinding.addActivityResultListener(this);
	}

	@Override
	public void onDetachedFromActivityForConfigChanges() {
	}

	@Override
	public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
	}

	@Override
	public void onDetachedFromActivity() {
		activity = null;
		handler.removeCallbacks(callback);
	}

	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		/*if (requestCode == REQUEST_CODE_FOR_START_ACTIVITY && data != null) {
			if (resultCode == Activity.RESULT_OK) {
				String result = data.getStringExtra("deviceInfo");
				pendingResult.success(result);
			} else {
				pendingResult.success("");
			}
			return true;
		}*/
		return false;
	}

	@Override
	public void onDataChange() {
		Map<String, String> data = CustomModel.getInstance().getData();
		JSONObject json = new JSONObject();
		try {
			json.put("value", data.get("value"));
			json.put("event", data.get("event"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		pendingResult.success(json);
	}
}