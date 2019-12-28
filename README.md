# FirebasePhoneAuth
A phone authentication library using firebase auth sdk

## How to Set Up
* Import `firebase-auth` into your project
* Add this to your activity that is supposed to be started after successful registration
```xml
     <!--replace activity name with yours i.e preceded by your package name-->
     
     <intent-filter>
         <action android:name="com.mementoguy.MainActivity"/> 

         <category android:name="android.intent.category.DEFAULT" />
     </intent-filter>
 ```
 * Pass the above activity name as intent extra when starting `RegisterActivity`. This helps the library know which activity to start after user phone is verified


# License
```text
MIT License

Copyright 2019 Edward Muturi

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
associated documentation files (the "Software"), to deal in the Software without restriction, 
including without limitation the rights to use, copy, modify, merge, publish, distribute, 
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is 
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```