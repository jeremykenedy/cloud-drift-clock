# Troubleshooting

## The screensaver is not listed

Confirm that installation succeeded with `adb -s SERIAL shell pm list packages | grep com.jeremykenedy.clouddriftclock`. Open the device's screensaver settings again. Some vendor launchers hide Android's DreamService picker or provide a separate ambient-mode menu.

## The installer cannot find the TV

Enable ADB debugging, connect the TV to the same network, and pass its authorized serial as `--serial TV_IP:5555`. Check `adb devices` and accept the authorization prompt on the TV.

## An update cannot install

Updates must be signed with the same release key as the installed app. A local build signed with a different key cannot update the public release in place. Remove the local build before installing a different signer; removing the app also erases its saved settings.

## The clock or palette is unexpected

Open the settings activity and review the individual options and Randomize all. Random values are selected at dream start, so stop and restart the screensaver to resolve them again.
