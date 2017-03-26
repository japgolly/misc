import commonjs from 'rollup-plugin-commonjs';
import nodeResolve from 'rollup-plugin-node-resolve';
import replace from 'rollup-plugin-replace';
import uglify from 'rollup-plugin-uglify'

export default {
  entry: './react3.js',
  format: 'iife',

  plugins: [

    nodeResolve({
      jsnext: true, main: true,
    }),

    replace({
      'process.env.NODE_ENV': JSON.stringify('production')
    }),

    commonjs({
      // namedExports: {
      //   'node_modules/react/react.js': ['Component', 'Children', 'createElement', 'PropTypes'],
      //   'node_modules/react-dom/index.js': ['render']
      // }
    }),

    // commonjs({
    //   include: 'node_modules/**',
    // }),

    uglify({
      compress: {
        screw_ie8: true,
        warnings: false
      },
      output: {
        comments: false
      },
      sourceMap: false
    })
  ],

  dest: '/tmp/rollup-react.min.js',

  moduleName: 'omfg'
};
