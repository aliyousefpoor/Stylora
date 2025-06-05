# 💡 StyloraApp

Stylora is an AI-powered fashion assistant designed to help users evaluate their wardrobe by analyzing clothing fit, color coordination, and style trends. You will implement a mobile app that integrates with the Stylora API to allow users to upload photos, receive feedback on their outfits, and explore AI-powered suggestions for improving their style.it built with **Jetpack Compose**, **CleanArch**, **Hilt**.
---

## 🧱 Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **MVVM + Clean Architecture**
- **Hilt (DI)**
- **ViewModel**
- **StateFlow**
  
---

## 🧱 Architecture

- **Presentation Layer**  
  - `GiveFeedbackScreen` 
  - `FeedbackHistoryScreen`
  - `FeedbackViewModel`

- **Domain Layer**  
  - `FeedbackRepository` interface
  - `GiveFeedbackUseCase`
  - `GetFeedbacksUseCase`

- **Data Layer**  
  - `FeedbackRepositoryImpl`  
  - `FeedbackRemoteDataSource`
 
- **Di Layer**  
  - `StyloraModule`  
  - `StyloraInterfaceModule`

---

