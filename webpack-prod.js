const Common = require('./webpack-common.js');
const Merge = require('webpack-merge');
const Webpack = require('webpack');

const ctx = {
    mode: 'prod',
    output_filename: 'webpack-[name].min.js',
};

module.exports = Merge(Common.config(ctx), {

    plugins: [

        // See webpack's bin/convert-argv.js for what -p expands to
        new Webpack.LoaderOptionsPlugin({
            minimize: true,
        }),
        new Webpack.DefinePlugin({
            'process.env.NODE_ENV': JSON.stringify('production')
        }),
        new Webpack.optimize.UglifyJsPlugin({
          compress: {
            screw_ie8: true,
            warnings: false
          },
          output: {
            comments: false
          },
          sourceMap: false
        }),

        // Don't emit anything when errors exist
        new Webpack.NoEmitOnErrorsPlugin(),
    ]

});
