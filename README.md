# 🖱️ TapTrack

**TapTrack** is an Android-based remote mouse control app built with **Kotlin**, following the **MVI** pattern and **Clean Architecture** principles. It allows users to seamlessly control their computer using their smartphone with an intuitive, responsive interface.

---

## 🚀 Features Implemented

✅ **Splash Screen**  
- Beautiful animations using [Lottie](https://airbnb.io/lottie/) for a modern and smooth user experience.

✅ **User Authentication**  
- Secure Email/Password login using **Firebase Authentication**.

✅ **Login Session Persistence**  
- Seamless login experience using **Preference DataStore** to retain sessions.

✅ **Home Page UI**  
- Fully designed home screen with real-time mousepad interaction area.
- Gesture handling with **pointerInput** for touch tracking.

✅ **Core Mouse Control (Work in Progress)**  
- Cursor movement with finger gestures.
- Optimized touch detection using `pointerInput` modifier for drag and tap responsiveness.

✅ **Right-click, Drag, and Scroll Support (Partially Integrated)**  
- Multitouch and long-press gesture experiments in progress.

✅ **Inbuilt Wi-Fi Connection Management**  
- Integrated with Android system services to show current Wi-Fi status and prompt network connection if not connected.

✅ **Session-Aware Navigation**  
- Automatically bypasses splash/login screens if session is active.

✅ **Clean App Exit**  
- Pressing the back button from Home exits the app instead of going to splash/login screen.

✅ **Socket Management**  
- Lifecycle-aware socket connection: socket auto-closes when view is destroyed or app exits.

✅ **MVI Architecture**  
- Built using a scalable **Model-View-Intent** approach for unidirectional data flow and easier debugging.

✅ **Clean Architecture**  
- Modular project structure separated into:
  - **UI Layer** – Jetpack Compose-based frontend
  - **Domain Layer** – Use cases and business logic
  - **Data Layer** – Firebase, preferences, network handling

✅ **Dependency Injection with Koin**  
- Lightweight and lifecycle-aware DI setup for seamless component access.

✅ **Pointer Tracking with Jetpack Compose**  
- Leveraged `pointerInput` for custom drag and tap handling on the virtual touchpad.

✅ **UI and UX Optimizations**  
- Fixed navigation flow
- Better gesture response tuning
- Reworked screen transitions and back-stack logic

---

## 🧩 Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Firebase Authentication**
- **Preference DataStore**
- **Koin** (Dependency Injection)
- **MVI Architecture**
- **Clean Architecture (UI + Domain + Data Layers)**
- **Android System Services**
- **Lottie Animations**

---


## 🧠 Contribution

Currently a open source project, but contributors are welcome to find bugs and add changes.

---


---

## 👨‍💻 Author

Built with ❤️ by **Sachin Pradeep Singh**  

---

## 📄 License

MIT License (Will be updated if changed)

---

