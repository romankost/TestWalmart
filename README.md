# Coding Assessment – Software Engineer Mobile (Android)

This repository contains the solution to the **Mobile Software Engineer coding assessment** for Android.  
The implementation focuses on fetching and displaying a list of countries in a RecyclerView, following the specifications in the exercise.

---

## Task Description

**Programming Exercise**

1. Fetch a list of countries in JSON format from this URL:  
   [Countries JSON](https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json)

2. Display all the countries in a **RecyclerView** ordered by their position in the JSON.  
   
3. Requirements:
- The user should be able to **scroll** through the entire list.
- The implementation should be **robust** (handle errors and edge cases).
- Support **device rotation**.
- **Do not use Jetpack Compose or dependency injection (Dagger/Hilt).**
- Focus is on **high-quality code**, not the number of features.
---

## Tech Stack

- **Architecture / Pattern:** MVI
- **Asynchronous:** Kotlin Coroutines, Flow
- **Networking:** Retrofit
- **UI:** RecyclerView, DataBinding
- **Testing:** JUnit, Mockito, Turbine
