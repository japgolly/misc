const webpack = require('webpack');

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

    entry: './launcher.js',

    output: {
        filename: 'dist/dev.js'
    }
}
