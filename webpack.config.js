const path = require('path');
const webpack = require('webpack');

module.exports = {
    entry: './ui-frontend/js/app.js',
    output: {
        path: path.resolve(__dirname, './ui-backend/src/main/resources/static/js'),
        filename: 'bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: [ 'style-loader', 'css-loader' ]
            },
            {
                test: /\.html$/,
                loader: 'html-loader'
            },
            { test: /\.(png|woff|woff2|eot|ttf|svg)$/, loader: 'url-loader?limit=100000' }
        ]
    },
    plugins: [
        new webpack.ProvidePlugin({
            moment: "moment"
        })
    ]
};
