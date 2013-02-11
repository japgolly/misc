### Notice

* I only tagged on the integration test at the end. I've confirmed that it correctly build, installs and runs tests
  on the target device. After confirming that it works I haven't spent the time to make it all amazing. Some lacking
  features:
  * It currently only tests the paid app, not the free app.
  * A straight Java/Scala module might be beneficial to share code between the integration tests.
  * It would be awesome to write integration tests in the library module with an abstract back-end. Thus, the same
    tests could run in `library` via Robolectric, and then for real for both free and paid apps.
  * You'd probably want to stop integration tests running more than once during a release.

### Structure
* Root - Declares dependency library versions, plugin versions, shared config, etc.
* `library` - Abstract Android project that houses 99% of your app code and resources.
* `free` - Concrete Android app that pulls in the `library` module and overrides some stuff like enabling ads.
* `paid` - Concrete Android app that pulls in the `library` module and can also override stuff if needed.
* `test-paid` - Integration tests for the paid add.

### Commands

* To build everything, deploy to phone and run integration tests:

    ```
    mvn clean install
    ```

* To create release:

    ```
    mvn release:prepare release:perform
    ```

