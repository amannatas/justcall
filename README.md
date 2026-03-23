📞 JustCall - Modern Android Calling UI
JustCall is a sleek, gesture-based calling application prototype built with Kotlin and Android Jetpack. It demonstrates advanced UI/UX principles, including real-time state simulation, gesture-based interactions (swipe-to-answer), and modern Android navigation.

✨ Key Features
Smart Dialer: Interactive dial pad with 10-digit input validation and backspace functionality.

Swipe-to-Action: Intuitive swipe gestures for handling calls:

Swipe Right: Accept the incoming call.

Swipe Left: Decline/Reject the incoming call.

Live Call Simulation: Realistic transition from Outgoing -> Incoming -> Active states without needing a backend.

Dynamic UI Animations: * Vertical bouncing "Pulse" effect during incoming calls.

Real-time call duration timer (Chronometer).

Smooth screen transitions.

Haptic Feedback: Integrated vibration patterns for a realistic ringing experience.

Modern Back Handling: Migrated to AndroidX OnBackPressedDispatcher for compatibility with Android 13+ Predictive Back Gestures.

🛠️ Technical Stack
Language: Kotlin

UI Framework: XML (ConstraintLayout, Material Design 3)

Architecture: Modular Event-driven logic

Minimum SDK: API 24 (Android 7.0)

Target SDK: API 34 (Android 14)

📱 Application Flow
Dialer Screen: Enter a 10-digit number.

Outgoing Screen: Simulated "Calling" state for 3 seconds.

Incoming Screen: Buttons bounce and phone vibrates. User can swipe to Accept or Reject.

Active Screen: Displays call timer, Mute, and Speaker toggles.

End Call: Clears the activity stack and returns safely to the Dialer.

🚀 How to Run
Clone the repository.

Open in Android Studio (Hedgehog or later).

Build and run on a physical device (recommended for Vibration testing) or Emulator.

Permissions: Ensure CALL_PHONE and VIBRATE permissions are granted.
