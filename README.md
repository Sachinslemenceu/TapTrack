# TapTrack

A low-latency remote PC control system that transforms an Android smartphone into a wireless touchpad using UDP-based communication and QR-powered device pairing.

TapTrack consists of an Android client and a desktop companion application that communicate over a local network to provide responsive cursor control with minimal overhead.

**Version:** 1.1.0


<img width="1536" height="1024" alt="taptrack_hero" src="https://github.com/user-attachments/assets/5772ebd7-37c8-48d0-99b2-f64ae1fd7567" />


---

## Demo

### Product Demo Video

---

## Overview

TapTrack enables users to control a computer remotely using an Android device without requiring additional hardware.

The project was designed with a focus on:

* Low-latency communication
* Efficient network transmission
* Responsive gesture handling
* Scalable software architecture
* Cross-platform system integration

The system consists of:

* Android Application
* Desktop Companion Application
* UDP Communication Layer
* QR-Based Pairing System

---

## System Architecture

```text
┌───────────────────────┐
│   Android Client      │
│  (Jetpack Compose)    │
└───────────┬───────────┘
            │
            │ QR Pairing
            ▼
┌───────────────────────┐
│   UDP Communication   │
│    Custom Protocol    │
└───────────┬───────────┘
            │
            ▼
┌───────────────────────┐
│ Desktop Companion App │
│  Kotlin Multiplatform │
└───────────┬───────────┘
            │
            ▼
┌───────────────────────┐
│ Native Mouse Control  │
└───────────────────────┘
```

---

## Companion Desktop Application

TapTrack requires the companion desktop application to be running on the target machine.

Desktop Repository:

https://github.com/Sachinslemenceu/TapTrack-PC

The desktop application listens for incoming UDP packets, manages active device connections, and translates incoming touch events into native mouse actions.

Both devices must be connected to the same local network.

---

## Key Features

### Real-Time Remote Touchpad

Control a computer cursor directly from an Android device using responsive touch gestures and real-time packet transmission.

### QR-Based Device Pairing

Connect to the desktop application instantly through QR code scanning without manually entering IP addresses or ports.

### Low-Latency UDP Communication

Uses UDP networking and a lightweight custom packet structure to reduce transmission overhead and improve responsiveness.

### Custom Binary Protocol

Implements optimized packet encoding using manual byte conversion techniques to minimize packet size and processing time.

### Desktop Companion Integration

Works seamlessly with the TapTrack desktop client to provide real-time device connectivity and control.

### Session Persistence

Maintains authenticated user sessions and restores application state automatically on launch.

### Network Awareness

Monitors Wi-Fi connectivity and guides users through the connection process when required.

### Production-Oriented Architecture

Built using modern Android development practices with a strong focus on maintainability and scalability.

---

## Technical Highlights

### Networking

* UDP Socket Communication
* Datagram Packet Transmission
* Custom Binary Packet Protocol
* Low-Overhead Message Encoding
* Local Network Device Discovery

### Android Development

* Kotlin
* Jetpack Compose
* Coroutines
* State Management
* Navigation Compose

### Architecture

* Clean Architecture
* Model-View-Intent (MVI)
* Dependency Injection
* Repository Pattern
* Lifecycle-Aware Components

### Backend Services

* Firebase Authentication
* Preference DataStore

---

## Project Structure

```text
app
│
├── di
│
├── core
│
└── features
    │
    ├── authentication
    │   ├── ui
    │   ├── domain
    │   └── data
    │
    ├── connection
    │   ├── ui
    │   ├── domain
    │   └── data
    │
    └── mousepad
        ├── ui
        ├── domain
        └── data
```

---

## Development Approach

TapTrack follows Clean Architecture principles to maintain clear separation of concerns between business logic, presentation logic, and data management.

```text
Presentation Layer
        │
        ▼
ViewModel (MVI)
        │
        ▼
Use Cases
        │
        ▼
Repositories
        │
        ▼
Data Sources / Services
        │
        ▼
Firebase / DataStore / UDP Layer
```

This architecture improves:

* Maintainability
* Testability
* Scalability
* Feature Development Speed

---

## Installation

### Android Application

Clone the repository:

```bash
git clone <repository-url>
```

Open the project in Android Studio and run it on a physical Android device connected to the same local network as the desktop application.

### Desktop Companion

Clone the desktop repository:

```bash
git clone https://github.com/Sachinslemenceu/TapTrack-PC
```

Launch the desktop application and scan the generated QR code using the Android application.

---

## Current Capabilities

Version 1.1.0 currently supports:

* User Authentication
* Session Persistence
* QR-Based Device Pairing
* Real-Time Cursor Control
* UDP Communication
* Desktop Integration
* Local Network Connectivity Management

---

## Roadmap

Future improvements under consideration:

* Right Click Support
* Two-Finger Scrolling
* Drag and Drop Operations
* Media Controls
* Multi-Monitor Support
* Custom Gesture Mapping
* Keyboard Input Support
* File Transfer Functionality

---

## Why TapTrack?

TapTrack was developed as a real-world networking and systems engineering project that combines Android development, desktop development, network programming, and software architecture principles into a complete end-to-end product.

The project demonstrates:

* Real-Time Communication Systems
* UDP Network Programming
* Cross-Platform Development
* Mobile Application Engineering
* Software Architecture Design
* Product-Oriented Development

---

## Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Submit a pull request

---

## Author

Sachin Pradeep Singh

Software Engineer focused on Android Development, Real-Time Systems, Product Engineering, and Cross-Platform Applications.

---

## License

This project is licensed under the MIT License.
