const webpack = require('webpack');
const path = require('path');
const ExtractTextPlugin = require("extract-text-webpack-plugin");

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
                //loader: "style-loader?name=/assets/[hash].[ext]!css-loader"
                use: ExtractTextPlugin.extract({
                    fallback: 'style-loader',
                    use: 'css-loader'
                }),
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
                test: /\.(?:jpe?g|eot(\?v=\d+\.\d+\.\d+)?)$/,
                loader: 'file-loader?name=/assets/[hash].[ext]'
            },
            {
                test: /\.svg(\?v=\d+\.\d+\.\d+)?$/,
                loader: 'url-loader?limit=10000&mimetype=image/svg+xml&name=/assets/[hash].[ext]'
            },
        ]
    },

    resolve: {
        alias: {
            'experiment-webpack': path.resolve(__dirname, 'local-modules'),
        },
    },

    entry: [
        'react', 'react-dom',
        'bootstrap/dist/css/bootstrap.css',
        './target/scala-2.12/demo-opt.js'
    ],

    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'prod.js',
    },

    plugins: [
        // See webpack's bin/convert-argv.js for what -p expands to
        new webpack.LoaderOptionsPlugin({
            minimize: true,
        }),
        new webpack.DefinePlugin({
            'process.env.NODE_ENV': JSON.stringify('production')
        }),
        new webpack.optimize.UglifyJsPlugin({}),
        // Externalise CSS
        new ExtractTextPlugin({
            filename: 'assets/main.css',
            allChunks: true,
            // filename: 'assets/[contenthash].css',
            // allChunks: false,
        }),
    ]
}
