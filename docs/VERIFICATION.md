# Device verification

## Version 2 filmed candidate

Physical Fire TV AFTALMO, API 28, October 26, 2026 UTC. Native PNG capture size is 1920 by 1080. This is not a native 4K claim.

The tested rendering candidate is version 2.0.0/code 4, SHA-256 `37302d097a236fc254d6160a40c89d931b4b3b331bb481a549ee018a023dd25b`. Final code 5 SHA-256 is `abb8fdf3abdd1c94b95106b6bd0cbdf85822ccffd4caec9cdbd6dcb99919fae8`, 40,297,575 bytes. It changes Credits focus and includes the current native poster. Its signed replacement retained all nine provider choices, actual Credits scrolling/Right/Close passed, and word-free Preview, an eight-second moving capture and Back passed. The installed hash was verified after strict cleanup.

| Check | Result |
| --- | --- |
| Same-signer replacement update | Installed hash verified; all nine provider values retained |
| Seven effective native controls | D-pad changes and provider readback passed for playback speed, Show clock, size, movement, format, seconds and randomization |
| Word-free scene | Show clock Off with randomization On produced no clock or other scene text |
| Clock style and movement | Large 24-hour time with seconds and wide drift visibly changed position over fifteen seconds |
| Preview lifecycle | D-pad Preview, Back and a 35-second moving capture completed |
| Slow, clock Off actual Dream | Normal non-test Dream attached after 300.383 seconds with unchanged TV timeout; Back detached it |
| Fast, clock On actual Dream | Normal non-test Dream attached after 300.948 seconds with unchanged TV timeout; Back detached it |
| Decoder | `OMX.amlogic.avc.decoder.awesome`, H264, 1920 by 1080; reported zero dropped frames |
| Restore | Fresh final baseline used Fire TV UI code 57. All 49 portable UI values, 13 observed OS values, available surrounding providers, external backup/import bytes and appop modes matched; native protection remained running, Dream detached and Main was restored. A cleanup-only fresh grant completed final readback after the earlier lease expired; no expired-lease device calls were made |
| Credits | Original focus issue was fixed in code 5; actual initial Close focus, Up/Down scrolling, Right to Close and Center dismissal passed |

### Rendering equivalence

All five bundled media/license assets in final code 5 are byte-identical to the native-tested code 4 artifact. The complete DEX differs because Credits UI changed. Normalized decompilation compares ten unchanged classes exactly, including the renderer, clock overlay, options, provider, DreamService and Preview; only SettingsActivity changes. Version metadata and the native poster also change. This is semantic renderer equivalence, not a claim that the whole APK or DEX is byte-identical.

### Cadence and measurement limits

The film source is 25fps. Playback at 0.75x retains each source frame longer, giving 18.75 source frames per elapsed second; 1.25x corresponds to 31.25. These are content timings, not display refresh rates.

During the slow normal Dream, decoder metrics reported 305 frames over 16.154 seconds and zero drops. During the fast normal Dream, they reported 515 frames over 16.509 seconds and zero drops. Decoder counts are not SurfaceFlinger presentation proof.

Without screenrecord running, three short buffers from the actual generic `Sys2023:dream` window in the fast clock-on case measured 34.208, 34.562 and 35.046 composite presents/second. Those include clock updates. Container layers returned no valid samples and were excluded. Samples overlap and do not establish a continuous full-duration frame rate.

The first slow-case presentation sampler selected hidden Settings layers and returned no valid samples. That case has no presentation-rate pass. Recorded movies also have capture overhead and must not be described as physical-display FPS measurements. No sustained thermal, idle-power or native 4K results are claimed.

### Footage and loop limits

This is actual sunset cloud-bank flight with nearby vapor, far mountains and camera banking. The 34-second loop uses a 1.2-second editorial dissolve, not a naturally closed camera path. Moving previews were reviewed. This evidence covers the single cinematic scene, not other cloud environments. Palette and density are retained preferences, inactive for this single filmed scene.

### Other platforms

Current version 2 testing is still needed on other Fire TV hardware, Android TV hardware and Google TV hardware. Volunteers with those devices are needed to report model, OS/API, resolution, navigation, playback and Dream behavior. Earlier API 31 emulator results apply to version 1's photograph renderer only and do not validate this filmed version.
