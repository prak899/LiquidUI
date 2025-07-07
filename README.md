# 📱 LiquidUI SDK — Server-Driven UI Rendering Engine for Android (PhonePe-style)

## 🚀 Introduction

Welcome to the **LiquidUI SDK Demo**, a fully functional, production-grade Server Driven UI (SDUI) rendering engine designed for Android using Kotlin. This SDK allows an Android application to render complex and dynamic UIs directly from JSON configurations at runtime — without requiring an app update. Inspired by **PhonePe’s dynamic home page architecture**, this demo brings real-world production-grade elements to life, such as:

- Dynamic Carousel (Auto-scroll + Dot indicators)
- Horizontally scrollable feature buttons
- Grid-based icon cards with padding, elevation, and shadows
- Full-width banners with overlay text
- Navigation via URL or Android Intents

---

## 🎯 Objective

The purpose of this project is to showcase how to build a **Server-Driven UI system** (like PhonePe or Paytm) on Android using native Views (not Jetpack Compose), emphasizing flexibility, maintainability, and modular design. The key objectives achieved in this SDK include:

- Rendering complete home screens from JSON.
- Full Carousel implementation with auto-scrolling and dot indicators.
- Reusable Grid and List renderers supporting varying column and item counts.
- On-click actions driving URL opens or explicit navigation via Intents.
- Demonstration of real-world complex layouts — not dummy screens.
- Clean, modular architecture enabling reuse and easy extension.

---

## 🏗️ Tech Stack

- **Language:** Kotlin
- **UI Components:** ViewPager2, RecyclerView, LinearLayout, GridLayout, CardView, ConstraintLayout
- **Image Loading:** Glide
- **JSON Parsing:** Gson
- **Minimum SDK:** 21+
- **Tested Android Version:** Android 12+

---

## 🔧 Features Implemented

1. **Server-Driven JSON UI:**
   - JSON read from `res/raw/liquid_ui.json` (can be changed to server fetch).
   - Parsed using Gson into data classes (`UIModel`).
   - Fully dynamic rendering based on JSON structure.

2. **Dynamic Carousel (PhonePe-like):**
   - ViewPager2-based.
   - Auto-scroll using Handler & Runnable.
   - Dot Indicator reflects carousel position.
   - On-click actions trigger Activity navigation via Intent.

3. **Grid Layout Rendering:**
   - Grid with adjustable column count.
   - Each item: CardView + elevation + padding + shadow + icon + text.
   - Real icons loaded via URL (Glide).
   - Click event shows Toast or navigates via Intent.

4. **Horizontal List Rendering:**
   - Circle icon buttons scrollable horizontally using RecyclerView.
   - Each item dynamically created from JSON.

5. **Banner Rendering:**
   - Full-width banner image.
   - Custom overlay text ("Prakash Majhi (Android Engineer)") centered.
   - Click action supports navigation.

6. **Event Handling:**
   - `"type": "navigate"` — open web URL.
   - `"type": "navigate_intent"` — open specific Activity via Intent.
   - Custom extras supported in Intent.

7. **Reusable & Extendable Architecture:**
   - Clean rendering logic via `LiquidUIRenderer`.
   - Easily add new UI types (e.g., rating cards, offer tiles).

---

8. **Indiaction**
   - Typically it indicates that a new UI has been introduce and it will apply this automatically.
