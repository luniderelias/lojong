# MVPV-LiveData-Base-Kotlin
This project contains an example of MVP+LiveData architecture for kotlin-based android apps.
I'm trying to set the best architecture in order to unit test complex UI.
MVP+LiveData is a nice approach for enabling Model View Presenter architecture to test complex UI.
LiveData is a feature from Androidx Lifecycle that is commonly used on MVVM-based architectures. It enables the access of data directly from XML layout files. Also, it has a nicely implemented way of observing data changes on both android and test environments.

## Observations
This is the MVP+LiveData implementation, I've also developed [MVVM](https://github.com/luniderelias/MVVM-Base-Kotlin) and [MVPView](https://github.com/luniderelias/MVPView-Base-Kotlin) examples.
This project is a study to understand the better approach in order to unit test complex UI of Android Apps without using any instrumented test.
The key points of MVP+LiveData is that its not a new architecture, we are still using MVP, but we are adapting it to use LiveData structure. So, we are able to use reactive programming and also to add complex UI unit tests.
I've seen some posts pointing out that this approach would separate our Presenter from the View, so we can have multiple views consuming the same Presenter. This is almost like turning our Presenter into a ViewModel, but also we can keep the relation one-to-one with our Views.
I prefer to keep the one-to-one relation so we can concentrate our entire business and UI logic inside the Presenter. It means we have to create a single test file in order to test both complex UI and business logic.

If you have been developing your project using MVP architecture for a while. It would be better to try this approach instead of changing entire application to MVVM, MVPView, VIPER or any other complex architecture. 
This means you wouldn't need to change a lot of your code. We can begin developing new features using this new approach and, whenever we visit old pieces of our code, we can modify it in order to enable complex UI testing.

## Libraries
Mockito,
Koin,
Lifecycle,
MockitoKotlin2

## Architecture
MVP Repositories and LiveData


