# SpeechRecognizationCar

Android app to control a smart car using voice commands over a TCP socket connection.

## Overview

This app connects to a car controller server using `IP:PORT`, unlocks voice input with a security code, captures speech with Android Speech Recognition, and sends the recognized command text to the car via socket.

## Features

- TCP connection to car server (`host:port`)
- Security keypad gate before voice control
- Voice recognition using `RecognizerIntent`
- Sends recognized text command directly to socket output stream
- Simple UI with connection panel and secure command panel

## Tech Stack

- Android (Java)
- `compileSdkVersion 29`, `targetSdkVersion 29`, `minSdkVersion 23`
- AndroidX + Material Components

## Project Structure

- `app/src/main/java/com/example/speechrecognizationcar/MainActivity.java` - Core app logic
- `app/src/main/res/layout/activity_main.xml` - Main UI layout
- `app/src/main/AndroidManifest.xml` - Permissions and app config

## Permissions Used

- `INTERNET`
- `ACCESS_WIFI_STATE`
- `ACCESS_NETWORK_STATE`
- `RECORD_AUDIO`

## How It Works

1. Enter car address in format `127.0.0.1:9090`.
2. Tap **Press To connect**.
3. If connected, secure panel appears.
4. Tap keypad code to unlock voice button (current code in source is `2`).
5. Tap microphone and speak command.
6. Recognized text is sent to the connected socket.

## Build and Run

### Option 1: Android Studio

1. Open project in Android Studio.
2. Let Gradle sync.
3. Run on device/emulator (Android 6.0+).
4. Grant microphone permission when prompted.

### Option 2: Command Line

```bash
cd "/Users/ankurshah/Documents/New project/SpeechRecognizationCar"
./gradlew assembleDebug
```

APK output path (default):
`app/build/outputs/apk/debug/`

## Notes

- The app sends plain text commands exactly as recognized.
- Security code is hardcoded in `MainActivity` as `"2"`.
- Manifest allows cleartext traffic (`android:usesCleartextTraffic="true"`) for local network communication.

## Author

Ankur Shah
