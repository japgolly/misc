const Common = require('./webpack-common.js');
const Merge = require('webpack-merge');

const ctx = {
    sjs_mode: 'fastopt',
    output_filename: 'dev',
};

module.exports = Merge(Common.config(ctx), {

    module: {
        rules: [{
            test: /\.css$/,
            loader: "style-loader?name=/assets/[hash].[ext]!css-loader",
        }]
    },

});
