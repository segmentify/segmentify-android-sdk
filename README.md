# Segmentify Android SDK
Segmentify  SDK for sending events and rendering recommendations for android based devices

> **Supports Android 4.4 and higher devices.**

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
implementation 'com.segmentify.sdk:android:1.0.0'
```
or If you are using another tool, you can add it as follows :

Maven:
```java
<!-- https://mvnrepository.com/artifact/com.segmentify.sdk/android -->
<dependency>
    <groupId>com.segmentify.sdk</groupId>
    <artifactId>android</artifactId>
    <version>1.0.0</version>
</dependency>
```
SBT :
```java
// https://mvnrepository.com/artifact/com.segmentify.sdk/android
libraryDependencies += "com.segmentify.sdk" % "android" % "1.0.0"
```

Ivy :
```java
<!-- https://mvnrepository.com/artifact/com.segmentify.sdk/android -->
<dependency org="com.segmentify.sdk" name="android" rev="1.0.0"/>
```

Grape : 
```java
<!-- https://mvnrepository.com/artifact/com.segmentify.sdk/android -->
<dependency org="com.segmentify.sdk" name="android" rev="1.0.0"/>
```

Leiningen : 
```java
;; https://mvnrepository.com/artifact/com.segmentify.sdk/android
[com.segmentify.sdk/android "1.0.0"]
```

Buildr : 
```java
# https://mvnrepository.com/artifact/com.segmentify.sdk/android
'com.segmentify.sdk:android:jar:1.0.0'

```


## Usage

To learn more about how to integrate Segmentify Android SDK to your application, please check [Integration Guide](https://www.segmentify.com/dev/integration_android/).

For other integrations you can check [Master Integration](https://www.segmentify.com/dev/) guide too.



## License

Segmentify Android SDK is available under the BSD-2 license.
Please check LICENSE file to learn more about details.
