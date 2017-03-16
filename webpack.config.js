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

    // plugins: [
    //   new webpack.ProvidePlugin({
    //     React: 'react',
    //     ReactDOM: 'react-dom'
    //   })
    // ],

    entry: './launcher.js',

    output: {
        filename: 'dist/dev.js'
    }
}
