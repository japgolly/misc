// This generates stuff needed locally (as opposed to assets that will be served.)

const
  Path = require('path'),
  Webpack = require('webpack'),
  TerserPlugin = require('terser-webpack-plugin'),
  NodeModules = Path.resolve(__dirname, '../node_modules');

const config = {

  entry: {

    'webapp-base-test': [
      './es6-shim',
    ],
  },

  output: {
    path: Path.resolve(__dirname, '../dist/local'),
    filename: '[name].js',
    chunkFilename: 'chunk-[name]-[id].[ext]',
  },

  context: Path.resolve(__dirname, '..'),

  resolve: { modules: [NodeModules] },
  resolveLoader: { modules: [NodeModules] },

  mode: 'production',

  // performance: {
  //   hints: false
  // },

  // optimization: {
  //   minimizer: [new TerserPlugin({
  //     cache: true,
  //     parallel: true,
  //     terserOptions: {
  //       output: {
  //         comments: false,
  //       }
  //     },
  //   })]
  // },

  // plugins: [
  //   new Webpack.LoaderOptionsPlugin({
  //     minimize: true,
  //   }),
  // ],

  bail: true,
};

module.exports = config;
