const path = require('path');

module.exports = {
    entry: './src/main/js/index.js',
    mode: 'development',
    devtool: 'eval-source-map',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/bundle/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            },
            {
                test: /\.(png|jpe?g|gif)$/i,
                use: [
                    {
                        loader: 'file-loader',
                        options: {
                            name: '[path][name].[ext]?[contenthash]',
                        },
                    },
                ],
            },
            {
                test: /\.css$/,
                use: [
                    "style-loader",
                    {
                        loader: "css-loader",
                        options: {
                            modules: true,
                            sourceMap: true,
                            importLoaders: 1,
                        }
                    },
                    "postcss-loader"
                ]
            },
        ]
    }
};
