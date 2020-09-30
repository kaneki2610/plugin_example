
package kaneki.com.fluttervnpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.startactivitylibrary.NewDesign;


public class SecondActivity extends AppCompatActivity implements NewDesign.OnHeadlineSelectedListener {
	ImageView btnBackFlutterView;
	String deviceInfo = "";
	String model = "";
	CustomModel customModel;
	Button btnGotoPluginNative1;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);

		customModel = CustomModel.getInstance();

		Intent intent = getIntent();
		model = intent.getStringExtra("type");

		btnBackFlutterView = findViewById(R.id.btnOnBackFlutterView);
		btnGotoPluginNative1 = findViewById(R.id.btnGotoPluginNative1);

		handleClick();
		FragmentManager fragmentManager = getSupportFragmentManager();
		NewDesign newDesign = (NewDesign) fragmentManager.findFragmentById(R.id.fragment);
		newDesign.setModel(model);
	}

	@Override
	public void onAttachFragment(@NonNull Fragment fragment) {
		super.onAttachFragment(fragment);
		if (fragment instanceof NewDesign) {
			NewDesign headlinesFragment = (NewDesign) fragment;
			headlinesFragment.setOnHeadlineSelectedListener(this);
		}
	}

	public void handleClick() {
		btnBackFlutterView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnGotoPluginNative1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				customModel.changeData(deviceInfo);
			}
		});
	}

	@Override
	public void onGetValue(String value) {
		deviceInfo = value;
	}
}