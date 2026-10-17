package com.codetrekker.bugjaeger;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private final int bg = Color.rgb(13, 18, 30);
    private final int panel = Color.rgb(25, 34, 52);
    private final int accent = Color.rgb(76, 201, 170);
    private LinearLayout content;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(20), dp(24), dp(20), dp(28));
        content.setBackgroundColor(bg);
        scroll.addView(content);
        setContentView(scroll);

        addText("BUGJAEGER TOOLKIT", 24, accent, true);
        addText("ADB • FASTBOOT • DEVICE INFO", 12, Color.LTGRAY, true);
        addText("A mobile-friendly companion for preparing and copying common Android debugging commands. This version does not execute ADB/Fastboot commands or unlock/flash devices directly.", 14, Color.WHITE, false);
        addDeviceInfo();
        addSection("ADB QUICK COMMANDS");
        addCommand("List connected devices", "adb devices -l");
        addCommand("Show Android version", "adb shell getprop ro.build.version.release");
        addCommand("Show device model", "adb shell getprop ro.product.model");
        addCommand("View recent system logs", "adb logcat -d -t 300");
        addCommand("Install an APK from the host", "adb install path/to/app.apk");
        addCommand("Copy a file from device", "adb pull /sdcard/Download/file.ext ./");
        addCommand("Reboot connected device", "adb reboot");
        addCommand("Reboot to bootloader", "adb reboot bootloader");
        addSection("FASTBOOT QUICK COMMANDS");
        addCommand("List fastboot devices", "fastboot devices");
        addCommand("Show bootloader variables", "fastboot getvar all");
        addCommand("Reboot to Android", "fastboot reboot");
        addCommand("Reboot to recovery", "fastboot reboot recovery");
        addSection("IMPORTANT SAFETY");
        addText("Commands are copied only; they are not run by this app. Unlocking a bootloader usually erases user data and may affect warranty. Flashing the wrong image can make a device unbootable. Confirm the exact model, partition and image before flashing. Many manufacturers restrict bootloader unlocking.", 14, Color.rgb(255, 218, 150), false);
        addText("Connection checklist", 17, Color.WHITE, true);
        addText("1. Use a data-capable USB cable and a compatible OTG adapter when needed.\n2. Enable USB debugging on the target device where permitted.\n3. Accept the target device's debugging authorization prompt.\n4. For wireless debugging, pair using Android's pairing flow; the host tool must support it.\n5. Fastboot commands require the target to be in bootloader/fastboot mode.", 14, Color.LTGRAY, false);
    }

    private void addDeviceInfo() {
        addSection("THIS PHONE");
        addText("Manufacturer: " + Build.MANUFACTURER + "\nModel: " + Build.MODEL + "\nAndroid: " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")\nDevice code: " + Build.DEVICE, 14, Color.WHITE, false);
        addText("These details describe the phone running this app, not a USB-connected target device.", 12, Color.LTGRAY, false);
    }

    private void addSection(String title) {
        TextView t = new TextView(this);
        t.setText(title);
        t.setTextColor(accent);
        t.setTextSize(13);
        t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        t.setPadding(0, dp(18), 0, dp(8));
        content.addView(t);
    }

    private void addText(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextColor(color);
        t.setTextSize(size);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        t.setLineSpacing(dp(3), 1f);
        t.setPadding(0, dp(5), 0, dp(8));
        content.addView(t);
    }

    private void addCommand(String title, String command) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(14), dp(12), dp(14), dp(12));
        card.setBackgroundColor(panel);
        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(-1, -2);
        cp.setMargins(0, 0, 0, dp(9));
        card.setLayoutParams(cp);

        TextView name = new TextView(this);
        name.setText(title);
        name.setTextColor(Color.WHITE);
        name.setTextSize(15);
        name.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        card.addView(name);

        TextView cmd = new TextView(this);
        cmd.setText(command);
        cmd.setTextColor(Color.rgb(205, 220, 240));
        cmd.setTextSize(13);
        cmd.setPadding(0, dp(7), 0, dp(10));
        card.addView(cmd);

        Button copy = new Button(this);
        copy.setText("Copy command");
        copy.setAllCaps(false);
        copy.setTextColor(Color.rgb(8, 24, 25));
        copy.setBackgroundTintList(android.content.res.ColorStateList.valueOf(accent));
        copy.setOnClickListener(v -> copyCommand(command));
        card.addView(copy);
        content.addView(card);
    }

    private void copyCommand(String command) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText("ADB/Fastboot command", command));
        Toast.makeText(this, "Command copied. Review it before running.", Toast.LENGTH_LONG).show();
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
