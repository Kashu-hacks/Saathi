# Saathi — Postpartum Support App (MVP)

Saathi is an AI-powered postpartum support app that helps new mothers track their
emotional well-being and helps family members (husband, mother-in-law, other family
members) support them effectively.

Stack: **Kotlin, Jetpack Compose, MVVM, Firebase Auth, Firebase Firestore, Material 3**

---

## 1. Project Structure

```
Saathi/
├── build.gradle                     # Root Gradle config (plugin versions)
├── settings.gradle                  # Module + repository config
├── gradle.properties
└── app/
    ├── build.gradle                 # App-level dependencies
    ├── proguard-rules.pro
    └── src/main/
        ├── AndroidManifest.xml
        ├── res/values/
        │   ├── strings.xml
        │   └── themes.xml
        └── java/com/saathi/app/
            ├── SaathiApplication.kt         # Initializes Firebase
            ├── MainActivity.kt              # Single-activity host
            │
            ├── data/
            │   ├── model/                   # Plain data classes (Firestore-mappable)
            │   │   ├── User.kt
            │   │   ├── UserRole.kt
            │   │   ├── MoodEntry.kt         # + MoodType enum
            │   │   ├── ChatMessage.kt
            │   │   └── FamilyActivity.kt
            │   └── repository/              # All Firebase/data-source access lives here
            │       ├── AuthRepository.kt        # FirebaseAuth wrapper
            │       ├── UserRepository.kt        # users/{uid} Firestore doc
            │       ├── MoodRepository.kt        # users/{uid}/moodEntries subcollection
            │       ├── ChatRepository.kt        # AiEngine interface + MockAiEngine
            │       └── FamilyActivityRepository.kt  # dummy data for MVP
            │
            └── ui/
                ├── theme/                   # Color.kt, Type.kt, Theme.kt (Material 3)
                ├── components/              # Reusable composables (WelcomeHeader, TipCard...)
                ├── common/                  # GenericViewModelFactory (lightweight manual DI)
                ├── navigation/              # Screen.kt (routes) + SaathiNavGraph.kt (root graph)
                ├── main/                    # MainScaffold.kt — bottom nav + nested NavHost
                │
                ├── auth/                    # LoginScreen, SignupScreen, AuthViewModel
                ├── roleselection/           # RoleSelectionScreen, RoleSelectionViewModel
                ├── home/
                │   ├── HomeScreen.kt        # Routes to the right dashboard by role
                │   ├── HomeViewModel.kt
                │   └── dashboards/
                │       ├── MotherDashboard.kt
                │       ├── HusbandDashboard.kt
                │       ├── MotherInLawDashboard.kt
                │       └── FamilyMemberDashboard.kt
                ├── mood/                    # MoodTrackerScreen, MoodViewModel
                ├── chat/                    # AiChatScreen, ChatViewModel ("Saathi AI")
                ├── familyguide/             # FamilyGuideScreen (dummy activity cards)
                └── profile/                 # ProfileScreen, ProfileViewModel
```

**Why this structure?**
- `data/` never imports anything from `ui/` — one-way dependency, easy to unit test.
- Every screen has its own ViewModel that owns exactly the repositories it needs.
- No DI framework (Hilt/Koin) is used for the MVP to keep the project simple to open
  and run; `GenericViewModelFactory` is a placeholder for when you want one later.
  Because repositories are constructor-injected with defaults
  (`class MoodViewModel(private val moodRepository: MoodRepository = MoodRepository())`),
  swapping in Hilt later is a small, mechanical change.

---

## 2. Firebase Setup Steps

1. Go to the [Firebase Console](https://console.firebase.google.com/) → **Add project**
   (e.g. "Saathi").
2. Add an Android app with package name **`com.saathi.app`**.
3. Download the generated **`google-services.json`** and place it at:
   `Saathi/app/google-services.json` (it is intentionally *not* included here —
   never commit real Firebase keys to a public repo).
4. In the Firebase Console:
   - **Authentication** → Sign-in method → enable **Email/Password**.
   - **Firestore Database** → Create database → start in **test mode** for
     development (lock it down with rules below before shipping).
5. Open the project in Android Studio (Hedgehog+ recommended), let Gradle sync,
   and run on an emulator or device.

### Suggested Firestore Security Rules (tighten before production)
```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;

      match /moodEntries/{entryId} {
        allow read, write: if request.auth != null && request.auth.uid == userId;
      }
    }
  }
}
```

---

## 3. Firestore Data Structure

```
users (collection)
└── {uid} (document)
    ├── uid: string
    ├── name: string
    ├── email: string
    ├── role: string        // "MOTHER" | "HUSBAND" | "MOTHER_IN_LAW" | "FAMILY_MEMBER"
    ├── createdAt: number
    └── moodEntries (subcollection)
        └── {entryId} (document)
            ├── id: string
            ├── userId: string
            ├── mood: string   // "HAPPY" | "CALM" | "TIRED" | "STRESSED" | "SAD" | "OVERWHELMED"
            ├── note: string
            └── timestamp: number
```

Chat messages are kept **in-memory only** for the MVP (per spec: "local mock
responses"). To persist them later, add a `chatMessages` subcollection under
`users/{uid}` following the same pattern as `moodEntries`.

---

## 4. Key Architectural Decisions

- **MVVM**: Compose screens observe `StateFlow` exposed by ViewModels.
  ViewModels never touch Firebase directly — only repositories do.
- **Navigation**: A root `NavHost` (`SaathiNavGraph`) handles Login → Signup →
  RoleSelection → Home. Once inside `Home`, `MainScaffold` owns a *nested*
  `NavHost` for the four bottom-nav tabs (Home, Mood, AI Chat, Profile), plus
  the Family Guide screen reached via "quick access" buttons on each dashboard.
- **Role-based dashboards**: `HomeScreen` reads the signed-in user's `role`
  field and renders the matching dashboard composable. Adding a 5th role later
  only requires: 1 new enum value + 1 new dashboard composable + 1 new `when`
  branch.
- **AI Chat is pluggable**: `ChatRepository` depends on an `AiEngine`
  interface. `MockAiEngine` (keyword-matched canned replies) is the default.
  To integrate OpenAI or Gemini later, implement `AiEngine` with a real HTTP
  call and swap the default constructor argument — no UI or ViewModel code
  needs to change.

---

## 5. Design Notes

- Light theme only, Material 3, with a soft palette (blush pink, warm beige,
  lavender, sage green, deep plum) intended to feel calm and non-clinical.
- All screens are built from small reusable composables (`WelcomeHeader`,
  `TipCard`, `SectionTitle`) in `ui/components`, so visual style stays
  consistent across every dashboard.

---

## 6. What's Deliberately Out of Scope for the MVP

- Push notifications / reminders
- Real AI backend integration (structured for it, not wired up)
- Offline support / caching
- Automated tests
- Password reset flow
- Editing role after initial selection (would just be another `UserRepository.updateUserRole` call from Profile)

These are natural "next steps" once the MVP is validated.
