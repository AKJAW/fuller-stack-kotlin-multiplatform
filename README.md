# Fuller stack
![](https://github.com/AKJAW/fuller-stack-kotlin-multiplatform/workflows/Build%20all%20platforms/badge.svg)
![](https://github.com/AKJAW/fuller-stack-kotlin-multiplatform/workflows/Static%20code%20analysis/badge.svg)
![](https://github.com/AKJAW/fuller-stack-kotlin-multiplatform/workflows/Tests/badge.svg)
<a href="https://androidweekly.net/issues/issue-480">
<img src="https://androidweekly.net/issues/issue-480/badge">
</a>

A simple note taking app for Android and the Web. Both client platforms share code using Kotlin Multiplatform and use
a Ktor server.

The project is part of my [article series](https://akjaw.com/kotlin-multiplatform-for-android-and-the-web-part-1/) about
Kotlin Multiplatform for Android and the Web.

## The client platform architecture
![Apps architecture](assets/apps-architecture.png)

![Apps architecture](assets/data-layer-implementations.png)

## How to change the server ip to the local server
The server endpoint can be changed in the [commonMain/network.ApiUrl](shared/src/commonMain/kotlin/network/ApiUrl.kt) 
file.

## Ktor server

![Apps architecture](assets/socket-update.png)

The gradle command for running the ktor server:
```
$ ./gradlew :ktor:ktorRun
```
The server will run on port 9000.

Test account email: test@test.com
Test account password: Test123123

## Android app

<img src="assets/android-home.png" alt="Apps architecture" width="400px"/>


The gradle command for installing the android app:
```
$ ./gradlew :android:app:installDebug
```

## React app

![Apps architecture](assets/react-home.png)

The gradle command for running the react app:
```
$ ./gradlew :react:spa-app:reactRun
```
The app will run on port 8080
