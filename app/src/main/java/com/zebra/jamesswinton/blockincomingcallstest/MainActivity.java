package com.zebra.jamesswinton.blockincomingcallstest;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  // Permissions
  private PermissionsHelper mPermissionsHelper;

  // Intent
  private static final String USSDCodeIntentExtra = "com.zebra.SEND_USSD_CODE";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Permissions
    mPermissionsHelper = new PermissionsHelper(this, () -> {
      String ussdCode = "*261#";
      if (getIntent() != null && getIntent().hasExtra(USSDCodeIntentExtra)) {
        ussdCode = getIntent().getStringExtra(USSDCodeIntentExtra);
      }

      Intent phoneCallIntent = new Intent(Intent.ACTION_CALL, ussdToCallableUri(ussdCode));
      startActivity(phoneCallIntent);
      finish();
    });
  }

  private Uri ussdToCallableUri(String ussd) {
    String uriString = "";
    if(!ussd.startsWith("tel:")) {
      uriString += "tel:";
    }

    for(char c : ussd.toCharArray()) {
      if(c == '#') {
        uriString += Uri.encode("#");
      } else {
        uriString += c;
      }
    }

    return Uri.parse(uriString);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    mPermissionsHelper.onRequestPermissionsResult();
  }
}