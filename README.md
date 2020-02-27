# Segmentify Android SDK
Segmentify  SDK for sending events and rendering recommendations for android based devices

> **Supports Android 4.4(API Level: 19) and higher devices.**
> **Current version : 2.1.1**

## Installation

You can install Segmentify Android SDK to your application by using  [Maven](https://mvnrepository.com/artifact/com.segmentify.sdk/android).

To use Maven, add the project gradle file:


```java
buildscript {
    repositories {
        mavenCentral()
    }
}
```

Please add following line to your gradle file:

Gradle : 
```java
implementation 'com.segmentify.sdk:android:*.*.*'
```
or If you are using another tool, you can add it as follows :

Maven:
```xml
<!-- https://mvnrepository.com/artifact/com.segmentify.sdk/android -->
<dependency>
    <groupId>com.segmentify.sdk</groupId>
    <artifactId>android</artifactId>
    <version>***</version>
</dependency>
```
SBT :
```scala
// https://mvnrepository.com/artifact/com.segmentify.sdk/android
libraryDependencies += "com.segmentify.sdk" % "android" % "*.*.*"
```

Ivy :
```xml
<!-- https://mvnrepository.com/artifact/com.segmentify.sdk/android -->
<dependency org="com.segmentify.sdk" name="android" rev="*.*.*"/>
```

Grape : 
```xml
<!-- https://mvnrepository.com/artifact/com.segmentify.sdk/android -->
<dependency org="com.segmentify.sdk" name="android" rev="*.*.*"/>
```

Leiningen : 
```Clojure 
;; https://mvnrepository.com/artifact/com.segmentify.sdk/android
[com.segmentify.sdk/android "*.*.*"]
```

Buildr : 
```
# https://mvnrepository.com/artifact/com.segmentify.sdk/android
'com.segmentify.sdk:android:jar:*.*.*'

```


## Usage

To learn more about how to integrate Segmentify Android SDK to your application, please check [Integration Guide](https://www.segmentify.com/dev/integration_android/).

For other integrations you can check [Master Integration](https://www.segmentify.com/dev/) guide too.



## License

Segmentify Android SDK is available under the BSD-2 license.
Please check LICENSE file to learn more about details.
