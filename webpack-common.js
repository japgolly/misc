const Path = require('path');
const Webpack = require('webpack');

const config = (ctx) => ({

    bail: true,

    entry: {
        react: [
          'expose-loader?React!react',
          'expose-loader?ReactDOM!react-dom',
          'expose-loader?ReactDOMServer!react-dom/server',
        ],
    },

    output: {
        path: '/tmp',
        filename: ctx.output_filename,
    },
});

module.exports = {
    config,
};
