const Webpack = require('webpack');
const Path = require('path');

const ctx = {
    assetDir: '/test/assets/',
    assetFile: '[name].[ext]',
};

module.exports = {

    bail: true,

    entry: {
        main: [
            'react', 'react-dom',
            'react-addons-test-utils',
            './test-loader.js',
        ],
    },

    output: {
        path: Path.resolve(__dirname, 'target/scala-2.12'),
        filename: 'demo-test-fastopt-bundle.js',
    },

    resolve: {
        alias: {
            'experiment-webpack': Path.resolve(__dirname, 'local_module'),
        },
    },

    // plugins: [
    //   new Webpack.DefinePlugin({
    //       'process.env.NODE_ENV': JSON.stringify('production')
    //   }),
    // ],

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
            }, {
                test: require.resolve('react-addons-test-utils'),
                use: [{
                    loader: 'expose-loader',
                    options: 'React.addons.TestUtils'
                }]
            },
            // https://medium.com/@victorleungtw/how-to-use-webpack-with-react-and-bootstrap-b94d33765970#.xrvg55omh
            // url-loader?limit=n means encode the file inline with base64 if the filesize < n, else make it a
            // separate url/link/request.
            {
                test: /\.(woff|woff2)(\?v=\d+\.\d+\.\d+)?$/,
                loader: 'url-loader?limit=10000&mimetype=application/font-woff&name=' + ctx.assetDir + ctx.assetFile,
            }, {
                test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/,
                loader: 'url-loader?limit=10000&mimetype=application/octet-stream&name=' + ctx.assetDir + ctx.assetFile,
            }, {
                test: /\.(?:jpe?g|eot(\?v=\d+\.\d+\.\d+)?)$/,
                loader: 'file-loader?name=' + ctx.assetDir + ctx.assetFile,
            }, {
                test: /\.svg(\?v=\d+\.\d+\.\d+)?$/,
                loader: 'url-loader?limit=10000&mimetype=image/svg+xml&name=' + ctx.assetDir + ctx.assetFile,
            },
        ],
    },
};
