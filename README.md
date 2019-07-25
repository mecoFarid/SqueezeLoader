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
    implementation 'com.github.mecoFarid:squeezeloader:1.0.1-alpha'
}
```
### 2.Troubleshooting
If you get an error similar to:
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
### 3. Code example
After integrating the library to your app you can use the `SqueezeLoader` in your xml as below:

`SqueezeLoader` is the Layout that fast-moving/squeezing `SqueezeBar` animates through. Look at the gif above fro reference, the fast moving bar is called `SqueezeBar`
```
<com.mecofarid.squeezeloader.SqueezeLoader
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            app:sl_squeezebarWidth="50dp"
            app:sl_animationDuration="1000"
            app:sl_colorSqueezebar="@color/colorPrimary"/>
```
### 4. Attribute explanation and values
**Minumum/Maximum values for:** 

| Atribute name              | Default          | Minimum   |             Maximum | 
|     :---                   |      :---        | :---      |     :---            |
| app:sl_animationDuration   | 1000ms           | 1000ms    | No restriction      |
| android:layout_width       | parent's width   | 200dp     | parent's width      |
| app:sl_squeezebarWidth     | 50dp             | 2dp       | 100dp               |



## Notes:
If you place SqueezeLoader in a parent view with `android:layout_width` less than `200dp` it will inherite parent's `width` in this case `SqueezeLoader` can be less than `200dp`
