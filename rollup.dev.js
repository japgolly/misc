import commonjs from 'rollup-plugin-commonjs';
import nodeResolve from 'rollup-plugin-node-resolve';
import replace from 'rollup-plugin-replace';

export default {
  entry: './react3.js',
  format: 'iife',

  plugins: [

    nodeResolve({
      jsnext: true, main: true,
    }),

    replace({
      'process.env.NODE_ENV': JSON.stringify('development')
    }),

    commonjs({}),
  ],

  dest: '/tmp/rollup-react.js',

  moduleName: 'omfg'
};
