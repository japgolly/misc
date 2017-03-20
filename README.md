# Setup

1. [Configure Node.JS](https://gist.github.com/japgolly/775314a0cb24e33653b059b8f8540250)
2. `yarn install`

# Building
1. `sbt fastOptJS::webpack fullOptJS::webpack`

# Features

This builds from SBT but I haven't tried serving it.
Once it's served, links will probably need to change.

This is incomplete and I've stopped experimenting with it.
The [experiment with webpack directly](https://github.com/japgolly/misc/tree/webpack) is much more complete.

- [x] Use scalajs-react without `@JSImport`s
- [x] Use some JS lib with `@JSImport`
- [x] Use assets from some JS lib (CSS + images)
- [x] Use local assets
- [x] Separate dev/prod processes
- [x] Use CDN - pending https://github.com/jantimon/html-webpack-plugin/issues/613
- [x] Output multiple JS files
- [x] `require` bundle assets from SJS
- [x] Hash filenames
  - [x] Images & fonts
  - [x] CSS links
- [x] HTML integration
  - [x] links
  - [x] minification
- [x] gzip
- [ ] scalajs-bundler
  - [x] main (dev/prod)
  - [ ] test
