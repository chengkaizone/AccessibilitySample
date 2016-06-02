package com.line.accessibilitysample;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by lance on 16/6/2.
 */
public class AutoInstallAccessibilityService extends AccessibilityService {
    private final String TAG = "AutoInstall";

    private static final String packageInstallerBundleName = "com.android.packageinstaller";
    private static final String settingsBundleName = "com.android.settings";
    private static final String buttonClassName = "android.widget.Button";

    public enum InvokeType {
        None, Install, Uninstall, Kill
    }

    public static InvokeType invokeType = InvokeType.None;

    public static void reset() {
        invokeType = InvokeType.None;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        processAccessibilityEvent(event);
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {

        return true;
    }

    private void processAccessibilityEvent(AccessibilityEvent event) {

        if (event.getSource() == null) {
            Log.i(TAG, "event source == null");
            return;
        }

        switch (invokeType) {
            case Install:
                installApplication(event);
                break;
            case Uninstall:
                uninstallApplication(event);
                break;
            case Kill:
                killApplication(event);
                break;
        }

    }

    private void installApplication(AccessibilityEvent event) {
        if (event.getSource() == null) {
            return;
        }

        if (packageInstallerBundleName.equals(event.getPackageName()) == false) {
            return;
        }

        // 寻找安装按钮并点击它
        List<AccessibilityNodeInfo> install_nodes = event.getSource().findAccessibilityNodeInfosByText("安装");
        if (install_nodes != null && !install_nodes.isEmpty()) {
            for (AccessibilityNodeInfo node: install_nodes) {
                if (buttonClassName.equals(node.getClassName())) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }

        // 寻找下一步按钮并点击它
        List<AccessibilityNodeInfo> next_nodes = event.getSource().findAccessibilityNodeInfosByText("下一步");
        if (next_nodes != null && !next_nodes.isEmpty()) {
            for (AccessibilityNodeInfo node: next_nodes) {
                if (buttonClassName.equals(node.getClassName())) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }

        // 寻找打开按钮并点击它
        List<AccessibilityNodeInfo> open_nodes = event.getSource().findAccessibilityNodeInfosByText("打开");
        if (open_nodes != null && !open_nodes.isEmpty()) {
            for (AccessibilityNodeInfo node: open_nodes) {
                if (buttonClassName.equals(node.getClassName())) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }

    }

    private void uninstallApplication(AccessibilityEvent event) {

        if (event.getSource() == null) {
            return;
        }

        if (packageInstallerBundleName.equals(event.getPackageName()) == false) {
            return;
        }

        List<AccessibilityNodeInfo> nodes = event.getSource().findAccessibilityNodeInfosByText("确定");
        if (nodes == null || nodes.isEmpty()) {
            return;
        }

        for (AccessibilityNodeInfo node: nodes) {

            if (buttonClassName.equals(node.getClassName()) && node.isEnabled()) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    // 杀死程序
    private void killApplication(AccessibilityEvent event) {
        if (event.getSource() == null) {
            return;
        }

        if (settingsBundleName.equals(event.getPackageName()) == false) {
            return;
        }

        // 寻找强行停止按钮并点击它
        List<AccessibilityNodeInfo> stop_nodes = event.getSource().findAccessibilityNodeInfosByText("强行停止");
        if (stop_nodes != null && !stop_nodes.isEmpty()) {
            for (AccessibilityNodeInfo node: stop_nodes) {
                if (buttonClassName.equals(node.getClassName())) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }

        // 寻找确定按钮并点击它
        List<AccessibilityNodeInfo> ok_nodes = event.getSource().findAccessibilityNodeInfosByText("确定");
        if (ok_nodes != null && !ok_nodes.isEmpty()) {
            for (AccessibilityNodeInfo node: ok_nodes) {
                if (buttonClassName.equals(node.getClassName())) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }

    }

}
