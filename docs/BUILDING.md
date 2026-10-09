# Building

Install JDK 21, Android SDK platform 36, and Android build-tools 36.0.0. Python 3 is needed for installer checks. The scripts use the Android SDK at `$ANDROID_HOME` or `~/Library/Android/sdk`.

Run `./build.sh --unsigned` for a review build. Run `./build.sh` for a signed local APK. The signed build creates a unique RSA signing key at `~/.android/cloud-drift-clock.jks` and its password at `~/.android/cloud-drift-clock.pass` on first use. Back up both outside the repository. Every later update must use the same key. Override these paths with `CLOUD_CLOCK_KEYSTORE` and `CLOUD_CLOCK_KEYPASS`.

The build writes `build/cloud-drift-clock.apk` and `build/cloud-drift-clock.apk.sha256`. CI builds unsigned artifacts; never put a production signing key or password in CI or the repository.
