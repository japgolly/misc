const webpack = require('webpack');
const path = require('path');

module.exports = {

    module: {
        rules: [{
            test: require.resolve('react'),
            use: [{
                loader: 'expose-loader',
                options: 'React'
            }]
        }, {
            test: require.resolve('react-dom'),
            use: [{
                loader: 'expose-loader',
                options: 'ReactDOM'
            }]
        }]
    },

    entry: ['react', 'react-dom', './launcher-prod.js'],

    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'prod.js'
    },

    // See webpack's bin/convert-argv.js for what -p expands to
    plugins: [
        new webpack.LoaderOptionsPlugin({
            minimize: true,
        }),
        new webpack.DefinePlugin({
            'process.env.NODE_ENV': JSON.stringify('production')
        }),
        new webpack.optimize.UglifyJsPlugin({})
    ]
}
