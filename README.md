# AndroidRTC

Step 1. ***Add the JitPack repository to your build file***

``` 
allprojects {
    repositories {
        ...
        jcenter()
        maven { url 'https://jitpack.io' }
        maven { url "file://${projectDir}/repo" }
    }
} 

```
Step 2. ***Add the dependency***

```

dependencies {

      implementation 'com.github.avirajrajput:AndroidRTC:Tag'
      
}

```

Step 3. ***Recommended***

```
dependencies {

      classpath "com.android.tools.build:gradle:4.0.1"
        
}
```
