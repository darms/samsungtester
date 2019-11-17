/**
 * DISCLAIMER: PLEASE TAKI thiE NOTE THAT THE SAMPLE APPLICATION AND
 * SOURCE CODE DESCRIBED HEREIN IS PROVIDED FOR TESTING PURPOSES ONLY.
 * <p>
 * Samsung expressly disclaims any and all warranties of any kind,
 * whether express or implied, including but not limited to the implied warranties and conditions
 * of merchantability, fitness for com.samsung.knoxsdksample particular purpose and non-infringement.
 * Further, Samsung does not represent or warrant that any portion of the sample application and
 * source code is free of inaccuracies, errors, bugs or interruptions, or is reliable,
 * accurate, complete, or otherwise valid. The sample application and source code is provided
 * "as is" and "as available", without any warranty of any kind from Samsung.
 * <p>
 * Your use of the sample application and source code is at its own discretion and risk,
 * and licensee will be solely responsible for any damage that results from the use of the sample
 * application and source code including, but not limited to, any damage to your computer system or
 * platform. For the purpose of clarity, the sample code is licensed “as is” and
 * licenses bears the risk of using it.
 * <p>
 * Samsung shall not be liable for any direct, indirect or consequential damages or
 * costs of any type arising out of any action taken by you or others related to the sample application
 * and source code.
 */
package com.samsung.knox.example.knoxsdk;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.application.ApplicationPolicy;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.ProKioskManager;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.restriction.RestrictionPolicy;

import java.util.ArrayList;
import java.util.List;

import static com.samsung.android.knox.application.ApplicationPolicy.PERMISSION_POLICY_STATE_GRANT;

/**
 * This activity displays the main UI of the application. This is a simple application to restrict
 * use of the device camera using the Samsung Knox SDK.
 * Read more about the Knox SDK here:
 * https://seap.samsung.com/sdk
 * <p>
 * Please insert valid KPE License key to {@link Constants}.
 * </p>
 *
 * @author Samsung R&D Canada Technical Support
 */

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private static final int DEVICE_ADMIN_ADD_RESULT_ENABLE = 1;
    private Button mToggleAdminBtn;
    private DevicePolicyManager mDPM;
    private ComponentName mDeviceAdmin;
    private Utils mUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //...called when the activity is starting. This is where most initialization should go.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView LogView = findViewById(R.id.logview_id);
        LogView.setMovementMethod(new ScrollingMovementMethod());
        mUtils = new Utils(LogView, TAG);

        // Check if device supports Knox SDK
        mUtils.checkApiLevel(24, this);

        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDeviceAdmin = new ComponentName(MainActivity.this, SampleAdminReceiver.class);

        mToggleAdminBtn = findViewById(R.id.toggleAdminBtn);
        mToggleAdminBtn.setOnClickListener(view -> toggleAdmin());

        Button activateLicenceBtn = findViewById(R.id.activateLicenseBtn);
        activateLicenceBtn.setOnClickListener(view -> activateLicense());

        Button deactivateLicenceBtn = findViewById(R.id.deactivateLicenseBtn);
        deactivateLicenceBtn.setOnClickListener(view -> deactivateLicense());

        Button toggleCameraBtn = findViewById(R.id.toggleCameraBtn);
        toggleCameraBtn.setOnClickListener(view -> toggleCameraState());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshButtons();
    }

    /** Handle result of device administrator activation request */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DEVICE_ADMIN_ADD_RESULT_ENABLE) {
            switch (resultCode) {
                // End user cancels the request
                case Activity.RESULT_CANCELED:
                    mUtils.log(getString(R.string.admin_cancelled));
                    break;
                // End user accepts the request
                case Activity.RESULT_OK:
                    mUtils.log(getString(R.string.admin_enabled));
                    refreshButtons();
                    break;
            }
        }
    }

    /** Present a dialog to activate device administrator for this application */
    private void toggleAdmin() {
        boolean adminState = mDPM.isAdminActive(mDeviceAdmin);
        if (adminState) {
            mUtils.log(getString(R.string.deactivating_admin));
            // Deactivate application as device administrator
            mDPM.removeActiveAdmin(new ComponentName(this, SampleAdminReceiver.class));
            mToggleAdminBtn.setText(getString(R.string.activate_admin));
        } else {
            try {
                mUtils.log(getString(R.string.activating_admin));
                // Ask the user to add a new device administrator to the system
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdmin);
                // Start the add device administrator activity
                startActivityForResult(intent, DEVICE_ADMIN_ADD_RESULT_ENABLE);

            } catch (Exception e) {
                mUtils.processException(e, TAG);
            }
        }
    }

    /**
     * Note that embedding your license key in code is unsafe and is done here for
     * demonstration purposes only.
     * Please visit https://seap.samsung.com/license-keys/about. for more details about license
     * keys.
     */
    private void activateLicense() {

        // Instantiate the EnterpriseLicenseManager class to use the activateLicense method
        KnoxEnterpriseLicenseManager klmManager = KnoxEnterpriseLicenseManager.getInstance(this.getApplicationContext());

        try {
            // KPE License Activation TODO Add license key to Constants.java
            klmManager.activateLicense(Constants.KPE_LICENSE_KEY);
            mUtils.log(getResources().getString(R.string.activate_license_progress));

        } catch (Exception e) {
            mUtils.processException(e,TAG);
        }
    }

    /**
     * Deactivate the license. Doing this frees up a seat in a license with limited seats.
     */
    private void deactivateLicense() {
        // Instantiate the EnterpriseLicenseManager class to use the activateLicense method
        KnoxEnterpriseLicenseManager klmManager = KnoxEnterpriseLicenseManager.getInstance(this.getApplicationContext());

        try {
            // KPE License Activation TODO Add license key to Constants.java
            klmManager.deActivateLicense(Constants.KPE_LICENSE_KEY);
            mUtils.log(getResources().getString(R.string.deactivate_license_progress));

        } catch (Exception e) {
            mUtils.processException(e,TAG);
        }
    }

    /**
     * Toggle the restriction of the device camera on/off. When set to disabled, the end user will
     * be unable to use the device cameras.
     */
    private void toggleCameraState() {
        // Instantiate the EnterpriseDeviceManager class
        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(this.getApplicationContext());
        // Get the RestrictionPolicy class where the setCameraState method lives
        RestrictionPolicy restrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy();
        boolean cameraEnabled = restrictionPolicy.isCameraEnabled(false);

        try {
            // Toggle the camera state on/off
            restrictionPolicy.setCameraState(!cameraEnabled);
            mUtils.log(getResources().getString(R.string.camera_state, !cameraEnabled));
        } catch (Exception e) {
            mUtils.processException(e,TAG);
        }
    }

    /**
     *  Update button state
     */
    private void refreshButtons() {
        boolean adminState = mDPM.isAdminActive(mDeviceAdmin);

        if (!adminState) {
            mToggleAdminBtn.setText(getString(R.string.activate_admin));

        } else {
            mToggleAdminBtn.setText(getString(R.string.deactivate_admin));
        }
    }
}