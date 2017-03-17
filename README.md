# Setup

1. [Configure Node.JS](https://gist.github.com/japgolly/775314a0cb24e33653b059b8f8540250)
2. `yarn install`

# Building
1. `sbt fastOptJS fullOptJS`
2. `./build`

# Running
1. `./serve.sh`
2. Open in browser:
  * http://localhost:4000/dev.html
  * http://localhost:4000/prod.html


# Features

- [x] Use scalajs-react without `@JSImport`s
- [x] Use some JS lib with `@JSImport`
- [x] Use assets from some JS lib (CSS + images)
- [x] Use local assets
- [x] Separate dev/prod processes
- [ ] Use CDN
- [ ] Output multiple JS files
- [x] `require` bundle assets from SJS
- [ ] Hash filenames
  - [x] Images & fonts
  - [ ] CSS links
- [ ] gzip
- [ ] webpack dev server
- [ ] SJS test deps
- [ ] scalajs-bundler
  - [ ] main (dev/prod)
  - [ ] test


# History
* `yarn add --dev webpack`
* `yarn add --dev expose-loader`
* `yarn add react react-dom`
* `yarn add google-map-react`
* `yarn add --dev css-loader style-loader file-loader url-loader`
* `yarn add bootstrap@3`
* `yarn add --dev extract-text-webpack-plugin`
* `yarn add --dev webpack-merge`
