

# 🖱️ TapTrack

**TapTrack** is an Android-based remote mouse control app built with **Kotlin**, following the **MVI** pattern and **Clean Architecture** principles. It allows users to seamlessly control their computer using their smartphone with an intuitive, responsive interface.It is optimized to have the minimum latency as possible. It uses **UDP Protocol** to connect to the Pc Server.The Pc Server code repo is attached at the end of the page.

## 📂 Companion Desktop App

This mobile application pairs with the official TapTrack PC client:

👉 [**TapTrack PC**](https://github.com/Sachinslemenceu/TapTrack-PC.git)  
A Kotlin Multiplatform Desktop app that listens for connections via UDP and displays real-time data about connected devices.

Make sure to have the PC client running on the same local network for seamless discovery and interaction.
---

## 🚀 Demo Example

- Demo Screen Shots

<p align="center">
  <img src="https://github.com/user-attachments/assets/ac1fbf9a-27f0-4117-93e2-69f8ac17af1d" width="15%" />
  <img src="https://github.com/user-attachments/assets/acd89a82-982d-4d86-97e6-716b2c4e2b5b" width="15%" />
  <img src="https://github.com/user-attachments/assets/530b3cff-0ecf-44e7-b045-0b4a11765d20" width="15%" />
  <img src="https://github.com/user-attachments/assets/79610e13-063f-4d3a-9ef7-688f24f9ea94" width="15%" />
  <img src="https://github.com/user-attachments/assets/2ba422f3-f0df-47ac-8a89-1f3f4aac2e3a" width="15%" />
</p>
<p align="center">
  <img src="https://github.com/user-attachments/assets/28ab9701-3e20-47ec-a5fb-92fd6b40ac2b" width="15%" />
  <img src="https://github.com/user-attachments/assets/b14b04e2-2e31-4950-9d7f-5a46de18de41" width="15%" />
  <img src="https://github.com/user-attachments/assets/91f527a0-805c-4065-bfbc-96546a1b0b59" width="15%" />
  <img src="https://github.com/user-attachments/assets/db73e8c1-a046-42c5-9313-0c43f7111c5a" width="15%" />
</p>
<p align="center">
  <img src="https://github.com/user-attachments/assets/eaa1a16f-ae68-4285-b78e-0b9874a411d6" width="40%" />
  <img src="https://github.com/user-attachments/assets/ff599fe6-e1d5-482b-81e4-ca338f636fc9" width="40%" />
</p>

- Demo Video



https://github.com/user-attachments/assets/b0efb37d-fcd2-464b-9020-455da7a69c67




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
- Lifecycle-aware socket connection: socket auto-closes when the view is destroyed or the app exits.

✅ **QR Code Scanner**  
- Uses **ZXing Library** to implement QR Code Scanner.
- It is used for a secure and convenient way to connect to the PC Server.
- https://github.com/zxing/zxing
  
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

MIT License

---

