1. Install Android Studio for your platform.
2. Install the required libraries through the SDK manager: Nougat 7.1.1 SDK, Android SDK Build-Tools, Android SDK Platform-Tools, Android SDK Tools, Google Play Services, All items under Support Repository.
3. If you miss installing a library the build will fail and it will prompt you in the Gradle messages to install that library.
4. Enable developer options on phone. In developer options, enable USB Debugging. Each phone is a little different so Google how to do it on your phone.
5. On Github go to the Nutriscope page and click the green button, download the zip file containing the code. Unzip the code and put the attached google-services.json file inside Nutriscope-master/app
6. File -> Open, Navigate to the project directory and open.
7. Go to the Run menu and click Run app.   Select your phone and make sure it accepts the RSA key to communicate with your computer.
7. The app should build and run.


Likely errors would be from not downloading all the SDK libraries. You may be prompted to install more.
