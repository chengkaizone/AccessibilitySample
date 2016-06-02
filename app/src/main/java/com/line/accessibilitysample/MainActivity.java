package com.line.accessibilitysample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.activeButton).setOnClickListener(this);
        this.findViewById(R.id.installButton).setOnClickListener(this);
        this.findViewById(R.id.uninstallButton).setOnClickListener(this);
        this.findViewById(R.id.killAppButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activeButton:
            {
                Intent killIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(killIntent);
            }
                break;
            case R.id.installButton:
            {
                String apkName = "test.apk";
                AutoInstallAccessibilityService.invokeType = AutoInstallAccessibilityService.InvokeType.Install;
                String fileName = Environment.getExternalStorageDirectory() + "/" + apkName;
                File installFile = new File(fileName);
                if(installFile.exists()){
                    installFile.delete();
                }
                try {
                    installFile.createNewFile();
                    FileOutputStream out = new FileOutputStream(installFile);
                    byte[] buffer = new byte[512];
                    InputStream in = MainActivity.this.getAssets().open(apkName);
                    int count;
                    while((count= in.read(buffer))!=-1){
                        out.write(buffer, 0, count);
                    }
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
                startActivity(intent);
            }
            break;
            case R.id.uninstallButton:
            {
                AutoInstallAccessibilityService.invokeType = AutoInstallAccessibilityService.InvokeType.Uninstall;

                Uri packageURI = Uri.parse("package:com.example.test");
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);
            }
            break;
            case R.id.killAppButton:
            {
                AutoInstallAccessibilityService.invokeType = AutoInstallAccessibilityService.InvokeType.Kill;
                Intent killIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri packageURI = Uri.parse("package:com.example.test");
                killIntent.setData(packageURI);
                startActivity(killIntent);
            }
            break;
        }
    }
}
