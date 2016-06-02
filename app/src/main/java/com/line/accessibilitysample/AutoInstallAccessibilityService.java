package com.line.accessibilitysample;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by lance on 16/6/2.
 */
public class AutoInstallAccessibilityService extends AccessibilityService {

    public enum InvokeType {
        None, Install, Uninstall, Kill
    }

    public static InvokeType invokeType = InvokeType.None;

    public static void reset() {

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }
}
