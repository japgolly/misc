/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 0);
/******/ })
/************************************************************************/
/******/ ({

/***/ "./es6-shim.js":
/*!*********************!*\
  !*** ./es6-shim.js ***!
  \*********************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("/* WEBPACK VAR INJECTION */(function(global) {var __WEBPACK_AMD_DEFINE_FACTORY__, __WEBPACK_AMD_DEFINE_RESULT__;/*!\n * https://github.com/paulmillr/es6-shim\n * @license es6-shim Copyright 2013-2016 by Paul Miller (http://paulmillr.com)\n *   and contributors,  MIT License\n * es6-shim: v0.35.4\n * see https://github.com/paulmillr/es6-shim/blob/0.35.3/LICENSE\n * Details and documentation:\n * https://github.com/paulmillr/es6-shim/\n */\n\n// UMD (Universal Module Definition)\n// see https://github.com/umdjs/umd/blob/master/returnExports.js\n(function (root, factory) {\n  /*global define, module, exports */\n  if (true) {\n    // AMD. Register as an anonymous module.\n    !(__WEBPACK_AMD_DEFINE_FACTORY__ = (factory),\n\t\t\t\t__WEBPACK_AMD_DEFINE_RESULT__ = (typeof __WEBPACK_AMD_DEFINE_FACTORY__ === 'function' ?\n\t\t\t\t(__WEBPACK_AMD_DEFINE_FACTORY__.call(exports, __webpack_require__, exports, module)) :\n\t\t\t\t__WEBPACK_AMD_DEFINE_FACTORY__),\n\t\t\t\t__WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));\n  } else {}\n}(this, function () {\n  'use strict';\n\n  var keys = Object.keys;\n\n  var throwsError = function (func) {\n    try {\n      func();\n      return false;\n    } catch (e) {\n      return true;\n    }\n  };\n\n  var arePropertyDescriptorsSupported = function () {\n    // if Object.defineProperty exists but throws, it's IE 8\n    return !throwsError(function () {\n      return Object.defineProperty({}, 'x', { get: function () { } }); // eslint-disable-line getter-return\n    });\n  };\n  var supportsDescriptors = !!Object.defineProperty && arePropertyDescriptorsSupported();\n\n  var _forEach = Function.call.bind(Array.prototype.forEach);\n\n  var defineProperty = function (object, name, value, force) {\n    if (!force && name in object) { return; }\n    if (supportsDescriptors) {\n      Object.defineProperty(object, name, {\n        configurable: true,\n        enumerable: false,\n        writable: true,\n        value: value\n      });\n    } else {\n      object[name] = value;\n    }\n  };\n\n  // Define configurable, writable and non-enumerable props\n  // if they don’t exist.\n  var defineProperties = function (object, map, forceOverride) {\n    _forEach(keys(map), function (name) {\n      var method = map[name];\n      defineProperty(object, name, method, !!forceOverride);\n    });\n  };\n\n  var _toString = Function.call.bind(Object.prototype.toString);\n  var isCallable =  false ? undefined : function IsCallableFast(x) { return typeof x === 'function'; };\n\n  var Value = {\n    preserveToString: function (target, source) {\n      if (source && isCallable(source.toString)) {\n        defineProperty(target, 'toString', source.toString.bind(source), true);\n      }\n    }\n  };\n\n  var getGlobal = function () {\n    /* global self, window, global */\n    // the only reliable means to get the global object is\n    // `Function('return this')()`\n    // However, this causes CSP violations in Chrome apps.\n    if (typeof self !== 'undefined') { return self; }\n    if (typeof window !== 'undefined') { return window; }\n    if (typeof global !== 'undefined') { return global; }\n    throw new Error('unable to locate global object');\n  };\n\n  var globals = getGlobal();\n\n  var ES = {\n    ToUint32: function (x) {\n      return ES.ToNumber(x) >>> 0;\n    },\n\n    ToNumber: function (value) {\n      if (_toString(value) === '[object Symbol]') {\n        throw new TypeError('Cannot convert a Symbol value to a number');\n      }\n      return +value;\n    },\n  };\n\n  var MathShims = {\n    imul: function imul(x, y) {\n      // taken from https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Math/imul\n      var a = ES.ToUint32(x);\n      var b = ES.ToUint32(y);\n      var ah = (a >>> 16) & 0xffff;\n      var al = a & 0xffff;\n      var bh = (b >>> 16) & 0xffff;\n      var bl = b & 0xffff;\n      // the shift by 0 fixes the sign on the high part\n      // the final |0 converts the unsigned value into a signed value\n      return (al * bl) + ((((ah * bl) + (al * bh)) << 16) >>> 0) | 0;\n    },\n  };\n\n  defineProperties(Math, MathShims);\n\n  var origImul = Math.imul;\n  if (Math.imul(0xffffffff, 5) !== -5) {\n    // Safari 6.1, at least, reports \"0\" for this value\n    Math.imul = MathShims.imul;\n    Value.preserveToString(Math.imul, origImul);\n  }\n\n  return globals;\n}));\n\n/* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./node_modules/webpack/buildin/global.js */ \"./node_modules/webpack/buildin/global.js\")))\n\n//# sourceURL=webpack:///./es6-shim.js?");

/***/ }),

/***/ "./node_modules/webpack/buildin/global.js":
/*!***********************************!*\
  !*** (webpack)/buildin/global.js ***!
  \***********************************/
/*! no static exports found */
/***/ (function(module, exports) {

eval("var g;\n\n// This works in non-strict mode\ng = (function() {\n\treturn this;\n})();\n\ntry {\n\t// This works if eval is allowed (see CSP)\n\tg = g || new Function(\"return this\")();\n} catch (e) {\n\t// This works if the window reference is available\n\tif (typeof window === \"object\") g = window;\n}\n\n// g can still be undefined, but nothing to do about it...\n// We return undefined, instead of nothing here, so it's\n// easier to handle this case. if(!global) { ...}\n\nmodule.exports = g;\n\n\n//# sourceURL=webpack:///(webpack)/buildin/global.js?");

/***/ }),

/***/ 0:
/*!************************!*\
  !*** multi ./es6-shim ***!
  \************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("module.exports = __webpack_require__(/*! ./es6-shim */\"./es6-shim.js\");\n\n\n//# sourceURL=webpack:///multi_./es6-shim?");

/***/ })

/******/ });