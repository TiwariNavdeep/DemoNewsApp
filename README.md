# MatchMate

A simple Android app built with Clean Architecture (MVVM) that fetches random user profiles, allows you to accept or reject them, and manages offline data using a local database.

Features

All Matches – Displays random user profiles with pagination (10 users per page).

My Choice – Shows users you have accepted or rejected, along with their status.

Offline Support –

If the internet is ON, random users are fetched from the API and saved into the local database.

If the internet is OFF, data is loaded from the local database.

Clean Architecture (MVVM) – Ensures clear separation of concerns.

Reactive UI – Uses LiveData to observe and update UI seamlessly.



Libraries Used

Library             | Purpose                                   
------------------- | ----------------------------------------- 
**Retrofit          | For making API calls                      
**Coroutines & Flow | For asynchronous and reactive programming 
**Hilt (Dagger)     | Dependency Injection                      
**Room / Local DB   | For local caching and offline support     
LiveData            | To communicate between ViewModel and UI   


App Flow

App Opens
Checks internet connection
Internet ON → Fetch users via API → Save to local DB → Display in "All Matches" tab.
Internet OFF → Load users directly from local DB.
User taps Accept or Reject → That user appears in My Choice tab with the respective status.



Tech Stack
Kotlin

MVVM + Clean Architecture

Retrofit + Coroutines + Flow

Hilt for Dependency Injection

Room (or Local Database)

LiveData / ViewModel
