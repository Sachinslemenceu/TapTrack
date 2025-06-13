

# 🖱️ TapTrack

**TapTrack** is an Android-based remote mouse control app built with **Kotlin**, following the **MVI** pattern and **Clean Architecture** principles. It allows users to seamlessly control their computer using their smartphone with an intuitive, responsive interface.It is optimized to have the minimum latency as possible. It uses **UDP Protocol** to connect to the Pc Server.The Pc Server code repo is attached at the end of the page.

---

## 🚀 Demo Example

![Screenshot 2025-06-13 190302](https://github.com/user-attachments/assets/ff6a5c55-d694-4ce0-ba2b-c1c4d8d28845)
![Screenshot 2025-06-13 190324](https://github.com/user-attachments/assets/c7a8acaf-b0a3-4c97-82a0-d7fcd6558224)
![Screenshot 2025-06-13 190337](https://github.com/user-attachments/assets/e3647a7d-70e0-4234-b3b1-91ca32e92423)
![Screenshot 2025-06-13 190357](https://github.com/user-attachments/assets/0599c740-9d00-4010-b245-b922362919a2)

https://github.com/user-attachments/assets/238a902d-a22f-4002-b4bb-6548addaad9c

---
## 🚀 Features Implemented

✅ **Splash Screen**  
- Animations using [Lottie](https://airbnb.io/lottie/) for a modern and smooth user experience.

✅ **User Authentication**  
- Secure Email/Password login using **Firebase Authentication**.

✅ **Login Session Persistence**  
- Seamless login experience using **Preference DataStore** to retain sessions.

✅ **Home Page UI**  
- Fully designed home screen with real-time mousepad interaction area.
- Gesture handling with **pointerInput** for touch tracking.

✅ **Socket Management**  
- Uses **Datagram packets** to send user inputs to the PC Server.
- Uses **Manual int to Byte Conversions** to **optimize delays** while sending packets via UDP.
- Lifecycle-aware socket connection: socket auto-closes when view is destroyed or app exits.

✅ **Core Mouse Control**  
- Cursor movement with finger gestures.
- Optimized touch detection using the `pointerInput` modifier for drag and tap responsiveness.

✅ **Right-click, Drag, and Scroll Support (Partially Integrated)**  
- Multitouch and long-press gesture experiments in progress.

✅ **Inbuilt Wi-Fi Connection Management**  
- Integrated with Android system services to show current Wi-Fi status and prompt network connection if not connected.

✅ **Session-Aware Navigation**  
- Automatically bypasses splash/login screens if session is active using **Preference DataStore**.

✅ **MVI Architecture**  
- Built using a scalable **Model-View-Intent** approach for unidirectional data flow and easier debugging.

✅ **Clean Architecture**  
- Modular project structure separated into:
  - **UI Layer** – Jetpack Compose-based frontend
  - **Domain Layer** – Use cases and business logic
  - **Data Layer** – Firebase, preferences, network handling

✅ **Dependency Injection with Koin**  
- Lightweight and lifecycle-aware DI setup for seamless component access.

---

## 🧩 Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Firebase Authentication**
- **Preference DataStore**
- **Koin** (Dependency Injection)
- **MVI Architecture**
- **Socket Programming (UDP Protocol)**
- **Clean Architecture (UI + Domain + Data Layers)**
- **Android System Services**
- **Lottie Animations**

---


## 🧠 Contribution

Currently an open-source project, but contributors are welcome to fork the repo and commit changes.

---

## 👨‍💻 Author

Built with ❤️ by **Sachin Pradeep Singh**  

---

## 📄 License

MIT License (Will be updated if changed)

---

