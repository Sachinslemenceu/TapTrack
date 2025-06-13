

# 🖱️ TapTrack

**TapTrack** is an Android-based remote mouse control app built with **Kotlin**, following the **MVI** pattern and **Clean Architecture** principles. It allows users to seamlessly control their computer using their smartphone with an intuitive, responsive interface.It is optimized to have the minimum latency as possible. It uses **UDP Protocol** to connect to the Pc Server.The Pc Server code repo is attached at the end of the page.

---

## 🚀 Demo Example

- Demo Screen Shots
![Screenshot_2025-06-13-19-41-10-363_com slemenceu taptrack](https://github.com/user-attachments/assets/ac1fbf9a-27f0-4117-93e2-69f8ac17af1d)
![Screenshot_2025-06-13-19-33-25-110_com slemenceu taptrack](https://github.com/user-attachments/assets/4ca384e7-8fb0-4ab1-b303-719a611081e0)
![Screenshot_2025-06-13-19-33-27-662_com slemenceu taptrack](https://github.com/user-attachments/assets/9344bfea-3f82-45d5-afc6-1acff3039747)
![Screenshot_2025-06-13-19-33-06-646_com slemenceu taptrack](https://github.com/user-attachments/assets/84cbd71b-8d18-4256-a497-bbb6a6d00c04)

- Demo Video

https://github.com/user-attachments/assets/22a617ac-5131-42dc-a94e-1f80eeea9270


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
- Developed a **custom Protocol** that minimize the latency.
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

