# SpeechRecognizationCar

Android application to control a smart car using voice commands over a TCP socket.

## Key Highlights

- Connects to car controller via `IP:PORT`
- Voice command capture with Android speech recognition
- Security keypad gate before voice control is enabled
- Sends command text directly over socket connection
- Lightweight Java + AndroidX implementation

## Demo Flow

1. Enter car endpoint in the address field (example: `192.168.1.10:9090`).
2. Tap **Press To connect**.
3. After successful connection, unlock with security code.
4. Tap microphone and speak a command.
5. App sends recognized text to the car server.

## Tech Stack

- Java (Android)
- AndroidX AppCompat + Material Components
- Speech Recognition API (`RecognizerIntent`)
- Socket networking (`java.net.Socket`)

## Configuration

- `compileSdkVersion`: `29`
- `targetSdkVersion`: `29`
- `minSdkVersion`: `23`
- Package: `com.example.speechrecognizationcar`

## Permissions

- `INTERNET`
- `ACCESS_WIFI_STATE`
- `ACCESS_NETWORK_STATE`
- `RECORD_AUDIO`

## Project Structure

- `app/src/main/java/com/example/speechrecognizationcar/MainActivity.java` - App logic (connection, security, speech, socket send)
- `app/src/main/res/layout/activity_main.xml` - UI layout
- `app/src/main/AndroidManifest.xml` - Permissions and app metadata

## Build

### Android Studio

1. Open the project.
2. Sync Gradle.
3. Run on physical device/emulator.

### CLI

```bash
./gradlew assembleDebug
```

Debug APK output:
`app/build/outputs/apk/debug/`

## Notes

- Current security code in source is `2`.
- Commands are sent as raw recognized text.
- Cleartext traffic is enabled for LAN/testing environments.

## Author

Ankur Shah
