# Android Quiz App

This is a mobile application developed in Android Native (Java) that allows users to take a short quiz on general knowledge. The app includes user authentication, a dynamic question system, scoring, and performance tracking.

## Features

### ✅ User Authentication
- User registration and login via **Firebase Authentication**.
- Secure access to the quiz is granted only after successful login.

### ✅ Quiz System
- The app fetches **20 questions** from **Firebase Realtime Database**.
- On each quiz attempt, **5 random questions** are selected to ensure variation.
- A single activity dynamically loads each question to optimize performance.
- Each question has a **20-second countdown timer**.
  - If the user doesn’t answer within the time limit, the app automatically skips to the next question with a score of 0 for the unanswered one.

### ✅ Real-Time Feedback and Score Tracking
- At the end of the quiz, the user's **total score is displayed**.
- All scores are saved to **Firebase Database**, allowing long-term tracking.

### ✅ Ranking System
- The app includes a **ranking page** that lists all users' scores in **descending order**, promoting competition and motivation.

### ✅ Answer Review
- After finishing the quiz, users can review their answers:
  - Each question is displayed with:
    - The user's selected answer
    - The correct answer
- This review is implemented using a **RecyclerView** for smooth and scalable display.

## Technologies Used
- Android Native (Java)
- Firebase Authentication
- Firebase Realtime Database
- XML for UI design

## Getting Started
To build and run the project:
1. Clone the repository.
2. Open it in Android Studio.
3. Set up Firebase with your own configuration.
4. Run the application on an emulator or physical device.

---
Build with passion and ❤️ by 👩‍💻 Soukaina LAKBICHI
