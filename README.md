# Hangman Game (In progress)

Hangman Game (Jogo da forca in pt-BR) is a mobile application developed using Jetpack Compose and Kotlin, providing a native mobile experience. It is a recreation of the classic game of hangman where players guess letters to uncover a hidden word. This project serves as a personal learning experience, aiming to build a game from scratch while incorporating best practices and following up-to-date development standards.

## Features

- Interactive gameplay: Guess letters to reveal the hidden word.
- Randomly generated words: Enjoy a variety of words to guess from a predefined word bank.
- Visual feedback: See the progress of the word and the incorrect guesses through a user-friendly interface.

## Prerequisites

Before running the Hangman Game project, ensure that you have the following:

- Android Studio: The latest version of Android Studio is recommended for an optimal development experience.
- Kotlin: Familiarity with the Kotlin programming language will be beneficial.
- Jetpack Compose: Understanding the basics of Jetpack Compose is essential to comprehend the structure of the project.

## Installation

1. Clone the repository:

   ```shell
   git clone https://github.com/machado001/hangman
2. Open the project in Android Studio.

3. Build and run the application on your emulator or physical device.

## Project Structure
The project follows a well-organized structure to ensure maintainability and scalability. Here's an overview of the main directories and files:

- `app/src/main`: Contains the main source code of the Hangman Game application.
  - `java/com/example/hangmangame`: Contains the Kotlin source code for the application.
    - `data`: Contains the data layer, handling game-related data and logic (actual it's just the words hardcoded).
    - `ui`: Contains the user interface components and screens built using Jetpack Compose.
  - `res`: Contains the application resources, such as layouts and strings.

## Documentation
To understand the project's codebase and learn more about the implementation details, refer to the following resources:

- **Jetpack Compose documentation**: Explore the official documentation for Jetpack Compose to understand the fundamental concepts and APIs used in the project. Visit [compose.android.com](https://compose.android.com) to access the documentation.
- **Compose samples repository**: The `compose-samples` repository provides a collection of sample projects demonstrating different aspects of Jetpack Compose. It can serve as a valuable reference to enhance your understanding. Find the repository at [github.com/android/compose-samples](https://github.com/android/compose-samples).
- **Android source code**: The official Android source code repository can be a valuable resource to explore the inner workings of the Android platform. Visit [cs.android.com](https://cs.android.com) to access the codebase.

Please note that the project primarily relies on the official documentation, compose-samples, and Android source code. Limiting external references ensures a deeper learning experience and encourages self-sufficiency in tackling challenges.

## Contributing
You're welcome. 
