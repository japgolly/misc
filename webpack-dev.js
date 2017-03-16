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

    entry: ['react', 'react-dom', './target/scala-2.12/demo-fastopt.js'],

    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'dev.js'
    }
}
