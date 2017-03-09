### Features
* Lombok: Modified to play nice with Android, added ability to add @Inject to
  generated constructors.
* AndroidAnnotations.
* CoFoJa: Contracts for Java. Contract checks disabled by default, can be
  enabled via the cofoja profile.
* Android lint integration.
* Findbugs.
* Eclipse: Ready to go with Eclipse, just import as Eclipse project.
  Preprocessors (lombok + AA) already configured and working.
* Release profile that signs build and zipaligns.


### Structure
* Root - Declares dependency library versions, plugin versions, shared config, etc.
* `app` - The Android app. Has unit tests using Robolectric.
* `test` - Integration tests. Deploys to phone and tests app on real or emulated device.

### Commands

* To build everything, deploy to phone and run integration tests:

    ```
    mvn clean install
    ```

* To run Android Lint with your build, enable the `lint` profile:

    ```
    mvn clean install -P lint
    ```

* To enable contract verification in code, enable the `cofoja` profile:

    ```
    mvn clean test -P cofoja
    ```

* To create release:

    ```
    mvn release:prepare release:perform
    ```

