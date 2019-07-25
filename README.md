## Description
SqueezeLoader is a tiny custom Loader (ProgressBar) 

![alt Library Logo](https://raw.githubusercontent.com/mecoFarid/SqueezeLoader/master/extra_media/library_logo.jpg)

## Demo
![alt SqueezeLoader demo gif](https://raw.githubusercontent.com/mecoFarid/SqueezeLoader/master/extra_media/squeezeloader.gif)

## Usage
### 1. Integration
Min API level is: `API 14` [![](https://jitpack.io/v/mecoFarid/squeezeloader.svg)](https://jitpack.io/#mecoFarid/squeezeloader)

**Step 1.** Add it in your root build.gradle at the end of repositories:

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
**Step 2.** Add the dependency
```
dependencies {
    ...  
    implementation 'com.github.mecofarid:squeezeloader:1.0.0-beta'
}
```
### 2.Troubleshooting
If you get and error similar to:
```
Error: Default interface methods are only supported starting with Android N (--min-api 24): android.view.MenuItem androidx.core.internal.view.SupportMenuItem.setContentDescription(java.lang.CharSequence)
```
then add this snippet to `android` section in `build.gradle (app Module)` file:

```
compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
```
