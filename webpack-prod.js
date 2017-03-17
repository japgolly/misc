const Common = require('./webpack-common.js');
const Merge = require('webpack-merge');
const Webpack = require('webpack');
const ExtractTextPlugin = require("extract-text-webpack-plugin");

module.exports = Merge(Common.config, {

    entry: [
        Common.sjs('opt'),
    ],

    output: {
        filename: 'prod.js',
    },

    module: {
        rules: [{
            test: /\.css$/,
            use: ExtractTextPlugin.extract({
                fallback: 'style-loader',
                use: 'css-loader',
            }),
        }]
    },

    plugins: [

        // See webpack's bin/convert-argv.js for what -p expands to
        new Webpack.LoaderOptionsPlugin({
            minimize: true,
        }),
        new Webpack.DefinePlugin({
            'process.env.NODE_ENV': JSON.stringify('production')
        }),
        new Webpack.optimize.UglifyJsPlugin({}),

        // Externalise CSS
        new ExtractTextPlugin({
            filename: 'assets/main.css',
            allChunks: true,
            // filename: 'assets/[contenthash].css',
            // allChunks: false,
        }),

        // Don't emit anything when errors exist
        new Webpack.NoEmitOnErrorsPlugin(),
    ]

});
