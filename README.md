# GomaChallenge - CryptoTracker

A real-time cryptocurrency price tracking Android app built with Kotlin, Jetpack Compose, and Clean Architecture with a MVVM pattern.

## Features

- Live prices for multiple cryptocurrencies via Binance WebSocket API
- Visual indicators for price changes (color + arrows)
- Clean MVVM + Compose UI architecture
- Handling of network interruptions

## Tech Stack

- Kotlin
- Jetpack Compose
- Coroutines + StateFlow
- OkHttp WebSocket
- Clean Architecture (MVVM)
- Binance API: [WebSocket Ticker](https://developers.binance.com/docs/binance-spot-api-docs/web-socket-streams)

## Unit Tests

Basic ViewModel unit tests located in our `/test` folder.

## AI Usage

- **Tools**: ChatGPT, DeepSeek, GitHub Copilot.
- **Used for**: Architecture planning, parsing Binance JSON, StateFlow setup and Compose UI.
- **Validation**: Handpicked all the code suggestions in Android Studio.

## Project Structure

- com.example.gomachallenge
- ├── data
- ├── domain
- ├── presentation
- ├── util
- ├── AppNavigation.kt
- └── MainActivity.kt

## Setup

```bash
git clone https://github.com/yourusername/GomaChallenge.git
open with Android Studio