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

```sh
rm -rf build .gradle app/build app/.gradle
gradle --stop
gradle --status
gradle clean :app:assembleDebug --no-build-cache
# gradle --stacktrace :app:installDebug

# gradle wrapper --gradle-version 7.5 --distribution-type all

# install
adb install -r app/build/outputs/apk/debug/app-debug.apk
```