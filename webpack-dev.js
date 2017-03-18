const Common = require('./webpack-common.js');
const Merge = require('webpack-merge');

const ctx = {
    sjs_mode: 'fastopt',
    mode: 'dev',
    assetDir: '/assets/',
    assetFile: '[name].[ext]',
    output_filename: 'dev.js',
};

module.exports = Merge(Common.config(ctx), {

    module: {
        rules: [{
            test: /\.css$/,
            loader: 'style-loader?name=' + ctx.assetDir + ctx.assetFile + '!css-loader',
        }]
    },

});
