# App for sending curl requests to localhost
## preparations
### java JDK
```sh
# switch to java 17 (`sdk install java 17.0.17-zulu`)
java -version
```
### android command line tools
```sh
mkdir /home/soft/android_sdk
pushd /home/soft/android_sdk
curl --silent https://developer.android.com/studio | grep commandlinetools-linux | grep href
wget https://dl.google.com/android/repository/commandlinetools-linux-13114758_latest.zip
unzip commandlinetools-linux-*.zip
```
```sh
pushd /home/soft/android_sdk
# accept all licenses,         the root should be the same as in local.properties
./cmdline-tools/bin/sdkmanager --sdk_root=/home/soft/android_sdk --licenses
```
```sh
pushd /home/soft/android_sdk
./cmdline-tools/bin/sdkmanager --sdk_root=/home/soft/android_sdk --install "platform-tools" "platforms;android-34" "build-tools;34.0.0"
# check with app/build.gradle `android { compileSdk 34
```

### android vs gradle
> Android Gradle Plugin 8.2.2 is not compatible with Gradle 9.x 
```sh
gradle wrapper --gradle-version 8.3 --distribution-type all
./gradlew --version
```

## build
```sh
rm -rf build .gradle app/build app/.gradle
gradle --stop
gradle --status
./gradlew --stop
./gradlew --status

# build/rebuild
./gradlew clean :app:assembleDebug --no-build-cache
ls -la app/build/outputs/apk/debug/app-debug.apk
```

## install application on physical android device
```sh
adb devices
# if no devices: https://github.com/cherkavi/cheat-sheet/blob/master/android-cheat-sheet.md#adb
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## [test application using http server](./http_test_server/server.py)

### start server
```sh
python3 http_test_server/server.py
```

### test requests
```sh
curl -X POST http://localhost:8000/posts -H "Content-Type: application/json" -d '{"msg":"hello"}'
curl -X POST http://localhost:8000/posts -d 'plain text body'
```