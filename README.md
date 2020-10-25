# Fuller stack
![](https://github.com/AKJAW/fuller-stack-kotlin-multiplatform/workflows/Build%20all%20platforms/badge.svg)
![](https://github.com/AKJAW/fuller-stack-kotlin-multiplatform/workflows/Static%20code%20analysis/badge.svg)
![](https://github.com/AKJAW/fuller-stack-kotlin-multiplatform/workflows/Tests/badge.svg)

### Ktor server
The gradle command for running the ktor server:
```
$ ./gradlew :ktor:ktorRun
```
The server will run on port 9000.

As of now there are no endpoints for the server

### Android app
The gradle command for installing the android app:
```
$ ./gradlew :android:installDebug
```

### React app
The gradle command for running the react app:
```
$ ./gradlew :react:reactRun
```
The app will run on port 8080