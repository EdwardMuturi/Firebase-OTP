# FirebasePhoneAuth [![FirebasePhoneAuth>](https://circleci.com/gh/EdwardMuturi/FirebasePhoneAuth.svg?style=svg)](https://app.circleci.com/jobs/github/EdwardMuturi/FirebasePhoneAuth/3)

A phone authentication library using firebase auth SDK

# Set Up

We need to pass the activity name that will be started after registration. Follow the steps below.D

- Import `firebase-auth` into your project
- Declare the immediate activity to start after registration.  He we assume the acitivity's name is `MainActivity`. Go to your manifest file in  target activity tag and add the following replacing with your actual path and name.

    <!--replace activity name with yours i.e preceded by your package name-->
         
         <intent-filter>
             <action android:name="com.mementoguy.MainActivity"/> 
    
             <category android:name="android.intent.category.DEFAULT" />
         </intent-filter>

- Pass the activity name as an intent extra using the **constant** `EXTRA_ACTIVITY_NAME_TO_START`

## License

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
