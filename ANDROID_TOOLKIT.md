# Bugjaeger Toolkit (new Android companion)

This repository originally contains legacy Bugjaeger APK files and Windows ADB/Fastboot binaries; it does **not** contain the original Android app's source project. The new `app/` module is therefore a separate, buildable companion utility rather than a modification of the original Bugjaeger APK.

## Included in this first version
- Device information for the phone running the toolkit.
- Categorized ADB and Fastboot command reference.
- One-tap copy to clipboard.
- Safety notes for bootloader unlocking and flashing.
- GitHub Actions workflow that builds and uploads a debug APK.

## Important limitation
The app currently prepares and copies commands; it does not itself implement the USB ADB/Fastboot protocol, run shell commands on a connected device, or flash partitions. Those capabilities need a dedicated Android USB-host implementation and testing on real devices. Do not assume that copying a command means it has run.

## Build an installable APK
GitHub Actions runs on pushes and pull requests. Open the repository's **Actions** tab, select **Build Bugjaeger Toolkit APK**, open a successful run, then download the `bugjaeger-toolkit-debug-apk` artifact. Extract it and install `app-debug.apk` on an Android device. Android may ask you to allow installation from the app used to open the file.

For a local build with JDK 17 and Android SDK platform 35 installed, run:

```sh
gradle assembleDebug
```

The output is `app/build/outputs/apk/debug/app-debug.apk`.

## Safety
- Bootloader unlocking commonly erases all user data.
- Flashing an image for the wrong model or partition can brick a device.
- Check the device manufacturer's official instructions and exact model before any destructive command.
- This companion does not bypass Android security restrictions or enable USB debugging where an administrator has disabled it.
