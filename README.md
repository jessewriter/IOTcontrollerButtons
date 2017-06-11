# IOTcontrollerButtons
low resolution prototype of an Android endpoint controller using buttons, sliders and text fields.

This is an Android API 25 IOT controller app.

It uses a REST server hosted on Heroku using noSQL JSON object with key=value fields to control a robot/door/machine/toaster whatever!

My endpoint is an Intel Edison board with some basic motors and sensors that listen to the REST server for changes and act appropriately to my bidding.

The database is MongoDB set up with MatLab.

All Android programming is done in Android Studio with Java programming language.

I would like the app to look prettier but function comes before beauty at least in my work-flow.  Beauty is essential for adoption and that is how I feel about most apps.

In image I just slid the slider to show the Toast message of the slider value.  To get that to register in the cloud you must click CHANGE.  To pull down the latest data from the cloud click UPDATE, note that the free version of Heroku puts my app to sleep so you may have to click update a few times before the data pulls down, be patient, after that it runs smoothly.


![screenshot_20170605-221335](https://goo.gl/photos/v82FWGyghZYCLoir9)
