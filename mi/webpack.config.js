let path = require('path');
let CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
    entry: './src/index.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist')
    },
    module: {
        loaders: [
            {
                test: /\.handlebars$/,
                loader: "handlebars-loader"
            },
            {
                loader: "babel-loader",

                include: [
                    path.resolve(__dirname, "src"),
                ],
                test: /\.js$/,
                query: {
                    presets: ['es2015']
                }
            },
            {
                test: /\.scss$/,
                use: [
                    {
                        loader: "style-loader"
                    },
                    {
                        loader: "css-loader"
                    },
                    {
                        loader: "sass-loader"
                    }
                ]
            }
        ]
    },
    plugins: [
        new CopyWebpackPlugin([
            {
                from: 'src/index.html'
            }
        ])
    ]
};