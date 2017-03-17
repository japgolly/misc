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
            },
            // https://medium.com/@victorleungtw/how-to-use-webpack-with-react-and-bootstrap-b94d33765970#.xrvg55omh
            // url-loader?limit=n means encode the file inline with base64 if the filesize < n, else make it a
            // separate url/link/request.
            {
                test: /\.css$/,
                loader: "style-loader?name=/assets/[hash].[ext]!css-loader"
            }, {
                test: /\.(woff|woff2)(\?v=\d+\.\d+\.\d+)?$/,
                // loader: 'url-loader?limit=10000&mimetype=application/font-woff&name=/assets/[hash].[ext]'
                loader: 'url-loader?limit=10000&mimetype=application/font-woff&name=/assets/[hash].[ext]'
            },
            {
                test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/,
                loader: 'url-loader?limit=10000&mimetype=application/octet-stream&name=/assets/[hash].[ext]'
            },
            {
                test: /\.eot(\?v=\d+\.\d+\.\d+)?$/,
                loader: 'file-loader?name=/assets/[hash].[ext]'
            },
            {
                test: /\.svg(\?v=\d+\.\d+\.\d+)?$/,
                loader: 'url-loader?limit=10000&mimetype=image/svg+xml&name=/assets/[hash].[ext]'
            },
        ]
    },

    entry: ['react', 'react-dom', './assets.js', './target/scala-2.12/demo-fastopt.js'],

    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'dev.js',
    }
}
