
## Overview

This repository contains two Android applications: `ClipServer` and `AudioClient`, developed as part of the coursework for CS478 at [University Name]. The `ClipServer` app is designed to manage and play audio clips, while `AudioClient` interacts with `ClipServer` to control audio playback.

### ClipServer

The `ClipServer` app stores and manages audio clips, providing functionality to play, pause, resume, and stop playback. It includes a service that runs in the foreground and exposes an API for client apps to interact with.

**Key Features:**
- Stores audio clips numbered from 1 through n.
- Foreground service for continuous playback control.
- API supports play, pause, resume, and stop operations.

### AudioClient

The `AudioClient` app serves as a frontend to interact with the `ClipServer` service. It allows users to control the playback of audio clips provided by `ClipServer`.

**Key Features:**
- Interface to start/stop the service and manage audio playback.
- Binds to `ClipServer` service for audio control.
- Provides UI elements for playback controls like play, pause, and stop.

## Setup

To set up these projects, follow the steps below:

1. Clone the repository to your local machine or download the project zip.
2. Open each project separately in Android Studio.
3. Build the project using Android Studio or using the command line with `./gradlew build`.
4. Ensure you have an Android Emulator set up with API level 34 (Android 14) or use a physical device with the same API level.

## Running the Applications

1. Start the `ClipServer` app first to ensure the service is available when `AudioClient` starts.
2. Launch `AudioClient` and use its interface to control the audio playback.
3. The apps are designed to function in portrait mode on Pixel 5 devices or similar.

## Development Notes

- Use `startForegroundService()` and `bindService()` to manage service lifecycle.
- Implement robust error handling to manage unexpected service terminations.
- Design the UI to gracefully handle enabled/disabled states as per service availability.

## Testing

Test the applications to ensure they meet the functional requirements and handle edge cases like service interruptions. The UI should reflect the current state of the service, and playback controls should be enabled/disabled appropriately.
