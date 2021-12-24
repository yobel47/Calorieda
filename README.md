# Kalorieda

[![GitHub release][release-shield]][release-url]

<a><img src="https://i.imgur.com/M5vLCyZ.png" /></a>

** Reminder eat and drink, Calculate calories in your meals using Kalorieda.** This Android-based app is powered by [Nutritionix API](https://developer.nutritionix.com/) and entirely well-written in Kotlin. Kalorieda is built using Clean Architecture which makes it robust, flexible, and maintainable. This app was developed as a capstone project in [Dicoding](https://www.dicoding.com)'s.

## Download
Check out the [release page](https://github.com/yobel47/Calorieda/releases) and download the latest apk.

## MAD Scorecard
<a><img src="https://i.ibb.co/mh8nQd1/summary.png" /></a>

## Architecture and Tech-stack
- 100% Kotlin
- Clean Architecture (three layers separation: presentation, domain, and data) with [MVVM pattern](https://developer.android.com/jetpack/guide#recommended-app-arch)
- Android Architecture Components, specifically [Room](https://developer.android.com/topic/libraries/architecture/room), [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), and [Material Components](https://material.io/develop/android)
- Fetch data from the network and a Room database using [Coroutines Flow](https://developer.android.com/kotlin/coroutines)
- [Retrofit](https://github.com/square/retrofit), REST client framework
- [Gson](https://github.com/google/gson), parsing the JSON format
- [OkHttp](https://github.com/square/okhttp), certificate pinning
- [AAChartCore](https://github.com/AAChartModel/AAChartCore-Kotlin), data visualization chart framework
- [Glide](https://github.com/bumptech/glide), image loading and caching
- [Lottie](https://github.com/airbnb/lottie-android), parsing animation natively
- [RoundedImageView](https://github.com/vinc3m1/RoundedImageView), image view with rounded corners

## Configuration
Firstly, clone this repository and import it into Android Studio (`git clone https://github.com/yobel47/Calorieda.git`).

### Setup API Key
1. Get your [Nutritionix API](https://developer.nutritionix.com/) app id and app key.
2. Open root directory and create a new file named `api.properties`.
3. Put your app id and key in `api.properties` file by adding the following lines:
```
APP_ID=YOUR_APP_ID
APP_KEY=YOUR_APP_KEY
API_REMOTE=YOUR_API_REMOTE
```
*Use relative path to specify your keystore file path. You can learn [here](https://networkencyclopedia.com/relative-path/).*

## 🤝 Support
Any contributions, issues, and feature requests are welcome.

Give a ⭐️ if you like this project.

## Acknowledgments
- [CSS Nutrition Facts Label](https://jsfiddle.net/thL6j/)
- [Empty State – Heart](https://lottiefiles.com/46771-empty-state-heart)
- [Feather Icons](https://www.figma.com/community/plugin/744047966581015514/Feather-Icons)
- [Figma](https://www.figma.com)
- [Freepik](https://www.freepik.com)
- [Material Design Icons (Community)](https://www.figma.com/community/plugin/775671607185029020/Material-Design-Icons-(Community))
- [Nutritionix](https://www.nutritionix.com/)
- [pch.vector](https://www.freepik.com/pch-vector)
- [Spaceship Empty Searching](https://lottiefiles.com/4011-spaceship-empty-searching)

## To-do List
- [ ] Add unit tests
- [ ] Add instrumented tests
