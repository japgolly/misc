const Common = require('./webpack-common.js');
const Merge = require('webpack-merge');

const ctx = {
    mode: 'dev',
    output_filename: 'webpack-[name].js',
};

module.exports = Merge(Common.config(ctx), {

});
