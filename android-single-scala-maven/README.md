### Notice

* Some addition features are missing but would be nice:
  * It would be awesome to write integration tests in the library module with an abstract back-end. Thus, the same
    tests could run in `app` via Robolectric, and then for real with the integration test.
  * You'd probably want to stop integration tests running more than once during a release.

### Structure
* Root - Declares dependency library versions, plugin versions, shared config, etc.
* `app` - The Android app. Has unit tests using Robolectric.
* `test` - Integration tests. Deploys to phone and tests app on real or emulated device.

### Commands

* To build everything, deploy to phone and run integration tests:

    ```
    mvn clean install
    ```

* To create release:

    ```
    mvn release:prepare release:perform
    ```

