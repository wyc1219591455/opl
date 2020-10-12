const path = require('path');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
const CompressionWebpackPlugin = require('compression-webpack-plugin');
const productionGzipExtensions = ['js', 'css'];
const HappyPack = require('happypack');
const os = require('os')
const happyThreadPool = HappyPack.ThreadPool({ size: os.cpus().length });
function resolve(dir) {
    return path.join(__dirname, dir)
}

module.exports = {
    publicPath: process.env.NODE_ENV === 'production' ? './' : '/',
    lintOnSave: false,
    productionSourceMap: true,
    css: {
        sourceMap: true
    },
    chainWebpack: (config) => {
        config.resolve.alias
            .set("@", resolve('src'))
            .set("@crud", resolve('src/components/Crud'))
        //忽略的打包文件
        config.externals({
            'vue': 'Vue',
            'vue-router': 'VueRouter',
            'vuex': 'Vuex',
            'axios': 'axios',
            'element-ui': 'ELEMENT',
        });
        const entry = config.entry('app')
        entry
            .add('babel-polyfill')
            .end()
        entry
            .add('classlist-polyfill')
            .end()
         // set svg-sprite-loader
        config.module
            .rule('svg')
            .exclude.add(resolve('src/assets/icons'))
            .end()
        config.module
            .rule('icons')
            .test(/\.svg$/)
            .include.add(resolve('src/assets/icons'))
            .end()
            .use('svg-sprite-loader')
            .loader('svg-sprite-loader')
            .options({
            symbolId: 'icon-[name]'
            })
        .end()
        // entry
        //     .add('@/mock')
        //     .end()
    },
    configureWebpack: config => {
        config.plugins.push(
            // gzip压缩
            new CompressionWebpackPlugin({
                filename: '[path].gz[query]',
                algorithm: 'gzip',
                test: new RegExp('\\.(' + productionGzipExtensions.join('|') + ')$'),
                threshold: 10240,
                minRatio: 0.8
            }),
            // 文件分析
            // new BundleAnalyzerPlugin(),
            // 多线程打包
            new HappyPack({
                id: 'happy-babel-js',
                loaders: ['babel-loader?cacheDirectory=true'],
                threadPool: happyThreadPool,
            }),
        )
    },
    // eslint-disable-next-line no-dupe-keys
    css: {
        // 是否使用css分离插件 ExtractTextPlugin
        extract: false,
        // 开启 CSS source maps?
        sourceMap: false,
        // css预设器配置项
        loaderOptions: {},
        // 启用 CSS modules for all css / pre-processor files.
        modules: false
    },
    devServer: {
        // 端口配置
        port: 10099,
        // 反向代理配置
        // proxy: {
        //     '/api': {
        //         target: 'http://localhost',
        //         ws: true,
        //         pathRewrite: {
        //             '^/api': '/'
        //         }
        //     },
        // },
    }
}
