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
                test: /\.js$/,
                enforce: "pre",
                use: ["source-map-loader"],
            },
            {
                test: /\.css$/i,
                use: ["style-loader", "css-loader"]
            },
        ]
    }
};
