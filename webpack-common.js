const Path = require('path');
const Webpack = require('webpack');
const HtmlPlugin = require('html-webpack-plugin');

const sjs = (c) => './target/scala-2.12/demo-' + c + '.js';

const config = (ctx) => ({

    bail: true,

    module: {
        rules: [
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

    resolve: {
        alias: {
            'experiment-webpack': Path.resolve(__dirname, 'local_module'),
        },
    },

    entry: {
        // react: ['react', 'react-dom'],
        main: [
            'bootstrap/dist/css/bootstrap.css',
            sjs(ctx.sjs_mode),
        ],
    },

    output: {
        path: Path.resolve(__dirname, 'dist', ctx.mode),
        publicPath: '/' + ctx.mode + '/',
        filename: ctx.output_filename,
    },

    plugins: [
        // new Webpack.optimize.CommonsChunkPlugin({
        //     name: 'react',
        // }),
        new Webpack.optimize.CommonsChunkPlugin({
            name: 'vendor',
            minChunks: (m) => m.context && m.context.indexOf('node_modules') !== -1,
        }),
        // new Webpack.optimize.CommonsChunkPlugin({
        //     name: 'manifest',
        //     minChunks: Infinity,
        // }),
        new HtmlPlugin(Object.assign({}, ctx.htmlOptions || {}, {
            title: 'demo [' + ctx.mode + ']',
            template: 'local_module/index.html',
            filename: 'index.html',
        })),
    ],
});

module.exports = {
    config,
};
