const Common = require('./webpack-common.js');
const Merge = require('webpack-merge');

module.exports = Merge(Common.config, {

    entry: [
        Common.sjs('fastopt'),
    ],

    output: {
        filename: 'dev.js',
    },

    module: {
        rules: [{
            test: /\.css$/,
            loader: "style-loader?name=/assets/[hash].[ext]!css-loader",
        }]
    },

});
